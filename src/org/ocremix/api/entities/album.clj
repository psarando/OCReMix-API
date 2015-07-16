(ns org.ocremix.api.entities.album
  (:require [org.ocremix.api.entities :as entities]
            [org.ocremix.api.persistence :as db]
            [org.ocremix.api.persistence.albums :as db-albums]))

(defn- get-album-info
  [id fetch-info-fn format-fn]
  (let [album (entities/get-entity-info id fetch-info-fn (str "Album ID not found: " id))]
    (format-fn album)))

(defn- format-album
  [album]
  (let [album-id (:id album)
        games (db-albums/fetch-album-games album-id)
        publisher (db/fetch-id-name :organizations (:publisher album))
        artists (db-albums/fetch-album-artists album-id)
        artwork (map :url (db-albums/fetch-album-art album-id))
        references (map :url (db-albums/fetch-album-references album-id))]
    (-> album
        (assoc :games games)
        (assoc :publisher publisher)
        (assoc :artists artists)
        (assoc :artwork artwork)
        (assoc :references references))))

(defn- format-album-composers
  [album]
  (let [composers (db-albums/fetch-album-composers (:id album))]
    (assoc album :composers composers)))

(defn- format-album-remixes
  [album]
  (let [remixes (db-albums/fetch-album-remixes (:id album))]
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

