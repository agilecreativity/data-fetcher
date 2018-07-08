(defproject data-fetcher "0.1.0"
  :description "Simple library to fetch remote data easily."
  :url "https://github.com/agilecreativity/data-fetcher"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :plugins [[lein-cljfmt "0.5.7"]]
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [commons-io/commons-io "2.6"]
                 [clj-http "3.9.0"]
                 [akvo/fs "20180618-165206.5f3fb50b"]])
