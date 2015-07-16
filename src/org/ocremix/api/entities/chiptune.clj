(ns org.ocremix.api.entities.chiptune
  (:require [org.ocremix.api.entities :as entities]
            [org.ocremix.api.persistence :as db]
            [org.ocremix.api.persistence.games :as db-games]))

(defn- format-chiptune
  [chiptune]
  (let [game-id (:game chiptune)
        game (when game-id (db/fetch-id-name :games game-id))
        composers (when game-id (db-games/fetch-game-composers game-id))]
    (-> chiptune
        (assoc :composers composers)
        (assoc :game game))))

(defn get-chiptune
  [id]
  (let [chiptune (entities/get-entity-info id
                                           (partial db/fetch-entity :chiptunes)
                                           (str "Chiptune ID not found: " id))]
    (format-chiptune chiptune)))

