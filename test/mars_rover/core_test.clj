(ns mars-rover.core-test
  (:require [clojure.test :refer :all]
            [mars-rover.core :refer :all]))

(deftest local-on-test
  (testing "should locate at 0 0, and face towards north"
    (is (= "I'm at 0 0, and towards N"
          (-> 
           (run "55" "00N" "")
           (mar-rover-to-str))))))

          
(deftest should-return-01N-given-00N-when-receive-m-directive
  (testing "should move towards"
    (is 
      (= "I'm at 0 1, and towards N"
        (-> 
         (run "55" "00N" "M")
         (mar-rover-to-str))))))
          
          
(deftest should-return-22E-given-22N-when-receive-R-directive
  (testing "should turn around"
    (is 
      (= "I'm at 2 2, and towards E"
        (-> 
         (run "55" "22N" "R")
         (mar-rover-to-str))))))
          
(deftest should-return-22W-given-22E-when-receive-MLLM-directive
  (testing "should receive muti-directive"
    (is
      (= "I'm at 2 2, and towards W"
        (->
         (run "55" "22E" "MLLM") 
         (mar-rover-to-str))))))
         
(deftest should-return-05N-given-05N-when-receive-MLM-directive
  (testing "should receive muti-directive"
    (is 
      (= "I'm at 0 5, and towards W"
        (->
         (run "55" "05N" "MLM")
         (mar-rover-to-str))))))
         
(deftest should-return-50S-given-50E-when-receive-MRM-directive
  (testing "should receive muti-directive"
    (is 
      (= "I'm at 5 0, and towards S"
        (->
         (run "55" "50E" "MRM")
         (mar-rover-to-str))))))
         
(deftest should-return-13N-given-12N-when-receive-LMLMLMLMM-directive
  (testing "should receive muti-directive"
    (is 
      (= "I'm at 1 3, and towards N"
        (->
         (run "55" "12N" "LMLMLMLMM")
         (mar-rover-to-str))))))
         
(deftest should-return-13N-given-12N-when-receive-LMLMLMLMM-directive
  (testing "should receive muti-directive"
    (is 
      (= "I'm at 1 3, and towards N"
        (->
         (run "55" "12N" "LMLMLMLMM")
         (mar-rover-to-str))))))
         
(deftest should-return-51E-given-33E-when-receive-MMRMMRMRRM-directive
  (testing "should receive muti-directive"
    (is 
      (= "I'm at 5 1, and towards E"
        (->
         (run "55" "51E" "LMLMLMLMM")
         (mar-rover-to-str))))))
