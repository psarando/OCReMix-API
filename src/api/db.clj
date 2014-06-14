(ns api.db
  (:use [korma.core])
  (:require [api.util.config :as config]
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

(defn fetch-song-composers
  [id]
  (select [:artists :a]
          (fields :a.id :a.name)
          (join [:composer_song :cs]
                {:a.id :cs.composer_id})
          (join [:songs :s]
                {:cs.song_id :s.id})
          (where {:s.id id})))

