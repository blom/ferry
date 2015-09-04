(ns ferry.client
  (:require [org.httpkit.client :as http]
            [clojure.data.json :as json]))

(def ^:no-doc base-url "https://api.digitalocean.com/v2")

(defn ^:no-doc parse-json-body
  [response]
  (if (empty? (:body response))
    response
    (assoc response :body (json/read-str (:body response) :key-fn keyword))))

(defn ^:no-doc request
  [token method path & [params query-params]]
  (future
    (parse-json-body
      @(http/request {:method method
                      :url (str base-url path)
                      :query-params query-params
                      :headers {"Authorization" (str "Bearer " token)
                                "Content-Type" "application/json"}
                      :body (and params (json/write-str params))
                      :timeout 10000}))))

(defn create-droplet
  [token params]
  (request token :post "/droplets" params))

(defn droplet
  [token droplet-id]
  (request token :get (str "/droplets/" droplet-id)))

(defn droplets
  [token & [query-params]]
  (request token :get "/droplets" nil query-params))

(defn droplet-kernels
  [token droplet-id]
  (request token :get (str "/droplets/" droplet-id "/kernels")))

(defn droplet-snapshots
  [token droplet-id]
  (request token :get (str "/droplets/" droplet-id "/snapshots")))

(defn droplet-backups
  [token droplet-id]
  (request token :get (str "/droplets/" droplet-id "/backups")))

(defn droplet-actions
  [token droplet-id & [query-params]]
  (request token
           :get
           (str "/droplets/" droplet-id "/actions")
           nil
           query-params))

(defn delete-droplet
  [token droplet-id]
  (request token :delete (str "/droplets/" droplet-id)))

(defn droplet-neighbors
  ([token]
   (request token :get "/reports/droplet_neighbors"))
  ([token droplet-id]
   (request token :get (str "/droplets/" droplet-id "/neighbors"))))

(defn droplet-upgrades
  [token]
  (request token :get "/droplet_upgrades"))

(defn droplet-action
  [token droplet-id action & [params]]
  (request token
           :post
           (str "/droplets/" droplet-id "/actions")
           (assoc params :type action)))
