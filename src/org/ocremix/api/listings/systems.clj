(ns org.ocremix.api.listings.systems
  (:require [org.ocremix.api.db :as db]
            [org.ocremix.api.util.date :as date]
            [org.ocremix.api.util.param :as param]))

(def ^:private system-sort-fields #{:id :name :name_jp :year :release_date})

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
  (let [[limit offset sort-field sort-dir] (param/parse-paging-params params system-sort-fields :id)
        systems (db/fetch-systems limit offset sort-field sort-dir)]
    {:systems (map format-system systems)}))

