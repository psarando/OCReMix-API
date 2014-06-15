(ns org.ocremix.api.listings.albums
  (:require [org.ocremix.api.db :as db]
            [org.ocremix.api.listings :as listings]))

(def ^:private album-sort-fields #{:id :name :catalog :release_date :media :vgmdb_id})

(defn- format-album
  [album]
  (let [publisher (db/fetch-id-name :organizations (:publisher album))]
    (-> album
        (assoc :publisher publisher))))

(defn get-albums
  [params]
  (let [albums (listings/get-listing :albums params album-sort-fields :release_date)]
    {:albums (map format-album albums)}))

