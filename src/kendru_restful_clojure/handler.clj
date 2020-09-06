(ns kendru-restful-clojure.handler
  (:require [ring.util.response :refer [response]]
            [monger.util :refer [object-id]]
            [clojure.pprint :as pp]
            [kendru-restful-clojure.ingestion :as insert]
            [kendru-restful-clojure.query :as query]))

(def coll-name "students")

(defn migrate
  "Insert some stuff to play with"
  []
  (insert/docs coll-name [{:_id (object-id) :name "Mustafa Basit" :age 24 :gender "m"}
                          {:_id (object-id) :name "Hammad Mir" :age 20 :gender "m"}
                          {:_id (object-id) :name "Anam Farooqui" :age 20 :gender "f"}
                          {:_id (object-id) :name "Nasreen Sheikh" :age 20 :gender "f"}])
  (response "Migration successful!"))

(defn students
  "Retrieve all students"
  []
  (response (query/students)))

(defn reset-coll-state
  []
  (query/remove-collection coll-name)
  (response "Collection removed!"))

(defn student
  "Retrieve student by id"
  [id]
  (response (query/student id)))

(defn insert-record
  [req]
   (let [{:keys [name age gender]} (:params req)
         id (object-id)
         doc {:_id id
              :name name
              :age age
              :gender gender}
         coll coll-name]
     (insert/doc coll doc))
  (response "Insertion Successfull!"))

(defn inspect-request-map
  [req]
  (response (-> req
                pp/pprint
                with-out-str)))