(ns tabouret.core-test
  (:require [clojure.test :refer :all]
            [tabouret.core :refer :all]))


(def conf {:base-url "http://resttest.bench.co/transactions/"})

(def transactions (get-all-transactions conf))

(deftest iterate-test
  (testing "Iterate all the data (should return 38 results)"
    (is (= 38 (count transactions)))

    (is (= (first transactions)
           {:Date "2013-12-22"
            :Ledger "Phone & Internet Expense"
            :Amount "-110.71"
            :Company "SHAW CABLESYSTEMS CALGARY AB"}))

    (is (= (last transactions)
           {:Date "2013-12-12"
            :Ledger "Business Meals & Entertainment Expense"
            :Amount "-91.12"
            :Company "NESTERS MARKET #x0064 VANCOUVER BC"}))))

(deftest balance-test
  (testing "compute final balance"
    (is (= 18377.16 (get-balance transactions)))
    (is (= 19377.16 (get-balance 1000 transactions)))))

(deftest cleanup-test
  (testing "test cleanup functions"
    (is (= 36 (count (clean-transactions transactions))))
    (is (= "Nesters Market #x0064 Vancouver Bc"
           (:Company (last (clean-transactions transactions)))))))
