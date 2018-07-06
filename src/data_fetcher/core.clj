(ns data-fetcher.core
  (:require
   [me.raynes.fs.compression :as fsc]
   [me.raynes.fs :as fs])
  (:import
   [org.apache.commons.io FileUtils]
   [java.net URL]))

(def mnist-sample "https://s3.us-east-2.amazonaws.com/mxnet-scala/scala-example-ci/mnist/mnist.zip")

(defn download-file
  [remote-url local-path & {:keys [override?]
                            :or {override? false}}]
  (let [remote-file (URL. remote-url)
        local-file (fs/file (fs/expand-home local-path))]
    (if-not (or (fs/exists? remote-url) override?)
      (FileUtils/copyURLToFile remote-file local-file)
      (println "File already exist locally, will be skipped."))))

;; Note: the directory will be created automatically!
#_ (download-file mnist-sample
                  "/Users/bchoomnu/Downloads/zzzz/mnist-demo.zip")


;; TODO: handle different type of input e.g.
;; .tar.gz as well as .zip or .rar, etc!
(defn extract-file
  [input-file output-dir]
  (fsc/unzip (fs/expand-home input-file)
             (fs/expand-home output-dir)))

#_ (extract-file "~/Downloads/mnist-demo.zip"
                 "~/Downloads/TTTT")


(def sample "https://github.com/apache/incubator-mxnet/blob/master/contrib/clojure-package/examples/cnn-text-classification/get_data.sh")

(def neg-file "https://raw.githubusercontent.com/yoonkim/CNN_sentence/master/rt-polarity.neg")

#_
(spit "demo-xxx.txt" (slurp sample))
(spit "neg-file.txt" (slurp neg-file))
