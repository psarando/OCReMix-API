(ns org.ocremix.api.listings.songs
  (:require [org.ocremix.api.db :as db]
            [org.ocremix.api.listings :as listings]))

(def ^:private song-sort-fields #{:id :name})

(defn- format-song
  [song]
  (let [game (db/fetch-id-name :games (:game song))]
    (assoc song :game game)))

(defn get-songs
  [params]
  (let [songs (listings/get-listing :songs params song-sort-fields :id)]
    {:songs (map format-song songs)}))

