(ns kendru-restful-clojure.handler-test
  (:require [clojure.test :refer [deftest testing is]]
            [kendru-restful-clojure.handler :refer [app]]
            [ring.mock.request :as mock]))

(deftest test-app
  (testing "users endpoint"
    (let [response (app (mock/request :get "/users"))]
      (is (= (:status response) 200))
      (is (= (get-in response [:headers "Content-Type"]) "application-json"))))
  (testing "list endpoint"
    (let [response (app (mock/request :get "/list"))]
          (is (= (:status response ) 200))
          (is (= (get-in response [:headers "Content-type"]) "application-json"))))
  (testing "not found route"
    (let [response (app (mock/request :get "/bogus-route"))]
      (is (= (:status response) 404)))))
