(ns kendru-restful-clojure.auth
  (:require [monger.collection :as mc]
            [monger.util :refer [object-id]]
            [buddy.hashers :as hashers]
            [buddy.auth.backends.token :refer [token-backend]]
            [buddy.auth :refer [authenticated?]]
            [buddy.auth.accessrules :refer [success error]]
            [crypto.random :refer [base64]]
            [kendru-restful-clojure.ingestion :as insert]
            [kendru-restful-clojure.query :as query]
            [kendru-restful-clojure.db :refer [get-db-ref]]
            [kendru-restful-clojure.util :refer [doc-object-id->str]]))

(defn password-match?
  [id password]
  (if-let [doc (query/student id)]
    (-> (:password-digest doc)
        (->>
         (hashers/check password)))
    nil))

;;(password-match? "" "my-password")

(defn gen-session-id
  []
  (base64 32))

(defn make-token!
  [user-id]
  (let [token (gen-session-id)]
    (insert/doc "authTokens" {:_id (object-id)
                             :user-id user-id
                             :token token
                             :timestamp 300})
    token))

(defn authenticate-token
  [req token]
  (let [db (get-db-ref)
        coll "authToken"
        user-id (-> (mc/find-one-as-map db coll {:token token})
                    doc-object-id->str
                    :user-id)]
    (if user-id
      (str user-id)
      nil)))

;;(authenticate-token 1 "3anFgS+De9dJ3lGKKgHmFpFxmHqatUS+6IsC4Pebl1U=")

(defn unauthorized-handler
  [req msg]
   {:status 401
    :body {:status :error
           :message (or msg "User not authorized")}})

(def auth-backend (token-backend {:authfn authenticate-token
                                  :unauthorized-handler unauthorized-handler}))

(defn authenticated-user
  [req]
  (if (authenticated? req)
    (success)
    (error "User must be authenticated")))

(defn user-has-id
  [id]
  (fn [req]
    (if (= id (get-in req [:identity :id]))
      (success)
      (error (str "User does not have id given")))))