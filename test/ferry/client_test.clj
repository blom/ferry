(ns ferry.client-test
  (:require [clojure.test :refer :all]
            [ferry.client :refer :all]
            [midje.sweet :refer :all]
            [clojure.data.json :as json]))

(facts "parse-json-body"
       (fact "with body"
             (parse-json-body {:body "{\"abc\": 123}" :status 200})
             => {:body {:abc 123} :status 200})
       (fact "without body"
             (parse-json-body {:body "" :status 200})
             => {:body "" :status 200}))

(defn request-map
  [token method path body]
  {:method method
   :url (str base-url path)
   :query-params nil
   :headers {"Authorization" (str "Bearer " token)
             "Content-Type" "application/json"}
   :body body
   :timeout 10000})

(facts "request"
       (fact "without params"
             @(request "123" :get "/path") => {:foo "bar"}
             (against-background
               (org.httpkit.client/request
                 (request-map "123" :get "/path" nil))
               => (future {:foo "bar"})))
       (fact "with params"
             @(request "abc" :post "/path/a" {:two "one"}) => {:x 10}
             (against-background
               (org.httpkit.client/request
                 (request-map "abc" :post "/path/a" "{\"two\":\"one\"}"))
               => (future {:x 10}))))
