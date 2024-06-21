(ns keycloak.reconciliation-unit-test
  (:require [clojure.test :refer :all]
            [keycloak.reconciliation :as reconciliation]))


(deftest test-skip-plan?-true
  (is (reconciliation/skip-plan? nil nil))
  (is (reconciliation/skip-plan? [] nil))
  (is (reconciliation/skip-plan? [] {}))
  (is (reconciliation/skip-plan? [] {:apply-deletions? false})))

(deftest test-skip-plan?-false
  (is (false? (reconciliation/skip-plan? nil {:apply-deletions? true})))
  (is (false? (reconciliation/skip-plan? [{}] nil)))
  (is (false? (reconciliation/skip-plan? [{}] {}))))


