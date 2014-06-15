(ns org.ocremix.api.listings
  (:require [org.ocremix.api.persistence :as db]
            [org.ocremix.api.util.param :as param]))

(defn get-listing
  [table params valid-sort-fields default-sort]
  (let [[limit offset sort-field sort-dir] (param/parse-paging-params params
                                                                      valid-sort-fields
                                                                      default-sort)]
    (db/fetch-listing table limit offset sort-field sort-dir)))

