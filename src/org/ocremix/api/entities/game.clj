(ns org.ocremix.api.entities.game
  (:require [org.ocremix.api.entities :as entities]
            [org.ocremix.api.persistence :as db]
            [org.ocremix.api.persistence.games :as db-games]
            [org.ocremix.api.util.date :as date]))

(defn- get-game-info
  [id fetch-info-fn format-fn]
  (let [game (entities/get-entity-info id fetch-info-fn (str "Game ID not found: " id))]
    (format-fn game)))

(defn- format-game
  [game]
  (let [game-id (:id game)
        year (:year game)
        year (when year (date/date-to-year year))
        publisher (db/fetch-id-name :organizations (:publisher game))
        system (db/fetch-id-name :systems (:system game))
        composers (db-games/fetch-game-composers game-id)
        artwork (map :url (db-games/fetch-game-art game-id))
        references (map :url (db-games/fetch-game-references game-id))
        chiptunes (db-games/fetch-game-chiptunes game-id)]
    (-> game
        (assoc :year year)
        (assoc :publisher publisher)
        (assoc :system system)
        (assoc :composers composers)
        (assoc :artwork artwork)
        (assoc :references references)
        (assoc :chiptunes chiptunes))))

(defn- format-game-songs
  [game]
  (let [songs (db-games/fetch-game-songs (:id game))]
    (assoc game :songs songs)))

(defn- format-game-albums
  [game]
  (let [albums (db-games/fetch-game-albums (:id game))]
    (assoc game :albums albums)))

(defn- format-game-remixes
  [game]
  (let [remixes (db-games/fetch-game-remixes (:id game))]
    (assoc game :remixes remixes)))

(defn get-game-remixes
  [id]
  (get-game-info id (partial db/fetch-id-name :games) format-game-remixes))

(defn get-game-albums
  [id]
  (get-game-info id (partial db/fetch-id-name :games) format-game-albums))

(defn get-game-songs
  [id]
  (get-game-info id (partial db/fetch-id-name :games) format-game-songs))

(defn get-game
  [id]
  (get-game-info id (partial db/fetch-entity :games) format-game))

