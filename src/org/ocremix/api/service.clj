(ns org.ocremix.api.service
  (:use [ring.util.http-response :only [ok bad-request internal-server-error]]
        [slingshot.slingshot :only [try+ throw+]])
  (:require [cheshire.core :as cheshire]
            [clojure.tools.logging :as log]))

(defn unrecognized-path-response
  "Builds the response to send for an unrecognized service path."
  []
  (cheshire/encode
   {:reason "unrecognized service path"}))

(defn error-body [e]
  {:reason (.getMessage e)})

(defn failure-response [e]
  (log/error e "bad request")
  (bad-request (error-body e)))

(defn error-response [e]
  (log/error e "internal error")
  (internal-server-error (error-body e)))

(defn clj-http-error?
  [{:keys [status body]}]
  (and (number? status) ((comp not nil?) body)))

(defn trap
  "Traps exceptions thrown by endpoints and returns an appropriate repsonse."
  [f]
  (try+
    (ok (f))
    (catch IllegalArgumentException e (failure-response e))
    (catch IllegalStateException e (failure-response e))
    (catch Throwable t (error-response t))
    (catch [:type :ring.util.http-response/response] {:keys [response] :as e}
      (log/error "http-response" response)
      (throw+ e))
    (catch clj-http-error? o
      (log/error "clj-http-error" o)
      o)
    (catch Object o (error-response (Exception. (str "unexpected error: " o))))))

