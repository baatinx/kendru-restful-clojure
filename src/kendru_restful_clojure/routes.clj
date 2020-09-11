(ns kendru-restful-clojure.routes
  (:require [compojure.core :refer [defroutes context GET POST]]
            [compojure.route :as route]
            [ring.middleware.json :refer [wrap-json-response]]
            [ring.middleware.params :refer [wrap-params]]
            [ring.middleware.keyword-params :refer [wrap-keyword-params]]
            [buddy.auth.middleware :refer [wrap-authentication wrap-authorization ]]
            [kendru-restful-clojure.handler :refer [migrate students tokens reset-db-state student inspect-request-map insert-record]]
            [buddy.auth.accessrules :refer [restrict]]
            [kendru-restful-clojure.auth :refer [auth-backend authenticate-token user-has-id authenticated-user unauthorized-handler make-token!]]))

(defroutes app-routes
  (GET "/" [] {:content-type "application/json"
               :status 200
               :body {:message "Server Running ..."}})

  (GET "/migrate" [] migrate)
  (GET "/students" [] students)
  (GET "/tokens" [] tokens)
  (GET "/reset-coll-state" [] reset-db-state)

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

  (GET "/buddy" [] (-> inspect-request-map
                      (restrict {:handler authenticated-user
                                 :on-error unauthorized-handler})) )

  (route/not-found "not-found"))

(def routes
  (-> app-routes
      wrap-json-response
      (wrap-authentication auth-backend)
      (wrap-authorization auth-backend)))