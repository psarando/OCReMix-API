(ns org.ocremix.api.listings.artists
  (:require [org.ocremix.api.listings :as listings]
            [org.ocremix.api.util.date :as date]))

(def sort-fields #{:id :name :real_name :gender :birthdate :birthplace})

(defn- format-artist
  [artist]
  (let [birthdate (:birthdate artist)
        birthdate (when birthdate (date/format-date birthdate))]
    (-> artist
        (assoc :birthdate birthdate))))

(defn get-artists
  [params]
  (let [artists (listings/get-listing :artists params sort-fields :name)]
    {:artists (map format-artist artists)}))

