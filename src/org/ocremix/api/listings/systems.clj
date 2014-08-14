(ns org.ocremix.api.listings.systems
  (:require [org.ocremix.api.listings :as listings]
            [org.ocremix.api.persistence :as db]
            [org.ocremix.api.util.date :as date]))

(def sort-fields #{:id :name :name_jp :year :release_date})

(defn- format-system
  [system]
  (let [year (:year system)
        year (when year (date/date-to-year year))
        publisher (db/fetch-id-name :organizations (:publisher system))]
    (-> system
        (assoc :year year)
        (assoc :publisher publisher))))

(defn get-systems
  [params]
  (let [systems (listings/get-listing :systems params sort-fields :name)]
    {:systems (map format-system systems)}))

