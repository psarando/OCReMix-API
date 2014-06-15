(ns org.ocremix.api.persistence.albums
  (:use [korma.core]))

(defn fetch-album-games
  [album-id]
  (select [:games :g]
          (fields :g.id :g.name)
          (join [:album_game :ag]
                {:ag.game_id :g.id})
          (where {:ag.album_id album-id})))

(defn fetch-album-art
  [album-id]
  (select :album_art
          (fields :url)
          (where {:album_id album-id})))

(defn fetch-album-references
  [album-id]
  (select :album_reference
          (fields :url)
          (where {:album_id album-id})))

(defn fetch-album-artists
  [album-id]
  (select [:artists :a]
          (fields :a.id :a.name)
          (join [:album_artist :aa]
                {:a.id :aa.artist_id})
          (where {:aa.album_id album-id})))

(defn fetch-album-composers
  [album-id]
  (select [:artists :a]
          (fields :a.id :a.name)
          (join [:composer_song :cs]
                {:a.id :cs.composer_id})
          (join [:songs :s]
                {:cs.song_id :s.id})
          (join [:album_game :ag]
                {:ag.game_id :s.game})
          (group :a.id :a.name)
          (where {:ag.album_id album-id})))

(defn fetch-album-remixes
  [album-id]
  (select :remixes
          (fields :id :title)
          (where {:album album-id})))

