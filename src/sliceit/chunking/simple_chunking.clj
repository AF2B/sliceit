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
  "Checks if the chunk size is greater than or equal to the text length.
     
     Parameters:
     - text: The input text to be analyzed.
     - chunk-size: The proposed size for each chunk.
     
     Returns:
     - true if the chunk size is greater than or equal to the text length.
     - false if the chunk size is smaller than the text length.
     
     This function is used to determine if the chunk size is appropriate for the given text."
  [text chunk-size]
  (let [size (count text)]
    (if (> chunk-size size)
      false
      true)))

(defn chunk-size-valid?
  "Validates if the chunk size is valid for the given text.
     
     Parameters:
     - number: The proposed chunk size.
     - text: The input text to be chunked.
     
     Returns:
     - true if the chunk size is a positive integer and not greater than the text length.
     - false otherwise.
     
     This function ensures that the chunk size meets all necessary criteria before chunking."
  [number text]
  (and (number? number)
       (pos? number)
       (integer? number)
       (chunk-greater-than-text? text number)))

(defn chunk-text-valid?
  "Checks if the input text is valid for chunking.
     
     Parameters:
     - text: The input text to be validated.
     
     Returns:
     - true if the text is a non-empty string.
     - false otherwise.
     
     This function ensures that the input text is suitable for the chunking process."
  [text]
  (and (string? text)
       (not (str/blank? text))))

(defn chunk-text
   "Splits the input text into chunks of the specified size.
     
     Parameters:
     - text: The input text to be chunked.
     - chunk-size: The size of each chunk.
     
     Preconditions:
     - The chunk size must be valid (checked by chunk-size-valid?).
     - The input text must be valid (checked by chunk-text-valid?).
     
     Returns:
     - A vector of text chunks, each not exceeding the specified chunk size.
     
     This function is the core of the chunking algorithm, dividing the text into appropriate segments."
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
  "Handles the chunking request for the API endpoint.
     
     Parameters:
     - req: A map containing the request data, including:
       - :text: The input text to be chunked.
       - :chunk-size: The desired size for each chunk.
     
     Returns:
     - A response map with a :chunks key containing the vector of text chunks.
     
     This function serves as the main entry point for the chunking service,
     processing the request and returning the chunked text."
  [req]
  (let [{text :text chunk-size :chunk-size} req
        chunks (chunk-text text chunk-size)]
    (response {:chunks chunks})))
