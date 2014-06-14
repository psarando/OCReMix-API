(ns org.ocremix.api.service
  (:use [slingshot.slingshot :only [try+]])
  (:require [cheshire.core :as cheshire]
            [clojure.tools.logging :as log]))

(def ^:private default-content-type
  "application/json; charset=utf-8")

(defn unrecognized-path-response
  "Builds the response to send for an unrecognized service path."
  []
  (cheshire/encode
   {:success false
    :reason "unrecognized service path"}))

(defn success?
  "Returns true if status-code is between 200 and 299, inclusive."
  [status-code]
  (<= 200 status-code 299))

(defn error-body [e]
  {:reason (.getMessage e)})

(defn- api-response
  [e status-code]
  {:status  status-code
   :body    (cheshire/encode (assoc e :success (success? status-code)))
   :headers {"Content-Type" default-content-type}})

(defn success-response
  ([]
     (success-response {}))
  ([retval]
    (api-response retval 200)))

(defn failure-response [e]
  (log/error e "bad request")
  (api-response (error-body e) 400))

(defn error-response [e]
  (log/error e "internal error")
  (api-response (error-body e) 500))

(defn clj-http-error?
  [{:keys [status body]}]
  (and (number? status) ((comp not nil?) body)))

(defn trap
  "Traps exceptions thrown by endpoints and returns an appropriate repsonse."
  [f]
  (try+
   (success-response (f))
   (catch IllegalArgumentException e (failure-response e))
   (catch IllegalStateException e (failure-response e))
   (catch Throwable t (error-response t))
   (catch clj-http-error? o
     (log/error "clj-http-error" o)
     o)
   (catch Object o (error-response (Exception. (str "unexpected error: " o))))))

