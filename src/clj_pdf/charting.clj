(ns clj_pdf.charting  
  (:import [org.jfree.chart ChartFactory ChartFrame JFreeChart ChartUtilities]
            [org.jfree.chart.plot CategoryPlot PlotOrientation]
            [org.jfree.data.xy XYDataset XYSeries XYSeriesCollection]
            org.jfree.data.category.DefaultCategoryDataset
            org.jfree.data.general.DefaultPieDataset
            org.jfree.chart.renderer.category.StandardBarPainter                        
            java.text.SimpleDateFormat            
            [javax.swing JLabel JFrame ]))


(defn- bar-chart [{title       :title
                   horizontal? :horizontal
                   x-label     :x-label
                   y-label     :y-label} & data]
  (let [dataset (new DefaultCategoryDataset)]
    (doseq [[val name] data]
      (.setValue dataset (double val) y-label name))
    (let [chart (ChartFactory/createBarChart title x-label y-label dataset 
                                             (if horizontal? PlotOrientation/HORIZONTAL PlotOrientation/VERTICAL) 
                                             true true false)]       
      (.. chart getCategoryPlot getRenderer (setBarPainter (new StandardBarPainter)))
      chart)))

(defn- pie-chart [{title :title} & data]
  (let [dataset (new DefaultPieDataset)]
    (doseq [[name value] data]
      (.setValue dataset name (double value)))
    (ChartFactory/createPieChart title dataset true true false)))

(defn- line-chart [{title       :title
                    points?     :show-points
                    horizontal? :horizontal
                    time?       :time-series
                    format      :time-format 
                    x-label     :x-label
                    y-label     :y-label} & data]
  (let [dataset   (new XYSeriesCollection)
        formatter (if time? (new SimpleDateFormat 
                                 (or format "yyyy-MM-dd-HH:mm:ss")))]
    (doseq [[series-title & points] data]
      (let [series (new XYSeries series-title)]
        (doseq [[x y] points]
          (.add series 
            (if time? (.. formatter (parse x) getTime) (double x)) 
            (double y)))
        (.addSeries dataset series)))
    
    (let [chart (if time? (ChartFactory/createTimeSeriesChart title x-label y-label dataset true true false)
                          (ChartFactory/createXYLineChart title x-label y-label dataset 
                                                          (if horizontal? 
                                                            PlotOrientation/HORIZONTAL
                                                            PlotOrientation/VERTICAL) true true false))
          plot  (.getPlot chart)]
      
      (if points? (.. plot getRenderer (setBaseShapesVisible true)))
      (if time? (.. plot getDomainAxis (setDateFormatOverride formatter)))
      chart)))


(defn chart [params & items]
  (let [{type   :type
         width  :page-width
         height :page-height} params         
        out (new java.io.ByteArrayOutputStream)
        chart (condp = (when type (name type))
                "bar-chart"  (apply bar-chart params items)
                "pie-chart"  (apply pie-chart params items)
                "line-chart" (apply line-chart params items)
                (throw (new Exception (str "Unsupported chart type " type))))]
    
    (ChartUtilities/writeScaledChartAsPNG out chart (int width) (int height) 2 2)
    (.toByteArray out)))