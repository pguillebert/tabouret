# tabouret

> A small bench.

## Usage

The only prerequisite to run this app is the [Leiningen](http://leinigen.org)
standard build tool for clojure.

To install Leiningen :

    wget https://raw.github.com/technomancy/leiningen/stable/bin/lein
    chmod +x lein
    mv lein ~/bin
    lein

This project comes with a few unit test for the business logic, run them
using :

      lein test

To start the server in development mode, just run


      lein run

This will open a web server on the port 3000 (by default).
Go to [server](http://localhost:3000/) !

If you're curious, this will also open a REPL server
(defaults to port 7000). You can connect to this port
with a REPL client :

      lein repl :connect localhost:7000

Or with cider inside Emacs.

The REPL will allow you to redefine code interactively
during the life of the web server.


## Environment variables

Three variables are available to configure some parameters
of Tabouret.

| Variable        | Usage               | Default value |
|-----------------|---------------------|---------------|
| `:port`         | The webserver port  | 3000          |
| `:nrepl-port`   | The NREPL port      | none          |
| `:base-url`     | The service URL     | none          |

The project.clj contains default values for the different
environments.

These values can be changed at runtime ; see
[weavejester/environ](https://github.com/weavejester/environ)

## Building a deployable jar

This will package everything in a self-contained jar :

      lein do clean, uberjar

## Managing prod environment

To run the server in standalone production mode :

      java -Dport=3003 -jar target/tabouret-0.1.0-standalone.jar

This will run the server on port 3003, without a NREPL server.


## License

Copyright © 2015 Philippe Guillebert

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
