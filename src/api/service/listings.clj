(ns api.service.listings
  (:require [api.db :as db]
            [api.util.date :as date]
            [api.util.param :as param]
            [clojure.tools.logging :as log]))

(def ^:private remix-sort-fields #{:id :title :year :size})

(defn- parse-params
  [params valid-sort-fields default-sort-field]
  (let [limit (param/string-to-int (:limit params) 50)
        offset (param/string-to-int (:offset params) 0)
        sort-dir (param/parse-sort-dir (:sort-dir params))
        sort-field (param/parse-sort-field (:sort-order params)
                                           valid-sort-fields
                                           default-sort-field)]
    [limit offset sort-field sort-dir]))

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

(defn- format-remix
  [remix]
  (let [remix-id (:id remix)
        mixpost (db/fetch-mixpost remix-id)
        artists (db/fetch-remix-artists remix-id)
        album-id (:album remix)
        album (when album-id
                (db/fetch-album album-id))
        songs (db/fetch-remix-songs remix-id)
        composers (when (seq songs)
                    (mapcat db/fetch-song-composers (map :id songs)))
        game (db/fetch-game (:game remix))
        publisher (db/fetch-org (:publisher game))
        system (db/fetch-system (:system game))]
    (-> remix
        (assoc :artists artists)
        (assoc :composers composers)
        (assoc :songs songs)
        (assoc :game (format-game-info game))
        (assoc :publisher (filter-id-name publisher))
        (assoc :system (filter-id-name system))
        (assoc :album (filter-id-name album))
        (assoc :date (date/format-date (:posted mixpost)))
        (dissoc :year :comment :lyrics :encoder :size :md5 :torrent))))

(defn get-remixes
  [params]
  (let [[limit offset sort-field sort-dir] (parse-params params remix-sort-fields :id)
        remixes (db/fetch-remixes limit offset sort-field sort-dir)]
    {:remixes (map format-remix remixes)}))

