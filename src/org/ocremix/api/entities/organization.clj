(ns org.ocremix.api.entities.organization
  (:use [slingshot.slingshot :only [throw+]])
  (:require [org.ocremix.api.entities :as entities]
            [org.ocremix.api.persistence :as db]
            [org.ocremix.api.persistence.organizations :as db-organizations]))

(defn- get-organization-info
  [id fetch-info-fn format-fn]
  (let [org (entities/get-entity-info id fetch-info-fn (str "Organization ID not found: " id))]
    (format-fn org)))

(defn- format-organization
  [org]
  (let [references (db-organizations/fetch-org-references (:id org))]
    (assoc org :references references)))

(defn- format-organization-systems
  [org]
  (let [systems (db-organizations/fetch-org-systems (:id org))]
    (assoc org :systems systems)))

(defn- format-organization-games
  [org]
  (let [games (db-organizations/fetch-org-games (:id org))]
    (assoc org :games games)))

(defn- format-organization-composers
  [org]
  (let [composers (db-organizations/fetch-org-composers (:id org))]
    (assoc org :composers composers)))

(defn- format-organization-albums
  [org]
  (let [albums (db-organizations/fetch-org-albums (:id org))]
    (assoc org :albums albums)))

(defn- format-organization-remixes
  [org]
  (let [remixes (db-organizations/fetch-org-remixes (:id org))]
    (assoc org :remixes remixes)))

(defn get-organization-remixes
  [id]
  (get-organization-info id (partial db/fetch-id-name :organizations) format-organization-remixes))

(defn get-organization-albums
  [id]
  (get-organization-info id (partial db/fetch-id-name :organizations) format-organization-albums))

(defn get-organization-composers
  [id]
  (get-organization-info id (partial db/fetch-id-name :organizations) format-organization-composers))

(defn get-organization-games
  [id]
  (get-organization-info id (partial db/fetch-id-name :organizations) format-organization-games))

(defn get-organization-systems
  [id]
  (get-organization-info id (partial db/fetch-id-name :organizations) format-organization-systems))

(defn get-organization
  [id]
  (get-organization-info id (partial db/fetch-entity :organizations) format-organization))

