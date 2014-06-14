(ns api.core
  (:gen-class)
  (:use [compojure.core]
        [ring.middleware params keyword-params])
  (:require [api.db :as db]
            [api.entities.remix :as remix]
            [api.entities.song :as song]
            [api.listings.remixes :as remixes]
            [api.listings.songs :as songs]
            [api.service :as service]
            [api.service.handlers :as handlers]
            [api.util.config :as config]
            [clojure.tools.cli :as cli]
            [compojure.route :as route]
            [ring.adapter.jetty :as jetty]))

(defroutes api-routes
  (GET "/" []
       "Welcome to OCR API v0.1!\n")

  (GET "/remixes" [:as {params :params}]
       (service/trap #(remixes/get-remixes params)))

  (GET "/remixes/:remix-id" [remix-id]
       (service/trap #(remix/get-remix remix-id)))

  (GET "/songs" [:as {params :params}]
       (service/trap #(songs/get-songs params)))

  (GET "/songs/:song-id" [song-id]
       (service/trap #(song/get-song song-id)))

  (GET "/songs/:song-id/remixes" [song-id]
       (service/trap #(song/get-song-remixes song-id)))

  (route/not-found (service/unrecognized-path-response)))

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
  (site-handler api-routes))

