(ns application-layer
  (:require [clj-http.client :as http]))

(http/get "http://localhost:8000")
;; => {:cached nil,
;;     :request-time 107,
;;     :repeatable? false,
;;     :protocol-version {:name "HTTP", :major 1, :minor 1},
;;     :streaming? true,
;;     :http-client
;;     #object[org.apache.http.impl.client.InternalHttpClient 0x4f7d1756 "org.apache.http.impl.client.InternalHttpClient@4f7d1756"],
;;     :chunked? false,
;;     :reason-phrase "OK",
;;     :headers
;;     {"Connection" "close",
;;      "Date" "Sun, 06 Sep 2020 18:20:06 GMT",
;;      "Content-Type" "application/json;charset=utf-8",
;;      "Content-Length" "32",
;;      "Server" "Jetty(9.4.28.v20200408)"},
;;     :orig-content-encoding nil,
;;     :status 200,
;;     :length 32,
;;     :body "{\"message\":\"Server Running ...\"}",
;;     :trace-redirects []}

(:body (http/get "http://localhost:8000/reset-coll-state"))
;; => "Collection removed!"

(:body (http/get "http://localhost:8000/students"))
;; => "[]"

(http/post "http://localhost:8000/insert-record?name=Seerat&age=20&gender=f")
;; => {:cached nil,
;;     :request-time 6,
;;     :repeatable? false,
;;     :protocol-version {:name "HTTP", :major 1, :minor 1},
;;     :streaming? true,
;;     :http-client
;;     #object[org.apache.http.impl.client.InternalHttpClient 0x58d5f9f5 "org.apache.http.impl.client.InternalHttpClient@58d5f9f5"],
;;     :chunked? false,
;;     :reason-phrase "OK",
;;     :headers
;;     {"Connection" "close",
;;      "Date" "Sun, 06 Sep 2020 18:24:54 GMT",
;;      "Content-Length" "22",
;;      "Server" "Jetty(9.4.28.v20200408)"},
;;     :orig-content-encoding nil,
;;     :status 200,
;;     :length 22,
;;     :body "Insertion Successfull!",
;;     :trace-redirects []}

(:body (http/get "http://localhost:8000/students"))
;; => "[{\"_id\":\"5f552976c6da8563047bde88\",\"name\":\"Seerat\",\"age\":\"20\",\"gender\":\"f\"}]"

(http/get "http://localhost:8000/student/5f552976c6da8563047bde88")
;; => {:cached nil,
;;     :request-time 7,
;;     :repeatable? false,
;;     :protocol-version {:name "HTTP", :major 1, :minor 1},
;;     :streaming? true,
;;     :http-client
;;     #object[org.apache.http.impl.client.InternalHttpClient 0x6fae7d60 "org.apache.http.impl.client.InternalHttpClient@6fae7d60"],
;;     :chunked? false,
;;     :reason-phrase "OK",
;;     :headers
;;     {"Connection" "close",
;;      "Date" "Sun, 06 Sep 2020 18:26:03 GMT",
;;      "Content-Type" "application/json;charset=utf-8",
;;      "Content-Length" "74",
;;      "Server" "Jetty(9.4.28.v20200408)"},
;;     :orig-content-encoding nil,
;;     :status 200,
;;     :length 74,
;;     :body "{\"_id\":\"5f552976c6da8563047bde88\",\"name\":\"Seerat\",\"age\":\"20\",\"gender\":\"f\"}",
;;     :trace-redirects []}

(http/get "http://localhost:8000/buddy" {:authorizaion "RC1ogoEbhD9d27x424oRORCgJp92Z1mTxnRkOwKrqVo"})