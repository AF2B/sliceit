(ns sliceit.chunking.simple-chunking
  "Simple Chunking Algorithm
   ENDPOINT: /api/v1/chunking/simple
   METHOD: POST

   This endpoint accepts a JSON object with the following structure:
   {
     \"text\": \"Text to be chunked\",
     \"chunkSize\": 1000
   }

   The response will be a JSON object structured as follows:
   {
     \"data\": {
       \"chunks\": [\"chunk1\", \"chunk2\", ..., \"chunkN\"]
     }
   }

   The algorithm splits the input text into smaller chunks based on the specified chunk size.
   Each chunk will not exceed the specified size, and the last chunk may be smaller if the text length is not a multiple of the chunk size."
  (:require [ring.util.response :refer [response]]
            [clojure.string :as str]
            [clojure.core :as core]))

(defn chunk-greater-than-text?
  [text chunk-size]
  (let [size (count text)]
    (if (> chunk-size size)
      false
      true)))

(defn chunk-size-valid?
  [number text]
  (and (number? number)
       (pos? number)
       (integer? number)
       (chunk-greater-than-text? text number)))

(defn chunk-text-valid?
  [text]
  (and (string? text)
       (not (str/blank? text))))

(defn chunk-text
  [text chunk-size]
  {:pre [(chunk-size-valid? chunk-size text)
         (chunk-text-valid? text)]}
  (loop [i 0
         chunks []]
    (if (>= i (count text))
      chunks
      (recur (+ i chunk-size)
             (conj chunks (subs text i (min (+ i chunk-size) (count text))))))))

(defn simple-chunking
  [req]
  (let [{text :text chunk-size :chunk-size} req
        chunks (chunk-text text chunk-size)]
    (response {:chunks chunks})))
