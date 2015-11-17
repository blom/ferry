ferry
=====

[![Build Status](https://img.shields.io/travis/blom/ferry/master.svg)][travis]
[![Clojars Project](https://img.shields.io/clojars/v/ferry.svg)][clojars]

Clojure library for [DigitalOcean's API][digitalocean-api].

[travis]: https://travis-ci.org/blom/ferry
[clojars]: https://clojars.org/ferry
[digitalocean-api]: https://developers.digitalocean.com/documentation/v2/

Examples
--------

``` clojure
(require '[ferry.client :as ferry])

(ferry/account token)

(ferry/actions token)
(ferry/actions token {:per_page 1 :page 1})
(ferry/action token action-id)

(ferry/domains token)
(ferry/domains token {:per_page 1 :page 1})
(ferry/create-domain token {:name name :ip_address ip-address})
(ferry/domain token domain-name)
(ferry/delete-domain token domain-name)

(ferry/records token domain-name)
(ferry/records token domain-name {:per_page 1 :page 1})
(ferry/create-record token domain-name {:type type :name name :data data})
(ferry/record token domain-name record-id)
(ferry/update-record token domain-name record-id {:data data})
(ferry/delete-record token domain-name record-id)

(ferry/create-droplet token {:name name
                             :image image
                             :region region
                             :size size})
(ferry/droplet token droplet-id)
(ferry/droplets token)
(ferry/droplets token {:per_page 1 :page 1})
(ferry/droplet-kernels token droplet-id)
(ferry/droplet-snapshots token droplet-id)
(ferry/droplet-backups token droplet-id)
(ferry/droplet-actions token droplet-id)
(ferry/droplet-actions token droplet-id {:per_page 1 :page 1})
(ferry/delete-droplet token droplet-id)
(ferry/droplet-neighbors token)
(ferry/droplet-neighbors token droplet-id)
(ferry/droplet-upgrades token)
(ferry/droplet-action token droplet-id :disable_backups)
(ferry/droplet-action token droplet-id :resize {:size size})

(ferry/images token)
(ferry/images token {:type "application"})
(ferry/images token {:type "distribution"})
(ferry/images token {:private true})
(ferry/images token {:per_page 1 :page 1})
(ferry/image token image-id-or-slug)
(ferry/update-image token image-id {:name "foo"})
(ferry/delete-image token image-id)
(ferry/image-action token image-id action-id)
(ferry/image-action token image-id :transfer {:region "ams2"})

(ferry/ssh-keys token)
(ferry/ssh-keys token {:per_page 1 :page 1})
(ferry/create-ssh-key token {:name name :public_key public-key})
(ferry/ssh-key token key-id-or-fingerprint)
(ferry/update-ssh-key token key-id-or-fingerprint {:name "key name"})
(ferry/delete-ssh-key token key-id-or-fingerprint)

(ferry/regions token)

(ferry/sizes token)
```

All functions return a future and must be dereferenced to get the result:

``` clojure
@(ferry/droplets token)
```
