(ns tabouret.core-test
  (:require [clojure.test :refer :all]
            [tabouret.core :refer :all]))


(def conf {:base-url "http://resttest.bench.co/transactions/"})

(deftest iterate-test
  (let [data (get-all-data conf)]
    (testing "Iterate all the data (should return 38 results)"
      (is (= 38 (count data)))

      (is (= (first data)
             {:Date "2013-12-22"
              :Ledger "Phone & Internet Expense"
              :Amount "-110.71"
              :Company "SHAW CABLESYSTEMS CALGARY AB"}))

      (is (= (last data)
             {:Date "2013-12-12"
              :Ledger "Business Meals & Entertainment Expense"
              :Amount "-91.12"
              :Company "NESTERS MARKET #x0064 VANCOUVER BC"})))))
