(ns rest.handler
  (:require [compojure.core :refer :all]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [cheshire.core :as json]
            [hiccup.middleware :refer (wrap-base-url)]))

(defroutes myroutes
  ; http://stackoverflow.com/questions/16706224/how-to-you-access-headers-inside-compojure-functions
  (GET "/" {:keys [headers params body] :as request} [] (do 
                                                           (prn request)
                                                           {:body "I'm here"}))

  ; Route to throw an exception. Just so you can see all the frames in ring/compojure
  (GET "/exception" {:keys [headers params body] :as request} [] (do 
                                                           (prn request)
                                                           (throw (Exception. "StackTrace!!!"))
                                                           {:body "I'm here"})) 
  ; The rest route itself
  (POST "/rest/message" {:keys [headers params body] :as req} []
    (do
      ;(prn req)
      (let [message (json/parse-string (slurp (:body req)) (fn [k] (keyword k)))]
        (prn  message)
        {:session {:foo (:message message)}
         :body (str "The message was " (:message message))})))

  ; The rest route includes a session demo. This one clears the session
  (GET "/logout" {:keys [headers params body] :as req} []
      {:session nil
       :body "Logged out"})

  ; We don't have any resources but this is how you can add a route to include them
  (route/resources "/")
  
  ; Any URL that is not mapped comes here
  (route/not-found  
                 {:body "That's not a page we know, sorry"})
)

; http://stackoverflow.com/questions/23341475/incorrect-routes-with-lein-ring-uberjar-deployed-to-tomcat
(def app-routes (wrap-base-url myroutes))

(def app
  (handler/site app-routes))

