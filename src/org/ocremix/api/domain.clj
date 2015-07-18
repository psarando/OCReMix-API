(ns org.ocremix.api.domain
  (:use [compojure.api.sweet :only [describe]])
  (:require [schema.core :as s]))

(def limit-docs (describe Long
                  "The maximum number of results to return. 50 by default."))
(def offset-docs (describe Long
                   "The number of results to skip, after sorting is applied. 0 by default."))

(defn sort-order-docs
  [valid-sort-fields default-sort]
  (describe (apply s/enum valid-sort-fields)
    (str "The listing field with which to sort the results. `"
         default-sort
         "` by default.")))

(defn sort-dir-docs
  [default-sort-dir]
  (describe (s/enum :ASC :DESC)
    (str "When a sort-order is given, this parameter determines the sort direction. `"
         default-sort-dir
         "` by default.")))

