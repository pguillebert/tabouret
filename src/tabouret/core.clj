(ns tabouret.core
  (:require [clj-http.client :as http]))

(defn fetch-page
  [base-url page-id]
  "Fetches and JSON-decodes page at index page-id."
  (http/get (str base-url page-id ".json")
            {:as :json}))

(defn get-all-data
  [{:keys [base-url] :as conf}]
  "Iterates on API pages to get all the data. Returns a vector
   of all the transactions."
  (loop [accumulator []
         transactions-count 0
         current-page 1]
    ;; fetch the page at current-page
    (if-let [page (:body (fetch-page base-url current-page))]
      (let [expected-count (:totalCount page)
            new-transactions (:transactions page)
            all-transactions-count (+ transactions-count
                                      (count new-transactions))
            all-transactions (concat accumulator new-transactions)]

        ;; Have we gathered all expected data ?
        (if (>= all-transactions-count expected-count)
          ;; we're done, return all-transactions
          all-transactions
          ;; else recur with next page
          (recur all-transactions
                 all-transactions-count
                 (inc current-page))))
      ;; something wrong happened (page is nil)
      (throw (Exception. "Could not get page" current-page)))))
