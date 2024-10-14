(defproject sliceit "0.1.0-SNAPSHOT"
  :description "Chunking api"
:url "https://github.com/af2b/sliceit"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [ring/ring-core "1.12.2"]
                 [ring/ring-jetty-adapter "1.12.2"]
                 [ring/ring-defaults "0.3.3"]
                 [compojure "1.7.1"]
                 [cheshire "5.13.0"]
                 [ring/ring-json "0.5.1"]]
  :main ^:skip-aot sliceit.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
