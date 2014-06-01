(ns api.service.entities
  (:use [slingshot.slingshot :only [throw+]])
  (:require [api.db :as db]
            [api.util.date :as date]
            [clojure.tools.logging :as log]))

(defn- date-to-year
  [m]
  (when (and m (:year m))
    (assoc m :year (date/date-to-year (:year m)))))

(defn- filter-id-name
  [m]
  (select-keys m [:id :name]))

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
      (assoc :evaluators (db/fetch-mixpost-evaluators (:remix_id mixpost)))
      (assoc :forum_comments (:forum_link mixpost))
      (dissoc :forum_link)
      (dissoc :remix_id)))

(defn- format-remix
  [remix]
  (let [remix-id (:id remix)
        mixpost (db/fetch-mixpost remix-id)
        artists (db/fetch-remix-artists remix-id)
        album-id (:album remix)
        album (when album-id
                (db/fetch-album album-id))
        download-urls (db/fetch-remix-downloads remix-id)
        songs (db/fetch-remix-songs remix-id)
        composers (when (seq songs)
                    (mapcat db/fetch-song-composers (map :id songs)))
        game (db/fetch-game (:game remix))
        publisher (db/fetch-org (:publisher game))
        system (db/fetch-system (:system game))]
    (log/info remix)
    (log/info mixpost)
    (log/info artists)
    (log/info album)
    (log/info download-urls)
    (log/info songs)
    (log/info composers)
    (log/info game)
    (log/info publisher)
    (log/info system)
    (-> remix
        (date-to-year)
        (assoc :artists artists)
        (assoc :composers composers)
        (assoc :songs songs)
        (assoc :game (format-game-info game))
        (assoc :publisher (filter-id-name publisher))
        (assoc :system (filter-id-name system))
        (assoc :album (filter-id-name album))
        (assoc :download (format-download-info remix download-urls))
        (dissoc :size :md5 :torrent)
        (assoc :mixpost (format-mixpost mixpost)))))

(defn get-remix
  [remix-id]
  (let [remix (db/fetch-remix remix-id)]
    (if remix
        (format-remix remix)
        (throw+ {:status 404
                 :body (str "Remix ID not found: " remix-id)}))))

