(ns org.ocremix.api.core
  (:gen-class)
  (:use [compojure.core]
        [ring.middleware params keyword-params])
  (:require [org.ocremix.api.db :as db]
            [org.ocremix.api.entities.album :as album]
            [org.ocremix.api.entities.game :as game]
            [org.ocremix.api.entities.remix :as remix]
            [org.ocremix.api.entities.song :as song]
            [org.ocremix.api.listings.albums :as albums]
            [org.ocremix.api.listings.games :as games]
            [org.ocremix.api.listings.remixes :as remixes]
            [org.ocremix.api.listings.songs :as songs]
            [org.ocremix.api.service :as service]
            [org.ocremix.api.service.handlers :as handlers]
            [org.ocremix.api.util.config :as config]
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

  (GET "/games" [:as {params :params}]
       (service/trap #(games/get-games params)))

  (GET "/games/:game-id" [game-id]
       (service/trap #(game/get-game game-id)))

  (GET "/games/:game-id/songs" [game-id]
       (service/trap #(game/get-game-songs game-id)))

  (GET "/games/:game-id/albums" [game-id]
       (service/trap #(game/get-game-albums game-id)))

  (GET "/games/:game-id/remixes" [game-id]
       (service/trap #(game/get-game-remixes game-id)))

  (GET "/albums" [:as {params :params}]
       (service/trap #(albums/get-albums params)))

  (GET "/albums/:album-id" [album-id]
       (service/trap #(album/get-album album-id)))

  (GET "/albums/:album-id/composers" [album-id]
       (service/trap #(album/get-album-composers album-id)))

  (GET "/albums/:album-id/remixes" [album-id]
       (service/trap #(album/get-album-remixes album-id)))

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

