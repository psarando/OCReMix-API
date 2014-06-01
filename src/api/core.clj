(ns api.core
  (:gen-class)
  (:use [compojure.core]
        [ring.middleware params keyword-params])
  (:require [api.db :as db]
            [api.service :as service]
            [api.service.entities :as entities]
            [api.service.handlers :as handlers]
            [api.service.listings :as listings]
            [clojure.tools.cli :as cli]
            [compojure.route :as route]
            [ring.adapter.jetty :as jetty]))

(defroutes api-routes
  (GET "/" []
       "Welcome to OCR API v0.1!\n")

  (GET "/remixes" [:as request]
       (service/trap #(listings/get-remixes request)))

  (GET "/remix/:remix-id" [remix-id]
       (service/trap #(entities/get-remix remix-id)))

  (route/not-found (service/unrecognized-path-response)))

(defn lein-ring-init
  []
  (db/define-database))

(defn site-handler
  [routes-fn]
  (-> routes-fn
      handlers/req-logger
      handlers/wrap-lcase-params
      wrap-keyword-params
      wrap-params))

(def app
  (site-handler api-routes))

