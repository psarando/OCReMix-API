(ns org.ocremix.api.listings.chiptunes
  (:require [org.ocremix.api.db :as db]
            [org.ocremix.api.util.param :as param]))

(def ^:private chiptune-sort-fields #{:id :name :size :file :format :songs})

(defn- format-chiptune
  [chiptune]
  (let [game (db/fetch-id-name :games (:game chiptune))]
    (-> chiptune
        (assoc :game game))))

(defn get-chiptunes
  [params]
  (let [[limit offset sort-field sort-dir] (param/parse-paging-params params chiptune-sort-fields :id)
        chiptunes (db/fetch-chiptunes limit offset sort-field sort-dir)]
    {:chiptunes (map format-chiptune chiptunes)}))

