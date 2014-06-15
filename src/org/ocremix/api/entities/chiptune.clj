(ns org.ocremix.api.entities.chiptune
  (:use [slingshot.slingshot :only [throw+]])
  (:require [org.ocremix.api.db :as db]
            [org.ocremix.api.entities :as entities]))

(defn- format-chiptune
  [chiptune]
  (let [game-id (:game chiptune)
        game (when game-id (db/fetch-id-name :games game-id))
        composers (when game-id (db/fetch-game-composers game-id))]
    (-> chiptune
        (assoc :composers composers)
        (assoc :game game))))

(defn get-chiptune
  [id]
  (let [chiptune (entities/get-entity-info id
                                           (partial db/fetch-entity :chiptunes)
                                           (str "Chiptune ID not found: " id))]
    (format-chiptune chiptune)))

