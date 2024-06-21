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


(deftest test-optimisation-apply-users-plan!
  (testing "When no desired users, should do nothing"
    (let [expected #:user{:additions ()
                          :deletions ()
                          :updates   ()}]
      (is (= expected
            (reconciliation/apply-users-plan! nil "my-realm" #:user{:additions nil
                                                                    :deletions nil})))
      (is (= expected
            (reconciliation/apply-users-plan! nil "my-realm" expected))))))

(deftest test-optimisation-apply-groups-plan!
  (testing "When no desired groups, should do nothing"
    (let [expected {:groups/additions    ()
                    :subgroups/additions ()
                    :groups/deletions    ()
                    :subgroups/deletions ()}]
      (is (= expected
            (reconciliation/apply-groups-plan! nil "my-realm" {:groups/additions    nil
                                                               :groups/deletions    nil
                                                               :subgroups/additions nil
                                                               :subgroups/deletions nil})))
      (is (= expected
            (reconciliation/apply-groups-plan! nil "my-realm" expected))))))