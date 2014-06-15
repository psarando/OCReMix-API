(ns org.ocremix.api.entities.system
  (:use [slingshot.slingshot :only [throw+]])
  (:require [org.ocremix.api.db :as db]
            [org.ocremix.api.util.date :as date]))

(defn- get-system-info
  [system-id fetch-info-fn format-fn]
  (let [system (when system-id (fetch-info-fn system-id))]
    (if system
        (format-fn system)
        (throw+ {:status 404
                 :body (str "System ID not found: " system-id)}))))

(defn- format-system
  [system]
  (let [year (:year system)
        year (when year (date/date-to-year year))
        publisher (db/fetch-id-name :organizations (:publisher system))
        references (map :url (db/fetch-system-references (:id system)))]
    (-> system
        (assoc :year year)
        (assoc :publisher publisher)
        (assoc :references references))))

(defn- format-system-games
  [system]
  (let [games (db/fetch-system-games (:id system))]
    (assoc system :games games)))

(defn- format-system-composers
  [system]
  (let [composers (db/fetch-system-composers (:id system))]
    (assoc system :composers composers)))

(defn- format-system-albums
  [system]
  (let [albums (db/fetch-system-albums (:id system))]
    (assoc system :albums albums)))

(defn- format-system-remixes
  [system]
  (let [remixes (db/fetch-system-remixes (:id system))]
    (assoc system :remixes remixes)))

(defn get-system-remixes
  [id]
  (get-system-info id (partial db/fetch-id-name :systems) format-system-remixes))

(defn get-system-albums
  [id]
  (get-system-info id (partial db/fetch-id-name :systems) format-system-albums))

(defn get-system-composers
  [id]
  (get-system-info id (partial db/fetch-id-name :systems) format-system-composers))

(defn get-system-games
  [id]
  (get-system-info id (partial db/fetch-id-name :systems) format-system-games))

(defn get-system
  [id]
  (get-system-info id (partial db/fetch-entity :systems) format-system))

