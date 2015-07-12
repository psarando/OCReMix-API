(defproject org.ocremix/api "0.0.2-SNAPSHOT"
  :description "OCReMix.org public API."
  :url "https://github.com/psarando/OCReMix-API"
  :license {:name "Eclipse Public License - v 1.0"
            :url "https://raw.githubusercontent.com/psarando/OCReMix-API/master/LICENSE"}
  :scm {:connection "scm:git:git@github.com:psarando/OCReMix-API.git"
        :developerConnection "scm:git:git@github.com:psarando/OCReMix-API.git"
        :url "git@github.com:psarando/OCReMix-API.git"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/tools.logging "0.3.1"]
                 [cheshire "5.5.0"]
                 [clj-http "1.0.0"]
                 [clj-time "0.9.0"]
                 [korma "0.4.2"]
                 [me.raynes/fs "1.4.6"]
                 [metosin/compojure-api "0.22.1"]
                 [postgresql "9.3-1101.jdbc4"]]
  :ring {:handler org.ocremix.api.core/app
         :init org.ocremix.api.core/lein-ring-init
         :port 8080}
  :uberjar-name "ocr-api.jar"
  :profiles {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]]
                   :plugins [[lein-ring "0.9.6"]]}}
  :uberjar-exclusions [#".*[.]SF" #"LICENSE" #"NOTICE"])

