ferry
=====

[![Build Status](https://img.shields.io/travis/blom/ferry/master.svg)][travis]

Clojure library for [DigitalOcean's API][digitalocean-api].

Note that only droplet endpoints are supported currently.

[travis]: https://travis-ci.org/blom/ferry
[digitalocean-api]: https://developers.digitalocean.com/documentation/v2/

Examples
--------

``` clojure
(require '[ferry.client :as ferry])

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
```

All functions return a future and must be dereferenced to get the result:

``` clojure
@(ferry/droplets token)
```
