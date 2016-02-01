(ns planetlab.core
  (:use seesaw.core)
  (:import (java.awt Toolkit))
  (:require [clojure.java.io :as io])
  (:gen-class))

(def bar-tools (flow-panel :hgap 10 :border 0
                           :items [(button :icon (icon (io/resource "openiconic/png/account-login-2x.png"))  :border 0)
                                   (button :icon (icon (io/resource "openiconic/png/account-logout-2x.png")) :border 0)]))
(def bar-status (toolbar :orientation :horizontal))
(def bar-aspects (toolbar :orientation :vertical))

(def panel-orgs (flow-panel))

(def panel-contents (tabbed-panel :placement :top :overflow :scroll))

(def panel-repl (flow-panel ))

(def action-new (menu-item :text "New"))

(def bar-menu (menubar :items
                       [(menu :text "File" :items [action-new])]))

(defn -main [& args]
  (let [dimension (.getScreenSize (Toolkit/getDefaultToolkit))
        scr-width (.getWidth dimension)
        scr-height (.getHeight dimension)
        frm-width (* 0.6 scr-width)
        frm-height (* 0.8 scr-height)
        center-x (/ (- scr-width frm-width) 2)
        center-y (/ (- scr-height frm-height) 2)]
    (invoke-later
      (-> (frame :title "PlanetLab"
                 :size [frm-width :by frm-height]
                 :on-close :exit
                 :menubar bar-menu
                 :content (border-panel :hgap 10 :vgap 10 :border 10
                                        :north  bar-tools
                                        :south  bar-status
                                        :west   bar-aspects
                                        :center (top-bottom-split
                                                  (left-right-split panel-orgs panel-contents :divider-location 0.2 :border 0)
                                                  panel-repl
                                                  :divider-location 0.8 :border 0)))
          (move-to! center-x center-y)
          show!))))