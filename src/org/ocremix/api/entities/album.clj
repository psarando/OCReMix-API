(ns org.ocremix.api.entities.album
  (:use [slingshot.slingshot :only [throw+]])
  (:require [org.ocremix.api.db :as db]
            [org.ocremix.api.entities :as entities]))

(defn- get-album-info
  [id fetch-info-fn format-fn]
  (let [album (entities/get-entity-info id fetch-info-fn (str "Album ID not found: " id))]
    (format-fn album)))

(defn- format-album
  [album]
  (let [album-id (:id album)
        games (db/fetch-album-games album-id)
        publisher (db/fetch-id-name :organizations (:publisher album))
        artists (db/fetch-album-artists album-id)
        artwork (map :url (db/fetch-album-art album-id))
        references (map :url (db/fetch-album-references album-id))]
    (-> album
        (assoc :games games)
        (assoc :publisher publisher)
        (assoc :artists artists)
        (assoc :artwork artwork)
        (assoc :references references))))

(defn- format-album-composers
  [album]
  (let [composers (db/fetch-album-composers (:id album))]
    (assoc album :composers composers)))

(defn- format-album-remixes
  [album]
  (let [remixes (db/fetch-album-remixes (:id album))]
    (assoc album :remixes remixes)))

(defn get-album-remixes
  [id]
  (get-album-info id (partial db/fetch-id-name :albums) format-album-remixes))

(defn get-album-composers
  [id]
  (get-album-info id (partial db/fetch-id-name :albums) format-album-composers))

(defn get-album
  [id]
  (get-album-info id (partial db/fetch-entity :albums) format-album))

