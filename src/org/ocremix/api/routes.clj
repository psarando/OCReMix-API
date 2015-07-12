(ns org.ocremix.api.routes
  (:use [org.ocremix.api.domain]
        [compojure.api.sweet])
  (:require [org.ocremix.api.entities.album :as album]
            [org.ocremix.api.entities.artist :as artist]
            [org.ocremix.api.entities.chiptune :as chiptune]
            [org.ocremix.api.entities.game :as game]
            [org.ocremix.api.entities.organization :as organization]
            [org.ocremix.api.entities.remix :as remix]
            [org.ocremix.api.entities.song :as song]
            [org.ocremix.api.entities.system :as system]
            [org.ocremix.api.listings.albums :as albums]
            [org.ocremix.api.listings.artists :as artists]
            [org.ocremix.api.listings.chiptunes :as chiptunes]
            [org.ocremix.api.listings.games :as games]
            [org.ocremix.api.listings.organizations :as organizations]
            [org.ocremix.api.listings.remixes :as remixes]
            [org.ocremix.api.listings.songs :as songs]
            [org.ocremix.api.listings.systems :as systems]
            [org.ocremix.api.service :as service]))

(defroutes* remix-routes
  (context* "/remixes" []
    :tags ["remixes"]

    (GET* "/" []
      :summary      "List all remixes."
      :query-params [{sort-order :- (sort-order-docs remixes/sort-fields "id") "id"}
                     {sort-dir :- (sort-dir-docs "DESC") "DESC"}
                     {limit :- limit-docs 50}
                     {offset :- offset-docs 0}
                     :as params]
      (service/trap #(remixes/get-remixes params)))

    (GET* "/:remix-id" [remix-id]
      :summary "Get a single remix."
      (service/trap #(remix/get-remix remix-id)))))

(defroutes* song-routes
  (context* "/songs" []
    :tags ["songs"]

    (GET* "/" []
      :summary      "List all songs."
      :query-params [{sort-order :- (sort-order-docs songs/sort-fields "name") "name"}
                     {sort-dir :- (sort-dir-docs "ASC") "ASC"}
                     {limit :- limit-docs 50}
                     {offset :- offset-docs 0}
                     :as params]
      (service/trap #(songs/get-songs params)))

    (GET* "/:song-id" [song-id]
      :summary     "Get a single song."
      :path-params [song-id :- Long]
      (service/trap #(song/get-song song-id)))

    (GET* "/:song-id/remixes" [song-id]
      :summary     "Get remixes for a single song."
      :path-params [song-id :- Long]
      (service/trap #(song/get-song-remixes song-id)))))

(defroutes* game-routes
  (context* "/games" []
    :tags ["games"]

    (GET* "/" []
      :summary      "List all games."
      :query-params [{sort-order :- (sort-order-docs games/sort-fields "name") "name"}
                     {sort-dir :- (sort-dir-docs "ASC") "ASC"}
                     {limit :- limit-docs 50}
                     {offset :- offset-docs 0}
                     :as params]
      (service/trap #(games/get-games params)))

    (GET* "/:game-id" [game-id]
      :summary     "Get a single game."
      :path-params [game-id :- Long]
      (service/trap #(game/get-game game-id)))

    (GET* "/:game-id/songs" [game-id]
      :summary     "Get songs for a single game."
      :path-params [game-id :- Long]
      (service/trap #(game/get-game-songs game-id)))

    (GET* "/:game-id/albums" [game-id]
      :summary     "Get albums for a single game."
      :path-params [game-id :- Long]
      (service/trap #(game/get-game-albums game-id)))

    (GET* "/:game-id/remixes" [game-id]
      :summary     "Get remixes for a single game."
      :path-params [game-id :- Long]
      (service/trap #(game/get-game-remixes game-id)))))

(defroutes* album-routes
  (context* "/albums" []
    :tags ["albums"]

    (GET* "/" []
      :summary      "List all albums."
      :query-params [{sort-order :- (sort-order-docs albums/sort-fields "release_date") "release_date"}
                     {sort-dir :- (sort-dir-docs "DESC") "DESC"}
                     {limit :- limit-docs 50}
                     {offset :- offset-docs 0}
                     :as params]
      (service/trap #(albums/get-albums params)))

    (GET* "/:album-id" [album-id]
      :summary     "Get a single album."
      :path-params [album-id :- Long]
      (service/trap #(album/get-album album-id)))

    (GET* "/:album-id/composers" [album-id]
      :summary     "Get composers for a single album."
      :path-params [album-id :- Long]
      (service/trap #(album/get-album-composers album-id)))

    (GET* "/:album-id/remixes" [album-id]
      :summary     "Get remixes for a single album."
      :path-params [album-id :- Long]
      (service/trap #(album/get-album-remixes album-id)))))

(defroutes* artist-routes
  (context* "/artists" []
    :tags ["artists"]

    (GET* "/" []
      :summary      "List all artists."
      :query-params [{sort-order :- (sort-order-docs artists/sort-fields "name") "name"}
                     {sort-dir :- (sort-dir-docs "ASC") "ASC"}
                     {limit :- limit-docs 50}
                     {offset :- offset-docs 0}
                     :as params]
      (service/trap #(artists/get-artists params)))

    (GET* "/:artist-id" [artist-id]
      :summary     "Get a single artist."
      :path-params [artist-id :- Long]
      (service/trap #(artist/get-artist artist-id)))

    (GET* "/:artist-id/games" [artist-id]
      :summary     "Get games for a single artist."
      :path-params [artist-id :- Long]
      (service/trap #(artist/get-artist-games artist-id)))

    (GET* "/:artist-id/albums" [artist-id]
      :summary     "Get albums for a single artist."
      :path-params [artist-id :- Long]
      (service/trap #(artist/get-artist-albums artist-id)))

    (GET* "/:artist-id/remixes" [artist-id]
      :summary     "Get remixes for a single artist."
      :path-params [artist-id :- Long]
      (service/trap #(artist/get-artist-remixes artist-id)))))

(defroutes* system-routes
  (context* "/systems" []
    :tags ["systems"]

    (GET* "/" []
      :summary      "List all systems."
      :query-params [{sort-order :- (sort-order-docs systems/sort-fields "name") "name"}
                     {sort-dir :- (sort-dir-docs "ASC") "ASC"}
                     {limit :- limit-docs 50}
                     {offset :- offset-docs 0}
                     :as params]
      (service/trap #(systems/get-systems params)))

    (GET* "/:system-id" [system-id]
      :summary      "Get a single system."
      (service/trap #(system/get-system system-id)))

    (GET* "/:system-id/games" [system-id]
      :summary      "Get games for a single system."
      (service/trap #(system/get-system-games system-id)))

    (GET* "/:system-id/composers" [system-id]
      :summary      "Get composers for a single system."
      (service/trap #(system/get-system-composers system-id)))

    (GET* "/:system-id/albums" [system-id]
      :summary      "Get albums for a single system."
      (service/trap #(system/get-system-albums system-id)))

    (GET* "/:system-id/remixes" [system-id]
      :summary      "Get remixes for a single system."
      (service/trap #(system/get-system-remixes system-id)))))

(defroutes* org-routes
  (context* "/orgs" []
    :tags ["orgs"]

    (GET* "/" []
      :summary      "List all organizations."
      :query-params [{sort-order :- (sort-order-docs organizations/sort-fields "name") "name"}
                     {sort-dir :- (sort-dir-docs "ASC") "ASC"}
                     {limit :- limit-docs 50}
                     {offset :- offset-docs 0}
                     :as params]
      (service/trap #(organizations/get-organizations params)))

    (GET* "/:organization-id" [organization-id]
      :summary     "Get a single organization."
      :path-params [organization-id :- Long]
      (service/trap #(organization/get-organization organization-id)))

    (GET* "/:organization-id/systems" [organization-id]
      :summary     "Get systems for a single organization."
      :path-params [organization-id :- Long]
      (service/trap #(organization/get-organization-systems organization-id)))

    (GET* "/:organization-id/games" [organization-id]
      :summary     "Get games for a single organization."
      :path-params [organization-id :- Long]
      (service/trap #(organization/get-organization-games organization-id)))

    (GET* "/:organization-id/composers" [organization-id]
      :summary     "Get composers for a single organization."
      :path-params [organization-id :- Long]
      (service/trap #(organization/get-organization-composers organization-id)))

    (GET* "/:organization-id/albums" [organization-id]
      :summary     "Get albums for a single organization."
      :path-params [organization-id :- Long]
      (service/trap #(organization/get-organization-albums organization-id)))

    (GET* "/:organization-id/remixes" [organization-id]
      :summary     "Get remixes for a single organization."
      :path-params [organization-id :- Long]
      (service/trap #(organization/get-organization-remixes organization-id)))))

(defroutes* chiptune-routes
  (context* "/chiptunes" []
    :tags ["chiptunes"]

    (GET* "/" []
      :summary      "List all chiptunes."
      :query-params [{sort-order :- (sort-order-docs chiptunes/sort-fields "name") "name"}
                     {sort-dir :- (sort-dir-docs "ASC") "ASC"}
                     {limit :- limit-docs 50}
                     {offset :- offset-docs 0}
                     :as params]
      (service/trap #(chiptunes/get-chiptunes params)))

    (GET* "/:chiptune-id" [chiptune-id]
      :summary     "Get a single chiptune."
      :path-params [chiptune-id :- Long]
      (service/trap #(chiptune/get-chiptune chiptune-id)))))
