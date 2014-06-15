(ns org.ocremix.api.listings.artists
  (:require [org.ocremix.api.db :as db]
            [org.ocremix.api.util.date :as date]
            [org.ocremix.api.util.param :as param]))

(def ^:private artist-sort-fields #{:id :name :real_name :gender :birthdate :birthplace})

(defn- format-artist
  [artist]
  (let [birthdate (:birthdate artist)
        birthdate (when birthdate (date/format-date birthdate))]
    (-> artist
        (assoc :birthdate birthdate))))

(defn get-artists
  [params]
  (let [[limit offset sort-field sort-dir] (param/parse-paging-params params artist-sort-fields :id)
        artists (db/fetch-artists limit offset sort-field sort-dir)]
    {:artists (map format-artist artists)}))

