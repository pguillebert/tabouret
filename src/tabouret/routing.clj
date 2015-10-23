(ns tabouret.routing
  (:require [tabouret.layout :as layout]
            [taoensso.timbre :as timbre]
            [compojure.core :refer [defroutes routes wrap-routes GET]]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [site-defaults wrap-defaults]]
            [ring.middleware.format :refer [wrap-restful-format]]
            [clojure.pprint]
            [environ.core :refer [env]]
            [clojure.java.io :as io]))

(defn home-page []
  (layout/render "home.html"))

(defn about-page []
  (layout/render "about.html"
                 {:env (-> env
                           (clojure.pprint/pprint)
                           (with-out-str))}))

(defn apidoc-page []
  (layout/render "apidoc.html"
                 {:docs (-> "doc/API.md" io/resource slurp)}))

(defroutes app-routes
  (GET "/" [] (home-page))
  (GET "/about" [] (about-page))
  (GET "/apidoc" [] (apidoc-page))

  ;; fallthrough, return a 404 not found
  (route/not-found
   (:body
    (layout/error-page {:status 404
                        :title "page not found"}))))

(defn wrap-internal-error [handler]
  (fn [req]
    (try
      (handler req)
      (catch Throwable t
        (timbre/error t)
        (layout/error-page {:status 500
                            :title "Something very bad has happened!"
                            :message "We've dispatched a team of highly trained gnomes to take care of the problem."})))))

(defn wrap-base [handler]
  (-> handler
      (wrap-restful-format {:formats [:json-kw :transit-json :transit-msgpack]})
      (wrap-defaults (-> site-defaults
                         (assoc-in [:security :anti-forgery] false)
                         (dissoc :session)))
      wrap-internal-error))

(def app (wrap-base #'app-routes))
