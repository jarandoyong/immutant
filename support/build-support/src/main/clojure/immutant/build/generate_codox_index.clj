;; Copyright 2008-2013 Red Hat, Inc, and individual contributors.
;; 
;; This is free software; you can redistribute it and/or modify it
;; under the terms of the GNU Lesser General Public License as
;; published by the Free Software Foundation; either version 2.1 of
;; the License, or (at your option) any later version.
;; 
;; This software is distributed in the hope that it will be useful,
;; but WITHOUT ANY WARRANTY; without even the implied warranty of
;; MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
;; Lesser General Public License for more details.
;; 
;; You should have received a copy of the GNU Lesser General Public
;; License along with this software; if not, write to the Free
;; Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
;; 02110-1301 USA, or see the FSF site: http://www.fsf.org.

(ns immutant.build.generate-codox-index
  (:require [clojure.java.io :as io]
            [clojure.walk    :as walk]
            [codox.utils     :as cu]
            [codox.reader    :as cr])
  (:import java.io.File)
  (:gen-class))


(defn -main [& args]
  (spit (io/file "target/codox-index.clj")
        (pr-str
         (walk/postwalk
          #(if (instance? File %) (.getCanonicalPath %) %)
          (-> (cr/read-namespaces "src/main/clojure/")
              (cu/add-source-paths ["src/main/clojure"])))))
  (shutdown-agents))