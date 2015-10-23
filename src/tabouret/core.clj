(ns tabouret.core
  (:require [clj-http.client :as http])
  (:import [org.apache.commons.lang3.text WordUtils]))

(defn fetch-page
  [base-url page-id]
  "Fetches and JSON-decodes page at index page-id."
  (http/get (str base-url page-id ".json")
            {:as :json}))

(defn get-all-transactions
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
      (throw (Exception. (str "Could not get page " current-page))))))

(defn get-balance
  ([initial-balance transactions]
   "Computes the final balance after applying these transaction, given
    an initial balance."
   (->> transactions
        (map (fn [transaction]
               ;; read each amount as a number
               ;; TODO : is number parsing robust enough ?
               (bigdec (:Amount transaction))))
        ;; add everything to the initial-balance
        (reduce + initial-balance)))
  ([transactions]
   "Computes the final balance, supposing the initial balance is zero."
   (get-balance 0 transactions)))

(defn clean-text
  [text]
  "Cleans the given text."
  ;; TODO : detect cities and provinces names to capitalize accordingly,
  ;; or better, extract this info in another field.
  ;; TODO : strip out account numbers and ids ?
  (WordUtils/capitalizeFully text))

(defn clean-transactions
  [transactions]
  "Applies some sanitation to the input transactions"
  (->> transactions
       (distinct) ;; remove exact duplicates
       (map (fn [transaction]
              (update-in transaction [:Company] clean-text)))))
