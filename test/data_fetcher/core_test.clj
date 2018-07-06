(ns data-fetcher.core-test
  (:require [clojure.test :refer :all]
            [data-fetcher.core :refer :all]))

(deftest resource-from-url-test
  (testing "resource-from-url"
    (is (= (resource-from-url "https://s3.us-east-2.amazonaws.com/mxnet-scala/scala-example-ci/mnist/mnist.zip") "mnist.zip"))
    (is (= (resource-from-url "file:///Users/john/data/mnist.zip") "mnist.zip"))
    (is (not= (resource-from-url "mnist.zip") "mnist.zip"))))
