(ns org.ocremix.api.core
  (:gen-class)
  (:use [ring.middleware params keyword-params])
  (:require [org.ocremix.api.persistence :as db]
            [org.ocremix.api.routes :as routes]
            [org.ocremix.api.service.handlers :as handlers]
            [org.ocremix.api.util.config :as config]))

(defn lein-ring-init
  []
  (config/load-config-from-file)
  (db/define-database))

(defn site-handler
  [routes-fn]
  (-> routes-fn
      handlers/req-logger
      handlers/wrap-lcase-params
      wrap-keyword-params
      wrap-params))

(def app
  (site-handler
   (context "/api/v1" []
            routes/api-routes)))

