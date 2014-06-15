(ns org.ocremix.api.listings.organizations
  (:require [org.ocremix.api.db :as db]
            [org.ocremix.api.util.param :as param]))

(def ^:private organization-sort-fields #{:id :name :name_jp})

(defn get-organizations
  [params]
  (let [[limit offset sort-field sort-dir] (param/parse-paging-params params organization-sort-fields :id)
        organizations (db/fetch-organizations limit offset sort-field sort-dir)]
    {:organizations organizations}))

