(ns api.service.handlers
  (:require [clojure.string :as string]
            [clojure.tools.logging :as log]))

(defn req-logger
  [handler]
  (fn [req]
    (log/info "Request received:" req)
    (handler req)))

(defn- lcase-params
  [target]
  (cond
   (map? target)    (into {} (for [[k v] target]
                               [(if (string? k)
                                  (string/lower-case k)
                                  (keyword (string/lower-case k)))
                                (lcase-params v)]))
   (vector? target) (mapv lcase-params target)
   :else            target))

(defn wrap-lcase-params
  "Middleware that converts all parameters under :params to lower case so that they can be treated
   as effectively case-insensitive."
  [handler]
  (fn [req]
    (handler (update-in req [:params] lcase-params))))

