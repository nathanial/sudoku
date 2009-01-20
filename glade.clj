(ns glade 
  (:use clojure.contrib.prxml))

(defn key-to-sym [key]
  (. (str key) (substring 1)))

(defn dec-zero [n] 
  (if (<= n 0) 0
      (dec n)))

(defn property [name value]
  (let [name (if (keyword? name) (key-to-sym name) name)
	value (if (keyword? value) (key-to-sym value) value)]
    (cond 
     (= value false) [:property {:name name} "False"]
     (= value true) [:property {:name name} "True"]
     :else [:property {:name name} value])))

(defn widget [class id name-value-pairs & body]
  (let [body (if (list? body) (vec body) body)]
    (vec 
     `(:widget {:class ~class :id ~id}
	       ~@(for [[name value] name-value-pairs]
		   (property name value))
	       ~@body))))

(defn parent-widget [class id name-value-pairs & body]
  (let [body (if (list? body) (vec body) body)]
    (widget class id name-value-pairs 
      (map (fn [element] [:child element]) body))))

(def starting-row)
(def starting-col)

(defn sudoku-box [row col]
  [:child 
   (widget "GtkEntry" (str "entry" (+ starting-row row) (+ starting-col col))
	   [[:visible true]
	    [:can_focus true]
	    [:max_length 1]
	    [:has_frame false]
	    [:width_chars 2]])
   (let [left-col (dec-zero col)
	 top-row (dec-zero row)]
     [:packing 
      (when (not (= left-col 0)) (property :left_attach left-col))
      (when (not (= col 1)) (property :right_attach col))
      (when (not (= top-row 0)) (property :top_attach top-row))
      (when (not (= row 1)) (property :bottom_attach row))
      (property :x_options "GTK_EXPAND")
      (property :y_options "GTK_EXPAND")])])

(defn sudoku-square-row [row]
  (for [x (range 1 4)]
    (sudoku-box row x)))

(defn sudoku-table [n]
  (widget "GtkTable" (str "table" n)
	  [[:visible true]
	   [:n_rows 3]
	   [:n_columns 3] 
	   [:column_spacing 1]
	   [:row_spacing 1]]
    (for [x (range 1 4)]
      (sudoku-square-row x))))

(defn test-table []
  (binding [starting-row 0, starting-col 0]
    (prxml
     [:glade-interface
      (parent-widget "GtkWindow" "appwindow"
		     [[:resizable false]
		    [:window_position "GTK_WIN_POS_CENTER"]]
	(sudoku-table 1))])))

(defn horizontal-box [name body]
  (apply widget "GtkHBox" name [[:visible true]] body))

(defn vertical-box [name body]
  (apply widget "GtkVBox" name [[:visible true]] body))

(defn glade-interface [root-widget]
  (prxml [:glade-interface root-widget]))

(defn window [name properties child]
  (parent-widget "GtkWindow" name properties child))

(defn vertical-separator [n]
  (widget "GtkVSeparator" (str "vseparator" n) [[:visible true]]))

(defn horizontal-separator [n]
  (widget "GtkHSeparator" (str "hseparator" n) [[:visible true]]))
  
(defn sudoku []
  (binding [starting-row 0 starting-col 0]
    (with-local-vars [counter 0]
      (glade-interface
       (window "appwindow" 
	       [[:resizable false]
		[:window_position "GTK_WIN_POS_CENTER"]]
	       (vertical-box 
		"topmost-vbox"
		(loop [x 0, row-acc []]
		  (if (>= x 3)
		    row-acc
		    (recur 
		     (inc x)
		     (conj row-acc
			   [:child
			    (horizontal-box (str "row" x)
			      (loop [y 0, col-acc []]
				(if (>= y 3)
				  col-acc
				  (do
				    (var-set counter (inc (var-get counter)))
				    (set! starting-row (* x 3))
				    (set! starting-col (* y 3))
				    (recur (inc y)
					   (conj col-acc
						 [:child (sudoku-table (var-get counter))]
						   [:child (vertical-separator y)
						    [:packing 
						     (property :expand false)
						     (property :position (inc (* 2 y)))]]))))))]
			   [:child (horizontal-separator x)]))))))))))
