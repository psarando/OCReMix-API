(ns org.ocremix.api.listings.chiptunes
  (:require [org.ocremix.api.db :as db]
            [org.ocremix.api.listings :as listings]))

(def ^:private chiptune-sort-fields #{:id :name :size :file :format :songs})

(defn- format-chiptune
  [chiptune]
  (let [game (db/fetch-id-name :games (:game chiptune))]
    (-> chiptune
        (assoc :game game))))

(defn get-chiptunes
  [params]
  (let [chiptunes (listings/get-listing :chiptunes params chiptune-sort-fields :id)]
    {:chiptunes (map format-chiptune chiptunes)}))

