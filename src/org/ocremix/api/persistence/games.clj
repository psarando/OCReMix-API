(ns org.ocremix.api.persistence.games
  (:use [korma.core]))

(defn fetch-game-songs
  [game-id]
  (select [:songs :s]
          (fields :id :name)
          (aggregate (count :rs.remix_id) :remix_count)
          (join [:remix_song :rs]
                {:rs.song_id :s.id})
          (group :s.id :s.name)
          (where {:game game-id})))

(defn fetch-game-albums
  [game-id]
  (select [:albums :a]
          (fields :a.id :a.name)
          (join [:album_game :ag]
                {:ag.album_id :a.id})
          (where {:ag.game_id game-id})))

(defn fetch-game-remixes
  [game-id]
  (select [:remixes :r]
          (fields :r.id :r.title)
          (join [:remix_song :rs]
                {:rs.remix_id :r.id})
          (join [:songs :s]
                {:rs.song_id :s.id})
          (where {:s.game game-id})))

(defn fetch-game-composers
  [game-id]
  (select [:artists :a]
          (fields :a.id :a.name)
          (join [:composer_song :cs]
                {:a.id :cs.composer_id})
          (join [:songs :s]
                {:cs.song_id :s.id})
          (group :a.id :a.name)
          (where {:s.game game-id})))

(defn fetch-game-art
  [game-id]
  (select :game_art
          (fields :url)
          (where {:game_id game-id})))

(defn fetch-game-references
  [game-id]
  (select :game_reference
          (fields :url)
          (where {:game_id game-id})))

(defn fetch-game-chiptunes
  [game-id]
  (select :chiptunes
          (fields :id :name :size)
          (where {:game game-id})))

