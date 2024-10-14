(ns sliceit.core
  (:gen-class)
 (:require [ring.adapter.jetty :refer [run-jetty]]
           [sliceit.routes.app-routes :refer [app-routes]]
           [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
           [ring.middleware.json :refer [wrap-json-body wrap-json-response]]))

(def app
  (-> app-routes
      (wrap-json-body {:keywords? true})
      wrap-json-response
      (wrap-defaults (assoc-in site-defaults [:security :anti-forgery] false))))

(defn -main []
  (run-jetty app {:port 3000}))
