(ns org.ocremix.api.listings.remixes
  (:require [org.ocremix.api.db :as db]
            [org.ocremix.api.util.date :as date]
            [org.ocremix.api.util.param :as param]))

(def ^:private remix-sort-fields #{:id :title :year :size})

(defn- date-to-year
  [m]
  (when (and m (:year m))
    (assoc m :year (date/date-to-year (:year m)))))

(defn- format-game-info
  [game]
  (-> game
      (date-to-year)
      (dissoc :name_jp :publisher :system)))

(defn- format-remix
  [remix]
  (let [remix-id (:id remix)
        mixpost (db/fetch-mixpost remix-id)
        artists (db/fetch-remix-artists remix-id)
        album-id (:album remix)
        album (when album-id
                (db/fetch-id-name :albums album-id))
        songs (db/fetch-remix-songs remix-id)
        composers (when (seq songs)
                    (mapcat db/fetch-song-composers (map :id songs)))
        game (db/fetch-game (:game remix))
        publisher (db/fetch-id-name :organizations (:publisher game))
        system (db/fetch-id-name :systems (:system game))]
    (-> remix
        (assoc :artists artists)
        (assoc :composers composers)
        (assoc :songs songs)
        (assoc :game (format-game-info game))
        (assoc :publisher publisher)
        (assoc :system system)
        (assoc :album album)
        (assoc :date (date/format-date (:posted mixpost)))
        (dissoc :year :comment :lyrics :encoder :size :md5 :torrent))))

(defn get-remixes
  [params]
  (let [[limit offset sort-field sort-dir] (param/parse-paging-params params remix-sort-fields :id)
        remixes (db/fetch-remixes limit offset sort-field sort-dir)]
    {:remixes (map format-remix remixes)}))
