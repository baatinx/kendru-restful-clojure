(ns kendru-restful-clojure.handler
  (:require [compojure.core :refer [defroutes GET]]
            [ring.util.response :refer [response]]
            [ring.middleware.json :refer [wrap-json-response wrap-json-body]]))

(defroutes app-routes
  (GET "/" []  (response {:message "hi"})))

(def wrap-routes
 (-> app-routes
     wrap-json-response
     wrap-json-body))
