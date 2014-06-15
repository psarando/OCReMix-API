(ns org.ocremix.api.entities.artist
  (:use [slingshot.slingshot :only [throw+]])
  (:require [org.ocremix.api.db :as db]
            [org.ocremix.api.util.date :as date]
            [org.ocremix.api.util.param :as param]))

(defn- get-artist-info
  [id fetch-info-fn format-fn]
  (let [artist-id (param/string-to-int id nil)
        artist (when artist-id (fetch-info-fn artist-id))]
    (if artist
        (format-fn artist)
        (throw+ {:status 404
                 :body (str "Artist ID not found: " id)}))))

(defn- format-artist
  [artist]
  (let [artist-id (:id artist)
        birthdate (:birthdate artist)
        birthdate (when birthdate (date/format-date birthdate))
        composer-credits (:count (db/fetch-artist-composer-counts artist-id))
        remix-credits (:count (db/fetch-artist-remix-counts artist-id))
        aliases (map :alias (db/fetch-artist-aliases artist-id))
        groups (db/fetch-artist-group artist-id)
        images (map :url (db/fetch-artist-art artist-id))
        references (map :url (db/fetch-artist-references artist-id))]
    (-> artist
        (assoc :birthdate birthdate)
        (assoc :credits {:composer composer-credits, :remixer remix-credits})
        (assoc :aliases aliases)
        (assoc :groups groups)
        (assoc :images images)
        (assoc :references references))))

(defn- format-artist-games
  [artist]
  (let [games (db/fetch-artist-games (:id artist))]
    (assoc artist :games games)))

(defn- format-artist-albums
  [artist]
  (let [albums (db/fetch-artist-albums (:id artist))]
    (assoc artist :albums albums)))

(defn- format-artist-remixes
  [artist]
  (let [remixes (db/fetch-artist-remixes (:id artist))]
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
  (get-artist-info id db/fetch-artist format-artist))

