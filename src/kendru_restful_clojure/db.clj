(ns kendru-restful-clojure.db
  (:require [monger.core :as mg]
            [monger.collection :as mc]))

(def ^:private host "localhost")
(def ^:private port 27017)
(def ^:private db-name "restful-clojure")
(def ^:private username "mustafa-basit")
(def ^:private password "root")

(defn ^:private get-conn
  []
  (mg/connect {:host host
               :port port}))

(defn get-db-ref
  []
  (mg/get-db (get-conn) db-name))
