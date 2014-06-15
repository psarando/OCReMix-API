(ns org.ocremix.api.entities.organization
  (:use [slingshot.slingshot :only [throw+]])
  (:require [org.ocremix.api.db :as db]
            [org.ocremix.api.util.param :as param]))

(defn- get-organization-info
  [id fetch-info-fn format-fn]
  (let [org-id (param/string-to-int id nil)
        org (when org-id (fetch-info-fn org-id))]
    (if org
        (format-fn org)
        (throw+ {:status 404
                 :body (str "Organization ID not found: " id)}))))

(defn- format-organization
  [org]
  (let [references (db/fetch-org-references (:id org))]
    (assoc org :references references)))

(defn- format-organization-systems
  [org]
  (let [systems (db/fetch-org-systems (:id org))]
    (assoc org :systems systems)))

(defn- format-organization-games
  [org]
  (let [games (db/fetch-org-games (:id org))]
    (assoc org :games games)))

(defn- format-organization-artists
  [org]
  (let [composers (db/fetch-org-composers (:id org))]
    (assoc org :composers composers)))

(defn- format-organization-albums
  [org]
  (let [albums (db/fetch-org-albums (:id org))]
    (assoc org :albums albums)))

(defn- format-organization-remixes
  [org]
  (let [remixes (db/fetch-org-remixes (:id org))]
    (assoc org :remixes remixes)))

(defn get-organization-remixes
  [id]
  (get-organization-info id (partial db/fetch-id-name :organizations) format-organization-remixes))

(defn get-organization-albums
  [id]
  (get-organization-info id (partial db/fetch-id-name :organizations) format-organization-albums))

(defn get-organization-artists
  [id]
  (get-organization-info id (partial db/fetch-id-name :organizations) format-organization-artists))

(defn get-organization-games
  [id]
  (get-organization-info id (partial db/fetch-id-name :organizations) format-organization-games))

(defn get-organization-systems
  [id]
  (get-organization-info id (partial db/fetch-id-name :organizations) format-organization-systems))

(defn get-organization
  [id]
  (get-organization-info id db/fetch-org format-organization))

