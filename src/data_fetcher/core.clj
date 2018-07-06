(ns data-fetcher.core
  (:require
   [me.raynes.fs :refer [exists?
                         expand-home
                         file
                         mkdirs] :as fs]
   [me.raynes.fs.compression :refer [unzip]]
   [clj-http.client :refer [get] :as client]
   [clojure.string :as str]
   [clojure.java.io :as io]))

(defn- get-parent-dir
  [file]
  (-> file
      expand-home
      io/file
      .getParent))

(defn- make-parent-dir
  [file]
  (if-let [parent-dir (get-parent-dir file)]
    (mkdirs parent-dir)))

(defn resource-from-url
  "Extract the resource name from a given url."
  [url]
  (if-let [last-index-of-url (str/last-index-of url "/")]
    (subs url (inc last-index-of-url))))

#_(resource-from-url "https://s3.us-east-2.amazonaws.com/mxnet-scala/scala-example-ci/mnist/mnist.zip") ;;=> mnist.zip
#_(resource-from-url "invalid") ;;=> nil

(defn download-file
  [url output-file]
  (make-parent-dir output-file)
  (io/copy (:body (client/get url {:as :stream}))
           (java.io.File. (str (expand-home output-file)))))

#_(downlaod-file "https://s3.us-east-2.amazonaws.com/mxnet-scala/scala-example-ci/mnist/mnist.zip" "~/Downloads/data/mnist-demo.zip")
#_(download-file "http://nlp.stanford.edu/data/glove.6B.zip" "data/glove/glove.6B.zip")

(defn extract-file
  ([input-file]
   (extract-file input-file (get-parent-dir input-file)))
  ([input-file output-dir]
   (unzip (expand-home input-file)
          (expand-home output-dir))))

#_(extract-file "data/glove/glove.6B.zip")

(defn download-and-extract
  "Download a file from a given url to output-dir. Skip if the sample-file exist locally."
  [url output-dir sample-file & {:keys [output-name]
                                 :or {output-name (resource-from-url url)}}]
  (let [output-dir (expand-home output-dir)
        local-file (str/join java.io.File/separator [output-dir sample-file])
        output-file (str/join java.io.File/separator [output-dir output-name])]
    (when-not (exists? (file (expand-home local-file)))
      (println (format "File %s not exist locally, will be downloaded." local-file))
      (download-file url output-file)
      (when (.endsWith output-file ".zip")
        (println (format "Extract %s to directory %s" output-file output-dir))
        (extract-file output-file output-dir)))))

#_(download-and-extract "https://s3.us-east-2.amazonaws.com/mxnet-scala/scala-example-ci/mnist/mnist.zip"
                        "data/mnist/sample"
                        "train-images-idx3-ubyte")
