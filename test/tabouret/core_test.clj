(ns tabouret.core-test
  (:require [clojure.test :refer :all]
            [tabouret.core :refer :all]))

(def transactions (get-all-transactions))

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
    (is (= 18377.16M (get-balance transactions)))
    ;; test 2-arity version
    (is (= 19377.16M (get-balance 1000 transactions)))))

(deftest cleanup-test
  (testing "test cleanup functions"
    (is (= 36 (count (clean-transactions transactions))))
    (is (= "Nesters Market #x0064 Vancouver Bc"
           (:Company (last (clean-transactions transactions)))))))

(deftest expenses-by-ledger-test
  (testing "dispatch expenses by ledger"
    (is (= -1851.76M
           (:totalExpenses
            (get (expenses-by-ledger transactions)
                 "Business Meals & Entertainment Expense"))))
    (is (= 10
           (count (:transactions
                   (get (expenses-by-ledger transactions)
                        "Business Meals & Entertainment Expense")))))

    ;; test 2-arity (will not return a transactions key)
    (is (nil?
         (:transactions
          (get (expenses-by-ledger false transactions)
               "Business Meals & Entertainment Expense"))))))

(deftest balance-by-day-test
  (testing "Compute expenses by day"
    ;; balance at the end should be the same as balance test above
    (is (= 18377.16M
           (second (last (balance-by-day transactions)))))

    ;; Balance at first day
    (is (= -227.35M
           (second (first (balance-by-day transactions)))))

    ;; test 2-arity version
    (is (= (+ 1000 -227.35M)
           (second (first (balance-by-day 1000 transactions)))))))
