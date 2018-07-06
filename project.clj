(defproject data-fetcher "0.1.0"
  :description "Helper library to fetch remote using Clojure"
  :url "https://github.com/agilecreativity/data-fetcher"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :plugins [[lein-cljfmt "0.5.7"]
            [lein-midje "3.1.3"]
            [codox "0.8.10"]]
  :codox {:src-dir-uri "https://github.com/agilecreativity/data-fetcher/blob/master/"
          :src-linenum-anchor-prefix "L"
          :defaults {:doc/format :markdown}}
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [clj-http "3.9.0"]
                 [akvo/fs "20180618-165206.5f3fb50b"]]
  :deploy-repositories [["releases" {:url           "https://clojars.org/repo"
                                     :sign-releases false
                                     :username      "agilecreativity"
                                     :password      :env/clojars_password}]]
  :profiles {:dev {:dependencies [[midje "1.6.3"]]}
             :set-version {:plugins [[lein-set-version "0.4.1"]]}})
