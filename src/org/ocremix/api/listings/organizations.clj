(ns org.ocremix.api.listings.organizations
  (:require [org.ocremix.api.listings :as listings]))

(def ^:private organization-sort-fields #{:id :name :name_jp})

(defn get-organizations
  [params]
  (let [organizations (listings/get-listing :organizations params organization-sort-fields :name)]
    {:organizations organizations}))

