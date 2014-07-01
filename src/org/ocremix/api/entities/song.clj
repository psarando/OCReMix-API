(ns org.ocremix.api.entities.song
  (:use [slingshot.slingshot :only [throw+]])
  (:require [org.ocremix.api.entities :as entities]
            [org.ocremix.api.persistence :as db]
            [org.ocremix.api.persistence.games :as db-games]
            [org.ocremix.api.persistence.songs :as db-songs]))

(defn- get-song-info
  [id fetch-info-fn format-fn]
  (let [song (entities/get-entity-info id fetch-info-fn (str "Song ID not found: " id))]
    (format-fn song)))

(defn- format-song
  [song]
  (let [song-id (:id song)
        ost-names (map :name (db-songs/fetch-song-ost-names song-id))
        aliases (map :alias (db-songs/fetch-song-aliases song-id))
        composers (db-songs/fetch-song-composers [song-id])
        game (db/fetch-id-name :games (:game song))
        chiptunes (db-games/fetch-game-chiptunes (:id game))]
    (-> song
        (assoc :ost_names ost-names)
        (assoc :aliases aliases)
        (assoc :composers composers)
        (assoc :game game)
        (assoc :chiptunes chiptunes))))

(defn- format-song-remixes
  [song]
  (let [remixes (db-songs/fetch-song-remixes (:id song))]
    (assoc song :remixes remixes)))

(defn get-song-remixes
  [id]
  (get-song-info id (partial db/fetch-id-name :songs) format-song-remixes))

(defn get-song
  [id]
  (get-song-info id (partial db/fetch-entity :songs) format-song))

