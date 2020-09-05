(ns kendru-restful-clojure.handler
  (:require [clojure.pprint :as pp]
            [compojure.core :refer [defroutes GET]]
            [compojure.route :as route]
            [ring.util.response :refer [response]]
            [ring.middleware.json :refer [wrap-json-response wrap-json-body]]
            [monger.util :refer [object-id]]
            [kendru-restful-clojure.ingestion :as insert]
            [kendru-restful-clojure.query :as query]))

(def coll-name "students")

(defn remove-all
  []
  (query/remove-collection coll-name)
  (response "collection removed!"))

(defn insert-batch
  []
  (insert/docs coll-name [{:_id (object-id) :name "Mustafa Basit" :age 24 :gender "m"}
                          {:_id (object-id) :name "Hammad Mir" :age 20 :gender "m"}
                          {:_id (object-id) :name "Anam Farooqui" :age 20 :gender "f"}
                          {:_id (object-id) :name "Nasreen Sheikh" :age 20 :gender "f"}])
  (response "insertion successful!"))

(defn student
  [id]
  (response (query/student id)))

(defn retrieve-all
  []
  (response (query/students)))

(defn inspect-request-map
  [req]
  (response (str req)))

(defn echo
  [data]
  (response (str data)))

(defroutes app-routes
  (GET "/" [] (response {:message "Server Running ..."}))
  (GET "/remove-all" [] (remove-all))
  (GET "/insert-batch" [] (insert-batch))
  (GET "/retrieve-all" [] (retrieve-all))
  (GET "/student/:id" [id] (student id))
  (GET "/echo/:name" {:keys [params]} (echo (:name params)))
  (GET "/inspect-request-map" req (inspect-request-map req))
  (GET "/inspect-request-map/:id" req (inspect-request-map req))
  (GET "/inspect-request-map/:id/route1" req (inspect-request-map req))
  (route/not-found "not-found"))

(defn wrap-pp
  [handler]
  (fn[req]
    (pp/pprint req)
    (handler req)))

(def routes
  (-> app-routes
      wrap-pp
      wrap-json-response
      wrap-json-body
      wrap-pp))