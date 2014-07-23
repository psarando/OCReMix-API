(ns org.ocremix.api.routes
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
            [org.ocremix.api.service :as service]
            [compojure.api.sweet :refer :all]
            [compojure.route :as route]))

(defn- ->params-map
  [sort-order sort-dir limit offset]
  {:sort-order sort-order
   :sort-dir sort-dir
   :limit limit
   :offset offset})

(defroutes* api-routes
  (GET* "/remixes" []
        :summary      "List all remixes."
        :query-params [{sort-order :- String "id"}
                       {sort-dir :- String "DESC"}
                       {limit :- Long 50}
                       {offset :- Long 0}]
        (service/trap #(remixes/get-remixes (->params-map sort-order sort-dir limit offset))))

  (GET* "/remixes/:remix-id" [remix-id]
        :summary "Get a single remix."
        (service/trap #(remix/get-remix remix-id)))

  (GET* "/songs" []
        :summary      "List all songs."
        :query-params [{sort-order :- String "name"}
                       {sort-dir :- String "ASC"}
                       {limit :- Long 50}
                       {offset :- Long 0}]
        (service/trap #(songs/get-songs (->params-map sort-order sort-dir limit offset))))

  (GET* "/songs/:song-id" [song-id]
        :summary     "Get a single song."
        :path-params [song-id :- Long]
        (service/trap #(song/get-song song-id)))

  (GET* "/songs/:song-id/remixes" [song-id]
        :summary     "Get remixes for a single song."
        :path-params [song-id :- Long]
        (service/trap #(song/get-song-remixes song-id)))

  (GET* "/games" []
        :summary      "List all games."
        :query-params [{sort-order :- String "name"}
                       {sort-dir :- String "ASC"}
                       {limit :- Long 50}
                       {offset :- Long 0}]
        (service/trap #(games/get-games (->params-map sort-order sort-dir limit offset))))

  (GET* "/games/:game-id" [game-id]
        :summary     "Get a single game."
        :path-params [game-id :- Long]
        (service/trap #(game/get-game game-id)))

  (GET* "/games/:game-id/songs" [game-id]
        :summary     "Get songs for a single game."
        :path-params [game-id :- Long]
        (service/trap #(game/get-game-songs game-id)))

  (GET* "/games/:game-id/albums" [game-id]
        :summary     "Get albums for a single game."
        :path-params [game-id :- Long]
        (service/trap #(game/get-game-albums game-id)))

  (GET* "/games/:game-id/remixes" [game-id]
        :summary     "Get remixes for a single game."
        :path-params [game-id :- Long]
        (service/trap #(game/get-game-remixes game-id)))

  (GET* "/albums" []
        :summary      "List all albums."
        :query-params [{sort-order :- String "release_date"}
                       {sort-dir :- String "DESC"}
                       {limit :- Long 50}
                       {offset :- Long 0}]
        (service/trap #(albums/get-albums (->params-map sort-order sort-dir limit offset))))

  (GET* "/albums/:album-id" [album-id]
        :summary     "Get a single album."
        :path-params [album-id :- Long]
        (service/trap #(album/get-album album-id)))

  (GET* "/albums/:album-id/composers" [album-id]
        :summary     "Get composers for a single album."
        :path-params [album-id :- Long]
        (service/trap #(album/get-album-composers album-id)))

  (GET* "/albums/:album-id/remixes" [album-id]
        :summary     "Get remixes for a single album."
        :path-params [album-id :- Long]
        (service/trap #(album/get-album-remixes album-id)))

  (GET* "/artists" []
        :summary      "List all artists."
        :query-params [{sort-order :- String "name"}
                       {sort-dir :- String "ASC"}
                       {limit :- Long 50}
                       {offset :- Long 0}]
        (service/trap #(artists/get-artists (->params-map sort-order sort-dir limit offset))))

  (GET* "/artists/:artist-id" [artist-id]
        :summary     "Get a single artist."
        :path-params [artist-id :- Long]
        (service/trap #(artist/get-artist artist-id)))

  (GET* "/artists/:artist-id/games" [artist-id]
        :summary     "Get games for a single artist."
        :path-params [artist-id :- Long]
        (service/trap #(artist/get-artist-games artist-id)))

  (GET* "/artists/:artist-id/albums" [artist-id]
        :summary     "Get albums for a single artist."
        :path-params [artist-id :- Long]
        (service/trap #(artist/get-artist-albums artist-id)))

  (GET* "/artists/:artist-id/remixes" [artist-id]
        :summary     "Get remixes for a single artist."
        :path-params [artist-id :- Long]
        (service/trap #(artist/get-artist-remixes artist-id)))

  (GET* "/systems" []
        :summary      "List all systems."
        :query-params [{sort-order :- String "name"}
                       {sort-dir :- String "ASC"}
                       {limit :- Long 50}
                       {offset :- Long 0}]
        (service/trap #(systems/get-systems (->params-map sort-order sort-dir limit offset))))

  (GET* "/systems/:system-id" [system-id]
        :summary      "Get a single system."
        (service/trap #(system/get-system system-id)))

  (GET* "/systems/:system-id/games" [system-id]
        :summary      "Get games for a single system."
        (service/trap #(system/get-system-games system-id)))

  (GET* "/systems/:system-id/composers" [system-id]
        :summary      "Get composers for a single system."
        (service/trap #(system/get-system-composers system-id)))

  (GET* "/systems/:system-id/albums" [system-id]
        :summary      "Get albums for a single system."
        (service/trap #(system/get-system-albums system-id)))

  (GET* "/systems/:system-id/remixes" [system-id]
        :summary      "Get remixes for a single system."
        (service/trap #(system/get-system-remixes system-id)))

  (GET* "/orgs" []
        :summary      "List all organizations."
        :query-params [{sort-order :- String "name"}
                       {sort-dir :- String "ASC"}
                       {limit :- Long 50}
                       {offset :- Long 0}]
        (service/trap #(organizations/get-organizations
                        (->params-map sort-order sort-dir limit offset))))

  (GET* "/orgs/:organization-id" [organization-id]
        :summary     "Get a single organization."
        :path-params [organization-id :- Long]
        (service/trap #(organization/get-organization organization-id)))

  (GET* "/orgs/:organization-id/systems" [organization-id]
        :summary     "Get systems for a single organization."
        :path-params [organization-id :- Long]
        (service/trap #(organization/get-organization-systems organization-id)))

  (GET* "/orgs/:organization-id/games" [organization-id]
        :summary     "Get games for a single organization."
        :path-params [organization-id :- Long]
        (service/trap #(organization/get-organization-games organization-id)))

  (GET* "/orgs/:organization-id/composers" [organization-id]
        :summary     "Get composers for a single organization."
        :path-params [organization-id :- Long]
        (service/trap #(organization/get-organization-composers organization-id)))

  (GET* "/orgs/:organization-id/albums" [organization-id]
        :summary     "Get albums for a single organization."
        :path-params [organization-id :- Long]
        (service/trap #(organization/get-organization-albums organization-id)))

  (GET* "/orgs/:organization-id/remixes" [organization-id]
        :summary     "Get remixes for a single organization."
        :path-params [organization-id :- Long]
        (service/trap #(organization/get-organization-remixes organization-id)))

  (GET* "/chiptunes" []
        :summary      "List all chiptunes."
        :query-params [{sort-order :- String "name"}
                       {sort-dir :- String "ASC"}
                       {limit :- Long 50}
                       {offset :- Long 0}]
        (service/trap #(chiptunes/get-chiptunes (->params-map sort-order sort-dir limit offset))))

  (GET* "/chiptunes/:chiptune-id" [chiptune-id]
        :summary     "Get a single chiptune."
        :path-params [chiptune-id :- Long]
        (service/trap #(chiptune/get-chiptune chiptune-id)))

  (route/not-found (service/unrecognized-path-response)))

