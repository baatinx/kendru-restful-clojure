(ns kendru-restful-clojure.handler
  (:require [compojure.core :refer [defroutes GET]]
            [compojure.route :as route]))

(defn- str-to
  [num]
  (->>
   (range 1 (inc num))
   (interpose ", ")
   (apply str)))

(defn- str-from
  [num]
  (->>
   (range 1 (inc num))
   (reverse)
   (interpose ", ")
   (apply str)))

(defroutes app-routes
    (GET "/" [] "Hello World")
    (GET "/users" [] "users")
    (GET "/count-up/:to" [to] (str-to (Integer. to)))
    (GET "/count-down/:from" [from] (str-from (Integer. from)))
    (route/not-found "Not Found"))