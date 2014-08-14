(ns org.ocremix.api.domain
  (:require [ring.swagger.schema :as ss]))

(def limit-docs (ss/describe Long "The maximum number of results to return. 50 by default."))
(def offset-docs (ss/describe Long "The number of results to skip, after sorting is applied. 0 by default."))

(defn sort-order-docs
  [valid-sort-fields default-sort]
  (ss/describe String (str "The listing field with which to sort the results. `"
                           default-sort
                           "` by default."
                           " Valid sort fields: "
                           (clojure.string/join ", " (map name valid-sort-fields)))))

(defn sort-dir-docs
  [default-sort-dir]
  (ss/describe String (str "When a sort-order is given, this parameter determines the sort direction: `ASC` or `DESC`. `"
                           default-sort-dir
                           "` by default.")))

