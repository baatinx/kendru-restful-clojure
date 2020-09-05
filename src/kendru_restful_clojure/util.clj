(ns kendru-restful-clojure.util
  (:require [monger.util :refer [get-id]]))

(defn doc-object-id->str
  "Accepts a map"
  [doc]
  (if (contains? doc :_id)
    (let [id (get-id doc)
          hexa-string (str id)]
      (and (org.bson.types.ObjectId/isValid hexa-string) (assoc doc :_id hexa-string)))
    nil))

(defn docs-object-id->str
  "Accepts a vector of maps"
  [docs]
  (pmap #(doc-object-id->str %)
        docs))
