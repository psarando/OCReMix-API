(ns org.ocremix.api.entities.chiptune
  (:use [slingshot.slingshot :only [throw+]])
  (:require [org.ocremix.api.db :as db]
            [org.ocremix.api.util.param :as param]))

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
  (let [chiptune-id (param/string-to-int id nil)
        chiptune (when chiptune-id (db/fetch-chiptune chiptune-id))]
    (if chiptune
        (format-chiptune chiptune)
        (throw+ {:status 404
                 :body (str "Chiptune ID not found: " id)}))))

