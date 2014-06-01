(ns api.util.date
  (:require [clj-time.coerce :as dt-coerce]
            [clj-time.core :as dt]
            [clj-time.format :as dt-format]))

(defn date-to-year
  [date]
  (dt/year (dt-coerce/from-date date)))

(defn format-date
  [date]
  (dt-format/unparse (dt-format/formatters :year-month-day)
                     (dt-coerce/from-date date)))

