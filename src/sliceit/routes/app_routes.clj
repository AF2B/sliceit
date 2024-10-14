(ns sliceit.routes.app-routes
  (:require 
   [compojure.core :refer [defroutes POST]]
   [ring.util.response :refer [response]]
   [sliceit.chunking.simple-chunking :refer [simple-chunking]]))

(defroutes app-routes
  (POST "/api/v1/chunking/simple" req (simple-chunking (:body req)))
  (POST "/api/v1/chunking/semantic" [] (response "<h1>TODO</h1>"))
  (POST "/api/v1/chunking/overlapping" [] (response "<h1>TODO</h1>"))
  (POST "/api/v1/chunking/adaptive" [] (response "<h1>TODO</h1>")))