(ns org.ocremix.api.listings.games
  (:require [org.ocremix.api.db :as db]
            [org.ocremix.api.listings :as listings]
            [org.ocremix.api.util.date :as date]))

(def ^:private game-sort-fields #{:id :name :name_short :name_jp :year :system})

(defn- format-game
  [game]
  (let [game-id (:id game)
        year (:year game)
        year (when year (date/date-to-year year))
        publisher (db/fetch-id-name :organizations (:publisher game))
        system (db/fetch-id-name :systems (:system game))]
    (-> game
        (assoc :year year)
        (assoc :publisher publisher)
        (assoc :system system))))

(defn get-games
  [params]
  (let [games (listings/get-listing :games params game-sort-fields :name)]
    {:games (map format-game games)}))

