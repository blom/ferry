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

(defn account
  [token]
  (request token :get "/account"))

(defn actions
  [token & [query-params]]
  (request token :get "/actions" nil query-params))

(defn action
  [token action-id]
  (request token :get (str "/actions/" action-id)))

(defn domains
  [token & [query-params]]
  (request token :get "/domains" nil query-params))

(defn create-domain
  [token params]
  (request token :post "/domains" params))

(defn domain
  [token domain-name]
  (request token :get (str "/domains/" domain-name)))

(defn delete-domain
  [token domain-name]
  (request token :delete (str "/domains/" domain-name)))

(defn records
  [token domain-name & [query-params]]
  (request token
           :get
           (str "/domains/" domain-name "/records")
           nil
           query-params))

(defn create-record
  [token domain-name params]
  (request token :post (str "/domains/" domain-name "/records") params))

(defn record
  [token domain-name record-id]
  (request token :get (str "/domains/" domain-name "/records/" record-id)))

(defn update-record
  [token domain-name record-id params]
  (request token
           :put
           (str "/domains/" domain-name "/records/" record-id)
           params))

(defn delete-record
  [token domain-name record-id]
  (request token :delete (str "/domains/" domain-name "/records/" record-id)))

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

(defn images
  [token & [query-params]]
  (request token :get "/images" nil query-params))

(defn image
  [token image-id-or-slug]
  (request token :get (str "/images/" image-id-or-slug)))

(defn image-actions
  [token image-id]
  (request token :get (str "/images/" image-id "/actions")))

(defn update-image
  [token image-id params]
  (request token :put (str "/images/" image-id) params))

(defn delete-image
  [token image-id]
  (request token :delete (str "/images/" image-id)))

(defmulti image-action
  (fn [& args]
    (class (second (rest args)))))

(defmethod image-action
  java.lang.Long
  [token image-id action-id]
  (request token :get (str "/images/" image-id "/actions/" action-id)))

(defmethod image-action
  :default
  [token image-id action & [params]]
  (request token
           :post
           (str "/images/" image-id "/actions")
           (assoc params :type action)))

(defn ssh-keys
  [token & [query-params]]
  (request token :get "/account/keys" nil query-params))

(defn create-ssh-key
  [token params]
  (request token :post "/account/keys" params))

(defn ssh-key
  [token key-id-or-fingerprint]
  (request token :get (str "/account/keys/" key-id-or-fingerprint)))

(defn update-ssh-key
  [token key-id-or-fingerprint params]
  (request token :put (str "/account/keys/" key-id-or-fingerprint) params))

(defn delete-ssh-key
  [token key-id-or-fingerprint]
  (request token :delete (str "/account/keys/" key-id-or-fingerprint)))

(defn regions
  [token]
  (request token :get "/regions"))

(defn sizes
  [token]
  (request token :get "/sizes"))
