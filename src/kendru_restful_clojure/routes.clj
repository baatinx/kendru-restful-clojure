(ns kendru-restful-clojure.routes
  (:require [monger.util :refer [object-id]]
            [compojure.core :refer [defroutes context GET POST PUT DELETE]]
            [compojure.route :as route]
            [ring.middleware.json :refer [wrap-json-response wrap-json-body]]
            [ring.middleware.params :refer [wrap-params]]
            [ring.middleware.keyword-params :refer [wrap-keyword-params]]
            [ring.util.response :refer [response]]
            [kendru-restful-clojure.handler :refer :all]))

(defroutes app-routes
  (GET "/" [] {:content-type "application/json"
               :status 200
               :body {:message "Server Running ..."}})

  (GET "/migrate" [] (migrate))
  (GET "/students" [] (students))
  (GET "/reset-coll-state" [] (reset-coll-state))

  (GET "/student/:id" [id] (student id))

  (-> (POST "/insert-record" req (insert-record req))
      wrap-keyword-params
      wrap-params)

  ;; optional stuff --BEGIN--
  (GET "/user/:name" [name] (str "welcome " name))

  ;; Parameter coercion
  (GET "/admin/:name" [name :<< #(.toUpperCase %)] (str "welcome" name))

  (context "/inspect-request-map" []
    (GET "/" [] inspect-request-map)
    (GET "/:id" [] inspect-request-map)
    (GET "/:id/query-string1" [] inspect-request-map)
    ;; wrap-param-demo
    (-> (GET "/:id/query-string2" [] inspect-request-map)
        wrap-keyword-params
        wrap-params))
  (route/not-found "not-found"))

(def routes
  (-> app-routes
      wrap-json-response))