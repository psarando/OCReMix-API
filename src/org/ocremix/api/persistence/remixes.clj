(ns org.ocremix.api.persistence.remixes
  (:use [korma.core]))

(defn fetch-mixpost
  [id]
  (first
   (select :mixposts
           (where {:remix_id id}))))

(defn fetch-remix-artists
  [id]
  (select [:artists :a]
          (fields :a.id :a.name)
          (join [:remix_artist :ra]
                {:a.id :ra.artist_id})
          (where {:ra.remix_id id})))

(defn fetch-remix-downloads
  [id]
  (select :remix_download
          (fields :url)
          (where {:remix_id id})))

(defn fetch-mixpost-evaluators
  [id]
  (select [:artists :a]
          (fields :a.id :a.name)
          (join [:mixpost_evaluator :me]
                {:a.id :me.artist_id})
          (where {:me.remix_id id})))

(defn fetch-remix-songs
  [id]
  (select [:songs :s]
          (fields :s.id :s.name)
          (join [:remix_song :rs]
                {:s.id :rs.song_id})
          (where {:rs.remix_id id})))

