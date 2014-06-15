(ns org.ocremix.api.listings.chiptunes
  (:require [org.ocremix.api.listings :as listings]
            [org.ocremix.api.persistence :as db]))

(def ^:private chiptune-sort-fields #{:id :name :size :file :format :songs})

(defn- format-chiptune
  [chiptune]
  (let [game (db/fetch-id-name :games (:game chiptune))]
    (-> chiptune
        (assoc :game game))))

(defn get-chiptunes
  [params]
  (let [chiptunes (listings/get-listing :chiptunes params chiptune-sort-fields :name)]
    {:chiptunes (map format-chiptune chiptunes)}))

