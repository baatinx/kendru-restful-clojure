(ns kendru-restful-clojure.core
  (:require [ring.adapter.jetty :refer [run-jetty]]
            [kendru-restful-clojure.routes :refer [routes]])
  (:gen-class))

(defn run
  []
  (run-jetty routes {:port 8000
                     :join? false}))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (run)
  (println "Server Running ..."))
