(ns repro
  (:require [clojure.pprint :as pprint]
            [datahike.api :as d]))

(defn repro
  []
  (let [config {:store {:backend :file
                        :path    "athens-dockerized_datahike-db/_data"}}
       conn   (d/connect config)]

    ;; Entity 194 has a reference to entity 258 in its :block/children
    (pprint/pprint (d/pull @conn '[*] 194))

    ;; But entity 258 doesn't seem to have any attributes.
    (pprint/pprint (d/pull @conn '[*] 258))
    (pprint/pprint (d/entity @conn 258))

    ;; Entity 258 doesn't seem to have any datoms at all.
    (pprint/pprint (filter (comp #{258} first) (d/datoms @conn :eavt)))

    (d/release conn)))

(defn run [_]
  (repro))

(comment
  (run nil))

