(ns org.ocremix.api.listings.songs
  (:require [org.ocremix.api.db :as db]
            [org.ocremix.api.util.param :as param]))

(def ^:private song-sort-fields #{:id :name})

(defn- format-song
  [song]
  (let [game (db/fetch-id-name :games (:game song))]
    (assoc song :game game)))

(defn get-songs
  [params]
  (let [[limit offset sort-field sort-dir] (param/parse-paging-params params song-sort-fields :id)
        songs (db/fetch-songs limit offset sort-field sort-dir)]
    {:songs (map format-song songs)}))

