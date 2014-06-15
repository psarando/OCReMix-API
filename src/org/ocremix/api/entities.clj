(ns org.ocremix.api.entities
  (:use [slingshot.slingshot :only [throw+]])
  (:require [org.ocremix.api.util.param :as param]))

(defn get-entity-info
  [id fetch-entity-fn not-found-msg]
  (let [entity-id (param/string-to-int id nil)
        entity (when entity-id (fetch-entity-fn entity-id))]
    (if entity
        entity
        (throw+ {:status 404
                 :body not-found-msg}))))

