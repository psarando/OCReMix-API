(ns org.ocremix.api.util.param
  (:require [clojure.string :as string]))

(defmulti param->int-value
  (fn [obj] (type obj)))

(defmethod param->int-value java.lang.String
  [value]
  (try
    (Integer/parseInt value)
    (catch NumberFormatException e
      nil)))

(defmethod param->int-value java.lang.Number
  [value]
  (.intValue value))

(defmethod param->int-value :default
  [value]
  nil)

(defn param->int
  "Attempts to convert a request parameter to an integer.
   Returns the given default if the parameter can't be converted."
  [value default]
  (if-let [int-value (param->int-value value)]
      int-value
      default))

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

(defn parse-paging-params
  "Parses valid limit, offset, sort-field, and sort-dir values from the given request params and
   valid/default sort fields."
  [{:keys [limit offset sort-dir sort-order]} valid-sort-fields default-sort-field]
  (let [limit (param->int limit 50)
        offset (param->int offset 0)
        sort-dir (parse-sort-dir sort-dir)
        sort-field (parse-sort-field sort-order valid-sort-fields default-sort-field)]
    [limit offset sort-field sort-dir]))

