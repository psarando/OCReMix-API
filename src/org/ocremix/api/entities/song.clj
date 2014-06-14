(ns org.ocremix.api.entities.song
  (:use [slingshot.slingshot :only [throw+]])
  (:require [org.ocremix.api.db :as db]
            [org.ocremix.api.util.param :as param]))

(defn- format-song
  [song]
  (let [song-id (:id song)
        ost-names (map :name (db/fetch-song-ost-names song-id))
        aliases (map :alias (db/fetch-song-aliases song-id))
        composers (db/fetch-song-composers song-id)
        game (db/fetch-id-name :games (:game song))
        chiptunes (db/fetch-game-chiptunes (:id game))]
    (-> song
        (assoc :ost_names ost-names)
        (assoc :aliases aliases)
        (assoc :composers composers)
        (assoc :game game)
        (assoc :chiptunes chiptunes))))

(defn- format-song-remixes
  [song]
  (let [remixes (db/fetch-song-remixes (:id song))]
    (assoc song :remixes remixes)))

(defn get-song-remixes
  [id]
  (let [song-id (param/string-to-int id nil)
        song (when song-id (db/fetch-id-name :songs song-id))]
    (if song
        (format-song-remixes song)
        (throw+ {:status 404
                 :body (str "Song ID not found: " id)}))))

(defn get-song
  [id]
  (let [song-id (param/string-to-int id nil)
        song (when song-id (db/fetch-song song-id))]
    (if song
        (format-song song)
        (throw+ {:status 404
                 :body (str "Song ID not found: " id)}))))

