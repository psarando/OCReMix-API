(ns api.entities.game
  (:use [slingshot.slingshot :only [throw+]])
  (:require [api.db :as db]
            [api.util.date :as date]
            [api.util.param :as param]))

(defn- format-game
  [game]
  (let [game-id (:id game)
        year (:year game)
        year (when year (date/date-to-year year))
        publisher (db/fetch-id-name :organizations (:publisher game))
        system (db/fetch-id-name :systems (:system game))
        composers (db/fetch-game-composers game-id)
        artwork (map :url (db/fetch-game-art game-id))
        references (map :url (db/fetch-game-references game-id))
        chiptunes (db/fetch-game-chiptunes game-id)]
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
  (let [songs (db/fetch-game-songs (:id game))]
    (assoc game :songs songs)))

(defn- format-game-albums
  [game]
  (let [albums (db/fetch-game-albums (:id game))]
    (assoc game :albums albums)))

(defn- format-game-remixes
  [game]
  (let [remixes (db/fetch-game-remixes (:id game))]
    (assoc game :remixes remixes)))

(defn get-game-remixes
  [id]
  (let [game-id (param/string-to-int id nil)
        game (when game-id (db/fetch-id-name :games game-id))]
    (if game
        (format-game-remixes game)
        (throw+ {:status 404
                 :body (str "Game ID not found: " id)}))))

(defn get-game-albums
  [id]
  (let [game-id (param/string-to-int id nil)
        game (when game-id (db/fetch-id-name :games game-id))]
    (if game
        (format-game-albums game)
        (throw+ {:status 404
                 :body (str "Game ID not found: " id)}))))

(defn get-game-songs
  [id]
  (let [game-id (param/string-to-int id nil)
        game (when game-id (db/fetch-id-name :games game-id))]
    (if game
        (format-game-songs game)
        (throw+ {:status 404
                 :body (str "Game ID not found: " id)}))))

(defn get-game
  [id]
  (let [game-id (param/string-to-int id nil)
        game (when game-id (db/fetch-game game-id))]
    (if game
        (format-game game)
        (throw+ {:status 404
                 :body (str "Game ID not found: " id)}))))

