(ns org.ocremix.api.listings.remixes
  (:require [org.ocremix.api.listings :as listings]
            [org.ocremix.api.persistence :as db]
            [org.ocremix.api.persistence.remixes :as db-remixes]
            [org.ocremix.api.persistence.songs :as db-songs]
            [org.ocremix.api.util.date :as date]))

(def ^:private remix-sort-fields #{:id :title :year :size})

(defn- date-to-year
  [m]
  (assoc m :year (when (:year m) (date/date-to-year (:year m)))))

(defn- format-game-info
  [game]
  (-> game
      (date-to-year)
      (dissoc :name_jp :publisher :system)))

(defn- format-remix
  [remix]
  (let [remix-id (:id remix)
        mixpost (db-remixes/fetch-mixpost remix-id)
        artists (db-remixes/fetch-remix-artists remix-id)
        album-id (:album remix)
        album (when album-id
                (db/fetch-id-name :albums album-id))
        songs (db-remixes/fetch-remix-songs remix-id)
        composers (when (seq songs)
                    (mapcat db-songs/fetch-song-composers (map :id songs)))
        game (db/fetch-entity :games (:game remix))
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
  (let [remixes (listings/get-listing :remixes params remix-sort-fields :id)]
    {:remixes (map format-remix remixes)}))

