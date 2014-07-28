(ns org.ocremix.api.core
  (:gen-class)
  (:use [ring.middleware params keyword-params])
  (:require [org.ocremix.api.persistence :as db]
            [org.ocremix.api.routes :as routes]
            [org.ocremix.api.service.handlers :as handlers]
            [org.ocremix.api.util.config :as config]
            [compojure.api.sweet :refer :all]))

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

