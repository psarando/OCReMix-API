(ns org.ocremix.api.listings.songs
  (:require [org.ocremix.api.listings :as listings]
            [org.ocremix.api.persistence :as db]))

(def ^:private song-sort-fields #{:id :name})

(defn- format-song
  [song]
  (let [game (db/fetch-id-name :games (:game song))]
    (assoc song :game game)))

(defn get-songs
  [params]
  (let [songs (listings/get-listing :songs params song-sort-fields :name)]
    {:songs (map format-song songs)}))

