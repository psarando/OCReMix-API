(defproject org.ocremix/api "0.0.1-SNAPSHOT"
  :description "OCReMix.org public API."
  :url "https://github.com/psarando/OCReMix-API"
  :license {:name "Eclipse Public License - v 1.0"
            :url "https://raw.githubusercontent.com/psarando/OCReMix-API/master/LICENSE"}
  :scm {:connection "scm:git:git@github.com:psarando/OCReMix-API.git"
        :developerConnection "scm:git:git@github.com:psarando/OCReMix-API.git"
        :url "git@github.com:psarando/OCReMix-API.git"}
  :dependencies [[cheshire "5.3.1"]
                 [clj-http "0.9.1"]
                 [clj-time "0.7.0"]
                 [compojure "1.1.7"]
                 [korma "0.3.0-RC4"]
                 [ring "1.3.0-RC1"]
                 [me.raynes/fs "1.4.5"]
                 [metosin/compojure-api "0.13.3"]
                 [metosin/ring-swagger-ui "2.0.17"]
                 [org.clojure/clojure "1.6.0"]
                 [org.clojure/data.codec "0.1.0"]
                 [org.clojure/tools.cli "0.3.1"]
                 [org.clojure/tools.logging "0.2.6"]
                 [postgresql "9.0-801.jdbc4"]]
  :plugins [[lein-ring "0.8.8"]]
  :aot [org.ocremix.api.core]
  :main org.ocremix.api.core
  :ring {:handler org.ocremix.api.core/app
         :init org.ocremix.api.core/lein-ring-init
         :port 8080}
  :uberjar-exclusions [#".*[.]SF" #"LICENSE" #"NOTICE"])

