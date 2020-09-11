(ns kendru-restful-clojure.query
  (:require [monger.collection :as mc]
            [monger.db :refer [drop-db]]
            [monger.util :refer [object-id]]
            [kendru-restful-clojure.db :refer [get-db-ref]]
            [kendru-restful-clojure.util :refer [docs-object-id->str doc-object-id->str docs-any-key-object-id->str]]))

(def ^:private db (get-db-ref))

(defn students
  "Keep it simple and short - KISS"
  []
  (let [coll "students"
        db db]
    (-> (mc/find-maps db coll)
        docs-object-id->str )))

(defn student
  [id]
  (let [coll "students"]
    (if (org.bson.types.ObjectId/isValid id)
      (->> (object-id id)
           (mc/find-map-by-id db coll)
           doc-object-id->str)
      nil)))

(defn reset-db-state
  []
  (drop-db db))

(defn tokens
  []
  (let [coll "authTokens"
        db db]
    (-> (mc/find-maps db coll)
        docs-object-id->str
        (docs-any-key-object-id->str :user-id))))
