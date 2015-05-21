;
(defproject rest "0.1.0-SNAPSHOT"
  :description "Rest Example"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [compojure "1.1.6"]
                 [hiccup "1.0.4"]
                 [cheshire "5.4.0"]]
  :plugins [[lein-ring "0.8.10"]]
  :ring {:handler rest.handler/app
         :url-pattern "/"
         :servlet-name "rest"}
  
  :java-source-paths ["src/main/java/"]
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring-mock "0.1.5"]]}})

