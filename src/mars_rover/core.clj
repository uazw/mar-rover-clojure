(ns mars-rover.core
  (:gen-class))

(defn locate-on [locate-info]
  (let [locate-info-list (map str locate-info)
       [x y d &_] locate-info-list]
   {:x (Integer/parseInt x) :y (Integer/parseInt y) :towards (keyword d)}))

(defn- move-within-bound [current incrment]
  (let [result (+ current incrment)]
    (cond
      (> 0 result) 0
      (< 5 result) 5
      :else result)))
    
(defn- execute-move-directive 
 [mar-rover]
 (let [moves {
     :N {:y 1} 
     :S {:y -1} 
     :W {:x -1} 
     :E {:x 1}}]
   (merge-with move-within-bound ((:towards mar-rover) moves) mar-rover)))
    
(defn- execute-right-directive
 [mar-rover]
  (let 
   [{:keys [x y towards]} mar-rover
    right {:N :E :E :S :S :W :W :N}]
 {:x x :y y :towards (towards right)}))  
 
(defn- execute-left-directive
 [mar-rover]
  (let 
   [{:keys [x y towards]} mar-rover
    left {:N :W :W :S :S :E :E :N}]
 {:x x :y y :towards (towards left)}))
    
(defn- execute-single-directive 
 [mar-rover directive]
  (case directive
    \M (execute-move-directive mar-rover)
    \R (execute-right-directive mar-rover)
    \L (execute-left-directive mar-rover)))

(defn receive [mar-rover directives]
  (reduce execute-single-directive mar-rover directives))
 
(defn mar-rover-to-str [mar-rover]
  (let [{:keys [x y towards]} mar-rover]
       (format "I'm at %d %d, and towards %s" x y (name towards))))