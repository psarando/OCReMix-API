(ns org.ocremix.api.persistence.systems
  (:use [korma.core]))

(defn fetch-system-references
  [system-id]
  (select :system_reference
          (fields :url)
          (where {:system_id system-id})))

(defn fetch-system-games
  [system-id]
  (select :games
          (fields :id :name)
          (where {:system system-id})))

(defn fetch-system-composers
  [system-id]
  (select [:artists :a]
          (fields :a.id :a.name)
          (join [:composer_song :cs]
                {:a.id :cs.composer_id})
          (join [:songs :s]
                {:cs.song_id :s.id})
          (join [:games :g]
                {:g.id :s.game})
          (group :a.id :a.name)
          (where {:g.system system-id})))

(defn fetch-system-albums
  [system-id]
  (select [:albums :a]
          (fields :a.id :a.name)
          (join [:album_game :ag]
                {:ag.album_id :a.id})
          (join [:games :g]
                {:g.id :ag.game_id})
          (group :a.id :a.name)
          (where {:g.system system-id})))

(defn fetch-system-remixes
  [system-id]
  (select [:remixes :r]
          (fields :r.id :r.title)
          (join [:games :g]
                {:g.id :r.game})
          (where {:g.system system-id})))

