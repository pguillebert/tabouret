(ns tabouret.routing
  (:require [tabouret.core :as core]
            [tabouret.layout :as layout]
            [taoensso.timbre :as timbre]
            [compojure.core :refer [defroutes routes wrap-routes GET]]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [site-defaults wrap-defaults]]
            [ring.middleware.format :refer [wrap-restful-format]]
            [ring.util.http-response :refer [ok]]
            [clojure.pprint :as pp]
            [environ.core :refer [env]]
            [clojure.java.io :as io]))

(defn home-page []
  (layout/render "home.html"))

(defn about-page []
  (layout/render "about.html"
                 {:env (-> env (pp/pprint) (with-out-str))}))

(defn apidoc-page []
  (layout/render "apidoc.html"
                 {:docs (-> "doc/API.md" io/resource slurp)}))

(defroutes app-routes
  ;; HTML routes
  (GET "/" [] (home-page))
  (GET "/about" [] (about-page))
  (GET "/apidoc" [] (apidoc-page))

  ;; REST routes
  (GET "/transactions" [raw]
       (ok {:transactions (core/get-clean-transactions raw)}))

  (GET "/balance/:initial" [initial raw]
       (ok {:balance (core/get-balance
                      (bigdec initial)
                      (core/get-clean-transactions raw))}))

  (GET "/balance" [raw]
       (ok {:balance (core/get-balance
                      (core/get-clean-transactions raw))}))

  (GET "/expenses-by-ledger" [raw detailed]
       (ok {:expenses-by-ledger (core/expenses-by-ledger detailed
                                 (core/get-clean-transactions raw))}))

  (GET "/balance-by-day/:initial" [initial raw]
       (ok {:balance-by-day (core/balance-by-day
                             (bigdec initial)
                             (core/get-clean-transactions raw))}))

  (GET "/balance-by-day" [raw]
       (ok {:balance-by-day (core/balance-by-day
                             (core/get-clean-transactions raw))}))

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
                            :message (str "We've dispatched a team of highly"
                                          " trained gnomes to take care of"
                                          " the problem.")})))))

(defn wrap-base [handler]
  (-> handler
      (wrap-restful-format {:formats [:json-kw]})
      (wrap-defaults (-> site-defaults
                         (assoc-in [:security :anti-forgery] false)
                         (dissoc :session)))
      wrap-internal-error))

(def app (wrap-base #'app-routes))
