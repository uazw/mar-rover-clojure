(ns mars-rover.core
  (:gen-class))

(defn locate-on [locate-info]
  (let [locate-info-list (map str locate-info)
       [x y d &_] locate-info-list]
   {:x (Integer/parseInt x) :y (Integer/parseInt y) :towards (keyword d)}))

(defn- walk-in-bound-helper [bound current]
  (cond
    (> 0 current) 0
    (< bound current) bound
    :else current))
    
(defn- walk-in-bound [bound-info mar-rover]
  {:x (walk-in-bound-helper (:x bound-info) (:x mar-rover))
   :y (walk-in-bound-helper (:y bound-info) (:y mar-rover))})

(defn- execute-move-directive [bound-info mar-rover]
 (let [moves {
     :N {:y 1} 
     :S {:y -1} 
     :W {:x -1} 
     :E {:x 1}}
     move-without-bound (merge-with + ((:towards mar-rover) moves) mar-rover)]
      (merge mar-rover (walk-in-bound bound-info move-without-bound))))
    
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

(defn mar-rover-to-str [mar-rover]
  (let [{:keys [x y towards]} mar-rover]
       (format "I'm at %d %d, and towards %s" x y (name towards))))

(defn- bound-info [mar-info]
  (let [mar-info-list (map str mar-info)
       [x y &_] mar-info-list]
    {:x (Integer/parseInt x) :y (Integer/parseInt y)}))
     
(defn run [mar-info location directives]
  (let [bound (bound-info mar-info)
        mar-rover (locate-on location)
        dir-func {
            \R execute-right-directive 
            \L execute-left-directive
            \M (partial execute-move-directive bound)}]            
    (reduce (fn [m d] ((dir-func d) m)) mar-rover directives)))