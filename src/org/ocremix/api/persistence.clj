(ns org.ocremix.api.persistence
  (:use [korma.core])
  (:require [org.ocremix.api.util.config :as config]
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

(defn fetch-listing
  [table result-limit start sort-field sort-dir]
  (select table
          (order sort-field sort-dir)
          (limit result-limit)
          (offset start)))

(defn fetch-entity
  [table id]
  (first
   (select table
           (where {:id id}))))

(defn fetch-id-name
  [table id]
  (first
   (select table
           (fields :id :name)
           (where {:id id}))))

