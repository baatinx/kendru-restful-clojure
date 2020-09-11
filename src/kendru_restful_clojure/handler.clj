(ns kendru-restful-clojure.handler
  (:require [ring.util.response :refer [response]]
            [monger.util :refer [object-id]]
            [clojure.pprint :as pp]
            [buddy.hashers :as hashers]
            [crypto.random :refer [base64]]
            [kendru-restful-clojure.ingestion :as insert]
            [kendru-restful-clojure.query :as query]
            [kendru-restful-clojure.auth :as auth]))

(defn migrate
  "Insert some stuff to play with"
  [_]
  (let [b (object-id)
        h (object-id)
        a (object-id)
        n (object-id)]
    (insert/docs "students" [{:_id b :name "Mustafa Basit" :age 24 :gender "m" :email "mailtobasit74@gmail.com" :password-digest (hashers/encrypt "my-password") :level "admin"}
                             {:_id h :name "Hammad Mir" :age 20 :gender "m" :email "mailtobasit74@gmail.com" :password-digest (hashers/encrypt "his-password") :level "user"}
                             {:_id a :name "Anam Farooqui" :age 20 :gender "f" :email "mailtobasit74@gmail.com" :password-digest (hashers/encrypt "Anam's-password") :level "user"}
                             {:_id n :name "Nasreen Sheikh" :age 20 :gender "f" :email "mailtobasit74@gmail.com" :password-digest (hashers/encrypt "Nasreen's-password") :level "user"}])
    (auth/make-token! b)
    (auth/make-token! h)
    (auth/make-token! a)
    (auth/make-token! n)
    (response "Migration successful!")))

(defn students
  "Retrieve all students"
  [_]
  (response (query/students)))

(defn reset-db-state
  [_]
  (query/reset-db-state)
  (response "DB Dropped!"))

(defn student
  "Retrieve student by id"
  [id]
  (response (query/student id)))

(defn tokens
  "Retrieve all Auth-tokens"
  [_]
  (response (query/tokens)))

(defn insert-record
  [req]
  (let [{:keys [name age gender]} (:params req)
        id (object-id)
        doc {:_id id
             :name name
             :age age
             :gender gender}
        coll "students"]
    (insert/doc coll doc))
  (response "Insertion Successfull!"))

(defn inspect-request-map
  [req]
  (response (-> req
                pp/pprint
                with-out-str)))