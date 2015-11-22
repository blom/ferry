(defproject ferry "0.2.0"
  :description "Library for DigitalOcean's API."
  :url "https://github.com/blom/ferry"
  :license {:name "ISC"
            :url "https://github.com/blom/ferry/blob/master/LICENSE"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [http-kit "2.1.19"]
                 [org.clojure/data.json "0.2.6"]]
  :profiles {:dev {:dependencies [[midje "1.8.2"]]}}
  :plugins [[lein-codox "0.9.0"]
            [lein-midje "3.2"]])
