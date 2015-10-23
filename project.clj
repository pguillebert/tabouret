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

  :main tabouret.http)
