(require '[clojure.string :as str])

;; load data
(def lista [])
(use 'clojure.java.io)
(with-open [rdr (reader "src/DatasetMX2.csv")]
  (doseq [line (line-seq rdr)]
    (def fila (str/split line #","))
    (def lista (conj lista fila))
    )
  )



;; clean data

;; filter base size
(def listaTemp [])
(defn filtro []
    (dotimes [x (- (count lista) 1)]
      (def valor (get (get lista (+ x 1))9))               ;; get 9th element from row
      (cond (not (empty? valor))
        (do
          (def data (get lista (+ x 1)))                    ;; get row where base size is not null
          (def listaTemp (conj listaTemp data))             ;; add data to new list
          )
        )
      )
  )
(filtro)
(def lista listaTemp )                                      ;; set lista equal to listaTemp
(println (count lista))                                       ;; print total elements for new lista

;;set column valor to 0 if null
(defn set0 []
  (dotimes [x (- (count lista) 1)]
    (def valor (get (get lista (+ x 1))10))
    (cond (empty? valor)
          (do
              (def rowTemp (assoc (get lista (+ x 1)) 10 0)) ;; temporal row without null values
              (def lista (assoc lista (+ x 1) rowTemp))     ;; assign temporal row to old row
              )


      )
    )
  )
(set0)

;; delete Answer option label column
(defn deleteAO []
  (def temporal [])
  (dotimes [x (- (count lista) 1)]

    (def part1 (subvec (get lista (+ x 1)) 0 8))
    (def part2 (subvec (get lista (+ x 1)) 9))

    (def valor (conj part1 part2))
    (def temporal (conj temporal valor))
    )
  (def lista temporal)
  (print lista)
  (print (count lista))
  )


(deleteAO )


;; divide test and training set
(def n (count lista))
(println "n")
(println n)

(def per75 (Math/round (* n 0.75)) )                        ;; 75% del total
(println "per75")
(println per75)

(def listaDiv (partition-all per75 lista))                  ;; divide in sets of 75%

(def training (nth listaDiv 0))                             ;; 75% of data used for training
(def testing (nth listaDiv 1))                              ;; 25% of data used for test





