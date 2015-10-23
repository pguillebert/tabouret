(defproject tabouret "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 ;; HTTP client
                 [clj-http "2.0.0"]
                 ;; JSON parser
                 [cheshire "5.5.0"]
                 ;; Logging facilities
                 [com.taoensso/timbre "4.1.4"]
                 ;; Environment management
                 [environ "1.0.1"]
                 ;; Remote REPL to live server
                 [org.clojure/tools.nrepl "0.2.11"]
                 ;; Web router
                 [compojure "1.4.0"]
                 ;; Templating system
                 [selmer "0.9.2"]
                 ;; Ring middleware
                 [ring "1.4.0" :exclusions [ring/ring-jetty-adapter]]
                 [ring/ring-defaults "0.1.5"]
                 [metosin/ring-middleware-format "0.6.0"]
                 [metosin/ring-http-response "0.6.5"]
                 ;; markdown interpreter
                 [markdown-clj "0.9.75"]
                 ;; Web container
                 [org.immutant/web "2.1.0"]
                 ;; Java imports for text cleanup functions
                 [org.apache.commons/commons-lang3 "3.4"]]

  :min-lein-version "2.0.0"
  :jvm-opts ["-server"]

  :plugins [[lein-environ "1.0.1"]]
  :profiles
  {:uberjar {:omit-source true
             :env {:production true
                   :base-url "http://resttest.bench.co/transactions/"}
             :aot :all}
   :dev          [:project/dev :profiles/dev]
   :test         [:project/test :profiles/test]
   :project/dev  {:repl-options {:init-ns tabouret.core}
                  ;; when :nrepl-port is set the application
                  ;; starts the nREPL server on load
                  :env {:dev        true
                        :base-url "http://resttest.bench.co/transactions/"
                        :port       3000
                        :nrepl-port 7000}}
   :project/test {:env {:test       true
                        :base-url "http://resttest.bench.co/transactions/"
                        :port       3001
                        :nrepl-port 7001}}
   :profiles/dev {}
   :profiles/test {}}

  :main tabouret.http)
