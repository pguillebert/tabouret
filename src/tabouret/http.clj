(ns tabouret.http
  (:require [tabouret.routing :refer [app]]
            [immutant.web :as immutant]
            [clojure.tools.nrepl.server :as nrepl]
            [taoensso.timbre :as timbre]
            [environ.core :refer [env]]))

;; Most of this namspace is a stripped down version of Luminus sample project,
;; see http://www.luminusweb.net/

(defonce nrepl-server (atom nil))

(defn parse-port [port]
  (when port
    (cond
      (string? port) (Integer/parseInt port)
      (number? port) port
      :else          (throw (Exception. (str "invalid port value: " port))))))

(defn stop-nrepl []
  (when-let [server @nrepl-server]
    (nrepl/stop-server server)))

(defn start-nrepl
  "Start a network repl for debugging when the :nrepl-port is set in the environment."
  []
  (if @nrepl-server
    (timbre/error "nREPL is already running!")
    (when-let [port (env :nrepl-port)]
      (try
        (->> port
             (parse-port)
             (nrepl/start-server :port)
             (reset! nrepl-server))
        (timbre/info "nREPL server started on port" port)
        (catch Throwable t
          (timbre/error t "failed to start nREPL"))))))

(defonce http-server (atom nil))

(defn stop-app []
  (stop-nrepl)
  (when @http-server
    (immutant/stop @http-server)
    (reset! http-server nil))
  (shutdown-agents))

(defn start-app []
  (.addShutdownHook (Runtime/getRuntime) (Thread. stop-app))
  (start-nrepl)
  (reset! http-server (immutant/run app
                        :host "0.0.0.0"
                        :port (parse-port (or (env :port) 3000))))
  (timbre/info "server started on port:" (:port @http-server)))

(defn -main [& args]
  (start-app))
