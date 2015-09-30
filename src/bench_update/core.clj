(ns bench-update.core
  (:use [criterium.core]))

(defn update-in'
  ([m [k & ks] f]
   (if ks
     (assoc m k (update-in' (get m k) ks f))
     (assoc m k (f (get m k)))))

  ([m [k & ks] f arg]
   (if ks
     (assoc m k (update-in' (get m k) ks f arg))
     (assoc m k (f (get m k) arg))))

  ([m [k & ks] f arg arg2]
   (if ks
     (assoc m k (update-in' (get m k) ks f arg arg2))
     (assoc m k (f (get m k) arg arg2))))

  ([m [k & ks] f arg arg2 arg3]
   (if ks
     (assoc m k (update-in' (get m k) ks f arg arg2 arg3))
     (assoc m k (f (get m k) arg arg2 arg3))))

  ([m [k & ks] f arg arg2 arg3 & args]
   (if ks
     (assoc m k (apply update-in' (get m k) ks f arg arg2 arg3 args))
     (assoc m k (apply f (get m k) arg arg2 arg3 args)))))

(defmacro bench-update [f]
  `(do (prn "Bench: " '~f)
       (bench ~f)))

(defn run-bench
  []
  (bench-update (update {:a 1} :a inc))
  (bench-update (update-in {:a 1} [:a] inc))
  (bench-update (update-in' {:a 1} [:a] inc))

  (bench-update (update {:a 1} :a + 1))
  (bench-update (update-in {:a 1} [:a] + 1))
  (bench-update (update-in' {:a 1} [:a] + 1))

  (bench-update (update {:a 1} :a + 1 2))
  (bench-update (update-in {:a 1} [:a] + 1 2))
  (bench-update (update-in' {:a 1} [:a] + 1 2))

  (bench-update (update {:a 1} :a + 1 2 3))
  (bench-update (update-in {:a 1} [:a] + 1 2 3))
  (bench-update (update-in' {:a 1} [:a] + 1 2 3))

  (bench-update (update {:a 1} :a + 1 2 3 4))
  (bench-update (update-in {:a 1} [:a] + 1 2 3 4))
  (bench-update (update-in' {:a 1} [:a] + 1 2 3 4))

  (bench-update (update {:a 1} :a + 1 2 3 4 5))
  (bench-update (update-in {:a 1} [:a] + 1 2 3 4 5))
  (bench-update (update-in' {:a 1} [:a] + 1 2 3 4 5)))


;; (bench-update (update {:a 1} :a inc))          [128 ns]
;; (bench-update (update-in {:a 1} [:a] inc))     [349 ns] [100%]
;; (bench-update (update-in' {:a 1} [:a] inc))    [209 ns] [ 60%]

;; (bench-update (update {:a 1} :a + 1))          [56  ns]
;; (bench-update (update-in {:a 1} [:a] + 1))     [495 ns] [100%]
;; (bench-update (update-in' {:a 1} [:a] + 1))    [217 ns] [ 44%]

;; (bench-update (update {:a 1} :a + 1 2))        [168 ns]
;; (bench-update (update-in {:a 1} [:a] + 1 2))   [516 ns] [100%]
;; (bench-update (update-in' {:a 1} [:a] + 1 2))  [538 ns] [104%]


;; "Bench: " (update {:a 1} :a inc)
;; Evaluation count : 487964280 in 60 samples of 8132738 calls.
;; Execution time mean : 127.694336 ns
;; Execution time std-deviation : 4.674846 ns
;; Execution time lower quantile : 119.546318 ns ( 2.5%)
;; Execution time upper quantile : 137.675084 ns (97.5%)
;; Overhead used : 2.092406 ns

;; Found 3 outliers in 60 samples (5.0000 %)
;; low-severe 1 (1.6667 %)
;; low-mild 2 (3.3333 %)
;; Variance from outliers : 23.7856 % Variance is moderately inflated by outliers
;; "Bench: " (update-in {:a 1} [:a] inc)
;; Evaluation count : 178472640 in 60 samples of 2974544 calls.
;; Execution time mean : 349.039135 ns
;; Execution time std-deviation : 12.024758 ns
;; Execution time lower quantile : 331.020799 ns ( 2.5%)
;; Execution time upper quantile : 370.967045 ns (97.5%)
;; Overhead used : 2.092406 ns
;; "Bench: " (update-in' {:a 1} [:a] inc)
;; Evaluation count : 293780820 in 60 samples of 4896347 calls.
;; Execution time mean : 209.396680 ns
;; Execution time std-deviation : 7.722909 ns
;; Execution time lower quantile : 193.493440 ns ( 2.5%)
;; Execution time upper quantile : 224.730736 ns (97.5%)
;; Overhead used : 2.092406 ns

;; Found 3 outliers in 60 samples (5.0000 %)
;; low-severe 1 (1.6667 %)
;; low-mild 2 (3.3333 %)
;; Variance from outliers : 23.8035 % Variance is moderately inflated by outliers
;; "Bench: " (update {:a 1} :a + 1)
;; Evaluation count : 1080665340 in 60 samples of 18011089 calls.
;; Execution time mean : 55.706674 ns
;; Execution time std-deviation : 5.411293 ns
;; Execution time lower quantile : 49.413385 ns ( 2.5%)
;; Execution time upper quantile : 69.788030 ns (97.5%)
;; Overhead used : 2.092406 ns

;; Found 3 outliers in 60 samples (5.0000 %)
;; low-severe 2 (3.3333 %)
;; low-mild 1 (1.6667 %)
;; Variance from outliers : 68.6477 % Variance is severely inflated by outliers
;; "Bench: " (update-in {:a 1} [:a] + 1)
;; Evaluation count : 142646340 in 60 samples of 2377439 calls.
;; Execution time mean : 494.822565 ns
;; Execution time std-deviation : 213.314578 ns
;; Execution time lower quantile : 402.857718 ns ( 2.5%)
;; Execution time upper quantile : 906.085022 ns (97.5%)
;; Overhead used : 2.092406 ns

;; Found 5 outliers in 60 samples (8.3333 %)
;; low-severe 1 (1.6667 %)
;; low-mild 4 (6.6667 %)
;; Variance from outliers : 98.1978 % Variance is severely inflated by outliers
;; "Bench: " (update-in' {:a 1} [:a] + 1)
;; Evaluation count : 295372440 in 60 samples of 4922874 calls.
;; Execution time mean : 217.278096 ns
;; Execution time std-deviation : 8.348957 ns
;; Execution time lower quantile : 200.494655 ns ( 2.5%)
;; Execution time upper quantile : 231.170270 ns (97.5%)
;; Overhead used : 2.092406 ns
;; "Bench: " (update {:a 1} :a + 1 2)
;; Evaluation count : 373564260 in 60 samples of 6226071 calls.
;; Execution time mean : 168.094272 ns
;; Execution time std-deviation : 7.354156 ns
;; Execution time lower quantile : 158.627326 ns ( 2.5%)
;; Execution time upper quantile : 180.768292 ns (97.5%)
;; Overhead used : 2.092406 ns

;; Found 1 outliers in 60 samples (1.6667 %)
;; low-severe 1 (1.6667 %)
;; Variance from outliers : 30.3024 % Variance is moderately inflated by outliers
;; "Bench: " (update-in {:a 1} [:a] + 1 2)
;; Evaluation count : 123159120 in 60 samples of 2052652 calls.
;; Execution time mean : 515.573620 ns
;; Execution time std-deviation : 20.261350 ns
;; Execution time lower quantile : 478.210621 ns ( 2.5%)
;; Execution time upper quantile : 552.316972 ns (97.5%)
;; Overhead used : 2.092406 ns

;; Found 1 outliers in 60 samples (1.6667 %)
;; low-severe 1 (1.6667 %)
;; Variance from outliers : 25.4676 % Variance is moderately inflated by outliers
;; "Bench: " (update-in' {:a 1} [:a] + 1 2)
;; Evaluation count : 107584380 in 60 samples of 1793073 calls.
;; Execution time mean : 537.859187 ns
;; Execution time std-deviation : 40.273053 ns
;; Execution time lower quantile : 498.067793 ns ( 2.5%)
;; Execution time upper quantile : 583.885097 ns (97.5%)
;; Overhead used : 2.092406 ns

;; Found 2 outliers in 60 samples (3.3333 %)
;; low-severe 1 (1.6667 %)
;; low-mild 1 (1.6667 %)
;; Variance from outliers : 56.7528 % Variance is severely inflated by outliers
