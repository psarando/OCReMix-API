(ns org.ocremix.api.persistence.organizations
  (:use [korma.core]))

(defn fetch-org-references
  [org-id]
  (select :organization_reference
          (fields :url)
          (where {:organization_id org-id})))

(defn fetch-org-systems
  [org-id]
  (select :systems
          (fields :id :name)
          (where {:publisher org-id})))

(defn fetch-org-games
  [org-id]
  (select [:games :g]
          (fields :g.id :g.name)
          (where {:publisher org-id})))

(defn fetch-org-composers
  [org-id]
  (select [:artists :a]
          (fields :a.id :a.name)
          (join [:composer_song :cs]
                {:a.id :cs.composer_id})
          (join [:songs :s]
                {:cs.song_id :s.id})
          (join [:games :g]
                {:g.id :s.game})
          (group :a.id :a.name)
          (where {:g.publisher org-id})))

(defn fetch-org-albums
  [org-id]
  (select [:albums :a]
          (fields :a.id :a.name)
          (join [:album_game :ag]
                {:ag.album_id :a.id})
          (join [:games :g]
                {:g.id :ag.game_id})
          (group :a.id :a.name)
          (where {:g.publisher org-id})))

(defn fetch-org-remixes
  [org-id]
  (select [:remixes :r]
          (fields :r.id :r.title)
          (join [:games :g]
                {:g.id :r.game})
          (where {:g.publisher org-id})))

