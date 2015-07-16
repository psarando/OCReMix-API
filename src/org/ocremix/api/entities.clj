(ns org.ocremix.api.entities
  (:use [ring.util.http-response :only [not-found!]]))

(defn get-entity-info
  [id fetch-entity-fn not-found-msg]
  (let [entity (when id (fetch-entity-fn id))]
    (if entity
        entity
        (not-found! not-found-msg))))
