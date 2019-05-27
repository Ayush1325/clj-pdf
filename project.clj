(defproject clj-pdf "2.3.4"
  :description "PDF generation library"
  :url "https://github.com/yogthos/clj-pdf"

  :license {:name         "GNU Lesser General Public License - v 3"
            :url          "http://www.gnu.org/licenses/lgpl.html"
            :distribution :repo}

  :dependencies [[org.clojure/clojure "1.10.0" :scope "provided"]
                 [com.github.librepdf/openpdf "1.2.17"]
                 [commons-codec "1.12"]
                 [org.jfree/jfreechart "1.0.19"]
                 [org.apache.xmlgraphics/batik-bridge "1.11"]
                 [org.apache.xmlgraphics/batik-anim "1.11"]
                 [org.apache.xmlgraphics/batik-codec "1.11"]
                 [org.apache.xmlgraphics/xmlgraphics-commons "2.3"]]
  :source-paths ["src/clj"]
  :profiles {:dev {:global-vars  {*warn-on-reflection* true}
                   :dependencies [[environ "1.0.2"]]
                   :plugins      [[lein-marginalia "0.7.1"]]}})
