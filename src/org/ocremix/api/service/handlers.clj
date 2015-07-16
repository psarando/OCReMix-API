(ns org.ocremix.api.service.handlers
  (:require [clojure.string :as string]
            [clojure.tools.logging :as log]))

(defn req-logger
  [handler]
  (fn [req]
    (log/info "Request received:" (dissoc req :body
                                              :ring.swagger.middleware/data
                                              :compojure.api.middleware/options))
    (let [response (handler req)]
      (log/info "Response:" (dissoc response :body))
      response)))

(defn- lcase-params
  [target]
  (cond
   (map? target)    (into {} (for [[k v] target]
                               [(if (string? k)
                                  (string/lower-case k)
                                  (keyword (string/lower-case (name k))))
                                (lcase-params v)]))
   (vector? target) (mapv lcase-params target)
   :else            target))

(defn wrap-lcase-params
  "Middleware that converts all parameters under :params and :query-params to lower case so that
   they can be treated as effectively case-insensitive."
  [handler]
  (fn [req]
    (handler (-> req
                 (update-in [:params] lcase-params)
                 (update-in [:query-params] lcase-params)))))

