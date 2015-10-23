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

This will open a web server on the port 3000 (by default). Go to
[server](http://localhost:3000/) !


## Building a deployable jar

This will package everything in a self-contained jar :

      lein uberjar

## Managing prod environment




## License

Copyright © 2015 Philippe Guillebert

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
