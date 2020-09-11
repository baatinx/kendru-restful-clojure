(ns kendru-restful-clojure.ingestion
  (:require [monger.collection :as mc]
            [kendru-restful-clojure.db :refer [get-db-ref]]))

(def ^:private db-ref (get-db-ref))

(defn doc
  "Accepts a map"
  [coll doc]
  (mc/insert db-ref coll doc))

(defn docs
  "Accepts a vector of maps"
  [coll docs]
  (mc/insert-batch db-ref coll docs))
