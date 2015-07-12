(ns org.ocremix.api.util.config
  (:use [clojure.java.io :only (file input-stream)])
  (:require [clojure.tools.logging :as log]
            [me.raynes.fs :as fs])
  (:import [java.util.regex Pattern]))

(def ^:private props
  "A ref for storing the configuration properties."
  (ref nil))

(defn- get-required-prop
  [prop-name]
  (let [value (get @props prop-name)]
    (when (empty? value)
      (log/error "Missing required property:" prop-name))
    value))

(defn db-host
  []
  (get-required-prop :ocr.db.host))

(defn db-port
  []
  (get-required-prop :ocr.db.port))

(defn db-name
  []
  (get-required-prop :ocr.db.name))

(defn db-user
  []
  (get-required-prop :ocr.db.user))

(defn db-password
  []
  (get-required-prop :ocr.db.password))

(defn- find-properties-file
  "Searches the classpath or current dir for the named properties file."
  [file-path]
  (if (fs/file? file-path)
    (fs/file file-path)
    (let [resource (.getResource (.. Thread currentThread getContextClassLoader) file-path)]
      (if (nil? resource)
        (first (fs/find-files "." (re-pattern (Pattern/quote file-path))))
        (.getFile resource)))))

(defn- read-properties
  "Reads properties from a file into a Properties object.
   Adapted from code in the clojure.contrib.properties."
  [file-or-path]
  (with-open [f (java.io.FileInputStream. (file file-or-path))]
    (doto (java.util.Properties.)
      (.load f))))

(defn- parse-props
  [props]
  (into {} (for [[k v] props] [(keyword k) v])))

(defn load-config-from-file
  "Loads the configuration settings from a properties file."
  ([]
   (load-config-from-file "api.properties"))
  ([cfg-path]
   (dosync (ref-set props (-> cfg-path
                              (find-properties-file)
                              (read-properties)
                              (parse-props))))
   (log/info "props:" props)))
