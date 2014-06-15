(ns org.ocremix.api.persistence.artists
  (:use [korma.core]))

(defn fetch-artist-composer-counts
  [artist-id]
  (let [games (subselect [:songs :s]
                         (fields :s.game)
                         (join [:composer_song :cs]
                               {:cs.song_id :s.id})
                         (group :s.game)
                         (where {:cs.composer_id artist-id}))]
    (first
     (select [:games :g]
             (aggregate (count :g.id) :count)
             (where {:g.id [in games]})))))

(defn fetch-artist-remix-counts
  [artist-id]
  (first
   (select [:artists :a]
           (aggregate (count :ra.remix_id) :count)
           (join [:remix_artist :ra]
                 {:a.id :ra.artist_id})
           (where {:a.id artist-id}))))

(defn fetch-artist-aliases
  [artist-id]
  (select :artist_alias
          (fields :alias)
          (where {:artist_id artist-id})))

(defn fetch-artist-group
  [artist-id]
  (select [:artists :a]
          (fields :a.id :a.name)
          (join [:artist_group :g]
                {:a.id :g.group_id})
          (where {:g.artist_id artist-id})))

(defn fetch-artist-art
  [artist-id]
  (select :artist_art
          (fields :url)
          (where {:artist_id artist-id})))

(defn fetch-artist-references
  [artist-id]
  (select :artist_reference
          (fields :url)
          (where {:artist_id artist-id})))

(defn fetch-artist-games
  [artist-id]
  (select [:games :g]
          (fields :g.id :g.name)
          (join [:songs :s]
                {:s.game :g.id})
          (join [:composer_song :cs]
                {:cs.song_id :s.id})
          (group :g.id :g.name)
          (where {:cs.composer_id artist-id})))

(defn fetch-artist-albums
  [artist-id]
  (let [games (subselect [:songs :s]
                         (fields :s.game)
                         (join [:composer_song :cs]
                               {:cs.song_id :s.id})
                         (group :s.game)
                         (where {:cs.composer_id artist-id}))]
    (select [:albums :a]
            (fields :a.id :a.name)
            (join [:album_game :ag]
                  {:ag.album_id :a.id})
            (where {:ag.game_id [in games]}))))

(defn fetch-artist-remixes
  [artist-id]
  (select [:remixes :r]
          (fields :r.id :r.title)
          (join [:remix_artist :ra]
                {:r.id :ra.remix_id})
          (where {:ra.artist_id artist-id})))

