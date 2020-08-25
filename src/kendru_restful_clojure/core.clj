(ns kendru-restful-clojure.core
  (:require [ring.adapter.jetty :refer [run-jetty]]
            [kendru-restful-clojure.handler :refer [wrap-routes]])
  (:gen-class))

(defn run
  []
  (run-jetty wrap-routes {:port 8000
                          :join? false}))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (def app (run)))
