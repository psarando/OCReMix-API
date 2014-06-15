(ns org.ocremix.api.entities.artist
  (:use [slingshot.slingshot :only [throw+]])
  (:require [org.ocremix.api.entities :as entities]
            [org.ocremix.api.persistence :as db]
            [org.ocremix.api.persistence.artists :as db-artists]
            [org.ocremix.api.util.date :as date]))

(defn- get-artist-info
  [id fetch-info-fn format-fn]
  (let [artist (entities/get-entity-info id fetch-info-fn (str "Artist ID not found: " id))]
    (format-fn artist)))

(defn- format-artist
  [artist]
  (let [artist-id (:id artist)
        birthdate (:birthdate artist)
        birthdate (when birthdate (date/format-date birthdate))
        composer-credits (:count (db-artists/fetch-artist-composer-counts artist-id))
        remix-credits (:count (db-artists/fetch-artist-remix-counts artist-id))
        aliases (map :alias (db-artists/fetch-artist-aliases artist-id))
        groups (db-artists/fetch-artist-group artist-id)
        images (map :url (db-artists/fetch-artist-art artist-id))
        references (map :url (db-artists/fetch-artist-references artist-id))]
    (-> artist
        (assoc :birthdate birthdate)
        (assoc :credits {:composer composer-credits, :remixer remix-credits})
        (assoc :aliases aliases)
        (assoc :groups groups)
        (assoc :images images)
        (assoc :references references))))

(defn- format-artist-games
  [artist]
  (let [games (db-artists/fetch-artist-games (:id artist))]
    (assoc artist :games games)))

(defn- format-artist-albums
  [artist]
  (let [albums (db-artists/fetch-artist-albums (:id artist))]
    (assoc artist :albums albums)))

(defn- format-artist-remixes
  [artist]
  (let [remixes (db-artists/fetch-artist-remixes (:id artist))]
    (assoc artist :remixes remixes)))

(defn get-artist-remixes
  [id]
  (get-artist-info id (partial db/fetch-id-name :artists) format-artist-remixes))

(defn get-artist-albums
  [id]
  (get-artist-info id (partial db/fetch-id-name :artists) format-artist-albums))

(defn get-artist-games
  [id]
  (get-artist-info id (partial db/fetch-id-name :artists) format-artist-games))

(defn get-artist
  [id]
  (get-artist-info id (partial db/fetch-entity :artists) format-artist))

