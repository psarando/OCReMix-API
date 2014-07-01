(ns org.ocremix.api.persistence.songs
  (:use [korma.core]))

(defn fetch-song-remixes
  [id]
  (select [:remixes :r]
          (fields :r.id :r.title)
          (join [:remix_song :rs]
                {:rs.remix_id :r.id})
          (where {:rs.song_id id})))

(defn fetch-song-composers
  [song-ids]
  (select [:artists :a]
          (fields :a.id :a.name)
          (join [:composer_song :cs]
                {:a.id :cs.composer_id})
          (where {:cs.song_id [in song-ids]})))

(defn fetch-song-ost-names
  [song-id]
  (select :song_ost_name
          (fields :name)
          (where {:song_id song-id})))

(defn fetch-song-aliases
  [song-id]
  (select :song_alias
          (fields :alias)
          (where {:song_id song-id})))

