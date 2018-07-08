(ns data-fetcher.core
  (:require
   [me.raynes.fs.compression :as fsc]
   [me.raynes.fs :as fs]
   [clj-http.client :as client]
   [clojure.java.shell :refer [sh]]
   [clojure.java.io :as io])
  (:import
   [org.apache.commons.io FileUtils]
   [java.net URL]))

;; TODO: handle different type of input e.g.
;; .tar.gz as well as .zip or .rar, etc!
(defn extract-file
  [input-file output-dir]
  (fsc/unzip (fs/expand-home input-file)
             (fs/expand-home output-dir)))

#_ (extract-file "~/Downloads/mnist-demo.zip" "~/Downloads/TTTT")

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
#_ (download-file mnist-sample "/Users/bchoomnu/Downloads/zzzz/mnist-demo.zip")

;; TODO: cleanup and adjust the api
;; - split into two, download-if-not-exist without extraction
(defn download-if-not-exist
  [url]
  (let [output-dir "data"
        sample-file (str/join java.io.File/separator [output-dir "train-images-idx3-ubyte"])
        output-file (str/join java.io.File/separator [output-dir "mnist.zip"])]
    (when-not (.exists (io/file sample-file))
      (println (format "File %s not exist locally, will be downloaded." sample-file))
      (download-file url output-file)
      (when (.endsWith output-file ".zip")
        (println (format "Extract %s to directory %s" output-file output-dir))
        (extract-file output-file output-dir)))))



;; ======================== ;;
;; Move this to scratch-library for later re-use!


(def sample "https://github.com/apache/incubator-mxnet/blob/master/contrib/clojure-package/examples/cnn-text-classification/get_data.sh")
(def neg-file "https://raw.githubusercontent.com/yoonkim/CNN_sentence/master/rt-polarity.neg")

;; https://stackoverflow.com/questions/8281082/downloading-image-in-clojure
(defn blurp [f]
  (let [dest (java.io.ByteArrayOutputStream.)]
    (with-open [src (io/input-stream f)]
      (io/copy src dest))
    (.toByteArray dest)))


#_ (blurp "http://www.lisperati.com/lisplogo_256.png")
#_ (use 'clojure.test)
#_ (deftest blurp-test
     (testing "basic operation"
       (let [src (java.io.ByteArrayInputStream. (.getBytes "foo" "utf-8"))]
         (is (= "foo" (-> (blurp src) (String. "utf-8")))))))

#_ (blurp "http://www.lisperati.com/lisplogo_256.png")
;; https://stackoverflow.com/questions/11321264/saving-an-image-form-clj-http-request-to-file

(defn copy-file
  [url output-file]
  (clojure.java.io/copy
   (:body (client/get url {:as :stream}))
   (java.io.File. output-file)))

#_ (copy-file "http://placehold.it/350x150" "testbed/aaa/test-file-1.gif")
#_ (download-file "http://placehold.it/350x150" "~/testbed/aaa/test-file-1.gif")

#_ (copy-file "http://placehold.it/350x150" "test-file-1.gif")
#_ (copy-file "http://www.lisperati.com/lisplogo_256.png" "lisplogo_256.png")
#_ (copy-file mnist-sample "mnist.zip")
#_ (copy-binary-file neg-file "rt-polarity.neg")

;; NOTE: simpler version of the download!
(defn download-sample [uri file]
  (with-open [in (io/input-stream uri)
              out (io/output-stream file)]
    (io/copy in out)))

#_ (download-sample neg-file "rt-polarity.neg")
#_ (download-sample mnist-sample "mnist.zip")
