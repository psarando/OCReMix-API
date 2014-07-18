(ns org.ocremix.api.entities.remix
  (:require [org.ocremix.api.entities :as entities]
            [org.ocremix.api.persistence :as db]
            [org.ocremix.api.persistence.remixes :as db-remixes]
            [org.ocremix.api.persistence.songs :as db-songs]
            [org.ocremix.api.util.date :as date]))

(defn- date-to-year
  [m]
  (assoc m :year (when (:year m) (date/date-to-year (:year m)))))

(defn- format-game-info
  [game]
  (-> game
      (date-to-year)
      (dissoc :name_jp :publisher :system)))

(defn- format-download-info
  [remix download-urls]
  (-> remix
      (select-keys [:size :md5 :torrent])
      (assoc :links (map :url download-urls))))

(defn- format-mixpost
  [mixpost]
  (-> mixpost
      (assoc :posted (date/format-date (:posted mixpost)))
      (assoc :evaluators (db-remixes/fetch-mixpost-evaluators (:remix_id mixpost)))
      (assoc :forum_comments (:forum_link mixpost))
      (dissoc :forum_link)
      (dissoc :remix_id)))

(defn- format-remix
  [remix]
  (let [remix-id (:id remix)
        mixpost (db-remixes/fetch-mixpost remix-id)
        artists (db-remixes/fetch-remix-artists remix-id)
        album-id (:album remix)
        album (when album-id
                (db/fetch-id-name :albums album-id))
        download-urls (db-remixes/fetch-remix-downloads remix-id)
        songs (db-remixes/fetch-remix-songs remix-id)
        composers (when (seq songs)
                    (db-songs/fetch-song-composers (map :id songs)))
        game (db/fetch-entity :games (:game remix))
        publisher (db/fetch-id-name :organizations (:publisher game))
        system (db/fetch-id-name :systems (:system game))]
    (-> remix
        (date-to-year)
        (assoc :artists artists)
        (assoc :composers composers)
        (assoc :songs songs)
        (assoc :game (format-game-info game))
        (assoc :publisher publisher)
        (assoc :system system)
        (assoc :album album)
        (assoc :download (format-download-info remix download-urls))
        (dissoc :size :md5 :torrent)
        (assoc :mixpost (format-mixpost mixpost)))))

(defn get-remix
  [remix-id]
  (let [remix (entities/get-entity-info remix-id
                                        (partial db/fetch-entity :remixes)
                                        (str "Remix ID not found: " remix-id))]
    (format-remix remix)))

