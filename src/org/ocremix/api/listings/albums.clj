(ns org.ocremix.api.listings.albums
  (:require [org.ocremix.api.db :as db]
            [org.ocremix.api.util.param :as param]))

(def ^:private album-sort-fields #{:id :name :catalog :release_date :media :vgmdb_id})

(defn- format-album
  [album]
  (let [publisher (db/fetch-id-name :organizations (:publisher album))]
    (-> album
        (assoc :publisher publisher))))

(defn get-albums
  [params]
  (let [[limit offset sort-field sort-dir] (param/parse-paging-params params album-sort-fields :id)
        albums (db/fetch-albums limit offset sort-field sort-dir)]
    {:albums (map format-album albums)}))

