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
    
(defn- execute-turn-around-directive [mar-rover way-to-turn]
  (let [{:keys [towards]} mar-rover]
    (merge mar-rover {:towards (towards way-to-turn)})))
    
(defn- execute-right-directive [mar-rover]
  (let [right {
      :N :E 
      :E :S 
      :S :W 
      :W :N}]
    (execute-turn-around-directive mar-rover right))) 
 
(defn- execute-left-directive [mar-rover]
  (let [left {
      :N :W 
      :W :S 
      :S :E 
      :E :N}]
    (execute-turn-around-directive mar-rover left)))

(defn- execute-single-directive [mar-rover directive]
  (case directive
    \M (execute-move-directive mar-rover)
    \R (execute-right-directive mar-rover)
    \L (execute-left-directive mar-rover)))

(defn receive [mar-rover directives]
  (reduce execute-single-directive mar-rover directives))
 
(defn mar-rover-to-str [mar-rover]
  (let [{:keys [x y towards]} mar-rover]
       (format "I'm at %d %d, and towards %s" x y (name towards))))


     
(defn run [mar-info location directives]
  (let [bound mar-info 
        mar-rover (locate-on location)
        dir-func {
            \R execute-right-directive 
            \L execute-left-directive
            \M execute-move-directive}]            
    (reduce (fn [m d] ((dir-func d) m)) mar-rover directives)))