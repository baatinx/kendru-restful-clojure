(defproject kendru-restful-clojure "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [compojure "1.6.2"]
                 [ring/ring-core "1.8.1"]
                 [ring/ring-jetty-adapter "1.8.1"]
                 [ring/ring-json "0.5.0"]
                 [com.novemberain/monger "3.5.0"]
                 [buddy/buddy-core "1.6.0"]
                 [buddy/buddy-auth "2.2.0"]
                 [buddy/buddy-hashers "1.4.0"]
                 [buddy/buddy-sign "3.1.0"]]

  :plugins [[lein-ring "0.12.5"]]

  :ring {:handler kendru-restful-clojure.handler/app-routes
         :port 8000
         :auto-refresh? true
         :nrepl {:start? true
                 :host "localhost"
                 :port 9988}}
  :profiles {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                                  [ring/ring-mock "0.4.0"]]}
             :uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}}

  :main ^:skip-aot kendru-restful-clojure.core
  :target-path "target/%s")
