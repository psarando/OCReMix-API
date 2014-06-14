(ns org.ocremix.api.db
  (:use [korma.core])
  (:require [org.ocremix.api.util.config :as config]
            [clojure.tools.logging :as log]
            [korma.db :as db]))

(defn- create-db-spec
  "Creates the database connection spec to use when accessing the database
   using Korma."
  []
  {:classname   (config/db-driver)
   :subprotocol (config/db-subprotocol)
   :subname     (str "//" (config/db-host) ":" (config/db-port) "/" (config/db-name))
   :user        (config/db-user)
   :password    (config/db-password)})

(defn define-database
  "Defines the database connection to use from within Clojure."
  []
  (defonce ocr (db/create-db (create-db-spec)))
  (db/default-connection ocr))

(defn fetch-remixes
  [result-limit start sort-field sort-dir]
  (select :remixes
          (order sort-field sort-dir)
          (limit result-limit)
          (offset start)))

(defn fetch-songs
  [result-limit start sort-field sort-dir]
  (select :songs
          (order sort-field sort-dir)
          (limit result-limit)
          (offset start)))

(defn fetch-games
  [result-limit start sort-field sort-dir]
  (select :games
          (order sort-field sort-dir)
          (limit result-limit)
          (offset start)))

(defn fetch-remix
  [id]
  (first
   (select :remixes
           (where {:id id}))))

(defn fetch-mixpost
  [id]
  (first
   (select :mixposts
           (where {:remix_id id}))))

(defn fetch-song
  [id]
  (first
   (select :songs
           (where {:id id}))))

(defn fetch-album
  [id]
  (first
   (select :albums
           (where {:id id}))))

(defn fetch-artist
  [id]
  (first
   (select :artists
           (where {:id id}))))

(defn fetch-game
  [id]
  (first
   (select :games
           (where {:id id}))))

(defn fetch-org
  [id]
  (first
   (select :organizations
           (where {:id id}))))

(defn fetch-system
  [id]
  (first
   (select :systems
           (where {:id id}))))

(defn fetch-id-name
  [table id]
  (first
   (select table
           (fields :id :name)
           (where {:id id}))))

(defn fetch-remix-artists
  [id]
  (select [:artists :a]
          (fields :a.id :a.name)
          (join [:remix_artist :ra]
                {:a.id :ra.artist_id})
          (join [:remixes :r]
                {:ra.remix_id :r.id})
          (where {:r.id id})))

(defn fetch-remix-downloads
  [id]
  (select :remix_download
          (fields :url)
          (where {:remix_id id})))

(defn fetch-mixpost-evaluators
  [id]
  (select [:artists :a]
          (fields :a.id :a.name)
          (join [:mixpost_evaluator :me]
                {:a.id :me.artist_id})
          (join [:remixes :r]
                {:me.remix_id :r.id})
          (where {:r.id id})))

(defn fetch-remix-songs
  [id]
  (select [:songs :s]
          (fields :s.id :s.name)
          (join [:remix_song :rs]
                {:s.id :rs.song_id})
          (join [:remixes :r]
                {:rs.remix_id :r.id})
          (where {:r.id id})))

(defn fetch-song-remixes
  [id]
  (select [:remixes :r]
          (fields :r.id :r.title)
          (join [:remix_song :rs]
                {:rs.remix_id :r.id})
          (join [:songs :s]
                {:s.id :rs.song_id})
          (where {:s.id id})))

(defn fetch-song-composers
  [id]
  (select [:artists :a]
          (fields :a.id :a.name)
          (join [:composer_song :cs]
                {:a.id :cs.composer_id})
          (join [:songs :s]
                {:cs.song_id :s.id})
          (where {:s.id id})))

(defn fetch-song-ost-names
  [song-id]
  (select :song_ost_name
          (fields :name)
          (where {:song_id song-id})))

(defn fetch-song-aliases
  [song-id]
  (select :song_alias
          (fields :alias)
          (where {:song_id song-id})))

(defn fetch-game-songs
  [game-id]
  (select [:songs :s]
          (fields :id :name)
          (aggregate (count :rs.remix_id) :remix_count)
          (join [:remix_song :rs]
                {:rs.song_id :s.id})
          (group :s.id :s.name)
          (where {:game game-id})))

(defn fetch-game-albums
  [game-id]
  (select [:albums :a]
          (fields :a.id :a.name)
          (join [:album_game :ag]
                {:ag.album_id :a.id})
          (join [:games :g]
                {:g.id :ag.game_id})
          (where {:g.id game-id})))

(defn fetch-game-remixes
  [game-id]
  (select [:remixes :r]
          (fields :r.id :r.title)
          (join [:remix_song :rs]
                {:rs.remix_id :r.id})
          (join [:songs :s]
                {:rs.song_id :s.id})
          (where {:s.game game-id})))

(defn fetch-game-composers
  [game-id]
  (select [:artists :a]
          (fields :a.id :a.name)
          (join [:composer_song :cs]
                {:a.id :cs.composer_id})
          (join [:songs :s]
                {:cs.song_id :s.id})
          (where {:s.game game-id})))

(defn fetch-game-art
  [game-id]
  (select :game_art
          (fields :url)
          (where {:game_id game-id})))

(defn fetch-game-references
  [game-id]
  (select :game_reference
          (fields :url)
          (where {:game_id game-id})))

(defn fetch-game-chiptunes
  [game-id]
  (select :chiptunes
          (fields :id :name :size)
          (where {:game_id game-id})))
