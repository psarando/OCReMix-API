(ns api.util.param
  (:require [clojure.string :as string]))

(defn string-to-int
  "Attempts to convert a String to an integer.
   Returns the given default if the string can't be converted."
  [value default]
  (try
    (if value
      (Integer/parseInt value)
      default)
    (catch NumberFormatException e
      default)))

(defn parse-sort-field
  "Returns the given sort-field as a lower-cased keyword if the set valid-sort-fields contains that
   keyword, otherwise the default-sort-field is returned."
  [sort-field valid-sort-fields default-sort-field]
  (let [sort-field (keyword (when sort-field (string/lower-case sort-field)))]
    (if (contains? valid-sort-fields sort-field)
      sort-field
      default-sort-field)))

(defn parse-sort-dir
  [sort-dir]
  (if (and sort-dir (= (string/upper-case sort-dir) "ASC"))
    :ASC
    :DESC))
