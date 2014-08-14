(ns org.ocremix.api.core
  (:gen-class)
  (:use [org.ocremix.api.domain]
        [compojure.api.sweet]
        [ring.middleware params keyword-params])
  (:require [org.ocremix.api.entities.album :as album]
            [org.ocremix.api.entities.artist :as artist]
            [org.ocremix.api.entities.chiptune :as chiptune]
            [org.ocremix.api.entities.game :as game]
            [org.ocremix.api.entities.organization :as organization]
            [org.ocremix.api.entities.remix :as remix]
            [org.ocremix.api.entities.song :as song]
            [org.ocremix.api.entities.system :as system]
            [org.ocremix.api.listings.albums :as albums]
            [org.ocremix.api.listings.artists :as artists]
            [org.ocremix.api.listings.chiptunes :as chiptunes]
            [org.ocremix.api.listings.games :as games]
            [org.ocremix.api.listings.organizations :as organizations]
            [org.ocremix.api.listings.remixes :as remixes]
            [org.ocremix.api.listings.songs :as songs]
            [org.ocremix.api.listings.systems :as systems]
            [org.ocremix.api.persistence :as db]
            [org.ocremix.api.routes :as routes]
            [org.ocremix.api.service.handlers :as handlers]
            [org.ocremix.api.util.config :as config]))

(defn lein-ring-init
  []
  (config/load-config-from-file)
  (db/define-database))

(defapi app
  (middlewares
   [handlers/wrap-lcase-params
    wrap-keyword-params
    wrap-params
    handlers/req-logger]
   (swagger-ui)
   (swagger-docs "/api/api-docs"
                 :title "OCReMix API"
                 :apiVersion "0.1.0"
                 :description "OCReMix Public API"
                 :license "Eclipse 1.0"
                 :licenseUrl "http://www.eclipse.org/legal/epl-v10.html")
   (swaggered "api.v1"
              :description "OCReMix API v1"
              (context "/api/v1" []
                       routes/api-routes))))

