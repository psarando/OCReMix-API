(ns org.ocremix.api.core
  (:use [compojure.api.sweet]
        [ring.middleware params keyword-params]
        [ring.middleware.http-response :only [wrap-http-response]])
  (:require [compojure.route :as route]
            [org.ocremix.api.persistence :as db]
            [org.ocremix.api.routes :as routes]
            [org.ocremix.api.service :as service]
            [org.ocremix.api.service.handlers :as handlers]
            [org.ocremix.api.util.config :as config]))

(defn lein-ring-init
  []
  (config/load-config-from-file)
  (db/define-database))

(defapi app
  (swagger-ui)
  (swagger-docs
    {:info {:title "OCReMix API"
            :version "0.2.0"
            :description "OCReMix Public API"
            :license "Eclipse 1.0"
            :licenseUrl "http://www.eclipse.org/legal/epl-v10.html"}
     :tags [{:name "api.v1", :description "OCReMix API v1"}
            {:name "remixes", :description "Remixes and Arrangements"}
            {:name "songs", :description "Original Game Songs"}
            {:name "games", :description "Games"}
            {:name "albums", :description "OCReMix Albums"}
            {:name "artists", :description "Remixers and Composers"}
            {:name "systems", :description "Game Systems"}
            {:name "orgs", :description "Organizations"}
            {:name "chiptunes", :description "Original Game Chiptunes"}]})
  (middlewares
   [handlers/wrap-lcase-params
    wrap-keyword-params
    wrap-params
    wrap-http-response
    handlers/req-logger]
    (context* "/api/v1" []
      :tags ["api.v1"]
      routes/remix-routes
      routes/song-routes
      routes/game-routes
      routes/album-routes
      routes/artist-routes
      routes/system-routes
      routes/org-routes
      routes/chiptune-routes)
    (route/not-found (service/unrecognized-path-response))))
