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
        :query-params [{sort-order :- String "id"}
                       {sort-dir :- String "DESC"}
                       {limit :- Long 50}
                       {offset :- Long 0}]
        (service/trap #(remixes/get-remixes (->params-map sort-order sort-dir limit offset))))

  (GET* "/remixes/:remix-id" [remix-id]
       (service/trap #(remix/get-remix remix-id)))

  (GET* "/songs" []
        :query-params [{sort-order :- String "name"}
                       {sort-dir :- String "ASC"}
                       {limit :- Long 50}
                       {offset :- Long 0}]
        (service/trap #(songs/get-songs (->params-map sort-order sort-dir limit offset))))

  (GET* "/songs/:song-id" [song-id]
        :path-params [song-id :- Long]
       (service/trap #(song/get-song song-id)))

  (GET* "/songs/:song-id/remixes" [song-id]
        :path-params [song-id :- Long]
       (service/trap #(song/get-song-remixes song-id)))

  (GET* "/games" []
        :query-params [{sort-order :- String "name"}
                       {sort-dir :- String "ASC"}
                       {limit :- Long 50}
                       {offset :- Long 0}]
        (service/trap #(games/get-games (->params-map sort-order sort-dir limit offset))))

  (GET* "/games/:game-id" [game-id]
        :path-params [game-id :- Long]
       (service/trap #(game/get-game game-id)))

  (GET* "/games/:game-id/songs" [game-id]
        :path-params [game-id :- Long]
       (service/trap #(game/get-game-songs game-id)))

  (GET* "/games/:game-id/albums" [game-id]
        :path-params [game-id :- Long]
       (service/trap #(game/get-game-albums game-id)))

  (GET* "/games/:game-id/remixes" [game-id]
        :path-params [game-id :- Long]
       (service/trap #(game/get-game-remixes game-id)))

  (GET* "/albums" []
        :query-params [{sort-order :- String "release_date"}
                       {sort-dir :- String "DESC"}
                       {limit :- Long 50}
                       {offset :- Long 0}]
        (service/trap #(albums/get-albums (->params-map sort-order sort-dir limit offset))))

  (GET* "/albums/:album-id" [album-id]
        :path-params [album-id :- Long]
       (service/trap #(album/get-album album-id)))

  (GET* "/albums/:album-id/composers" [album-id]
        :path-params [album-id :- Long]
       (service/trap #(album/get-album-composers album-id)))

  (GET* "/albums/:album-id/remixes" [album-id]
        :path-params [album-id :- Long]
       (service/trap #(album/get-album-remixes album-id)))

  (GET* "/artists" []
        :query-params [{sort-order :- String "name"}
                       {sort-dir :- String "ASC"}
                       {limit :- Long 50}
                       {offset :- Long 0}]
        (service/trap #(artists/get-artists (->params-map sort-order sort-dir limit offset))))

  (GET* "/artists/:artist-id" [artist-id]
        :path-params [artist-id :- Long]
       (service/trap #(artist/get-artist artist-id)))

  (GET* "/artists/:artist-id/games" [artist-id]
        :path-params [artist-id :- Long]
       (service/trap #(artist/get-artist-games artist-id)))

  (GET* "/artists/:artist-id/albums" [artist-id]
        :path-params [artist-id :- Long]
       (service/trap #(artist/get-artist-albums artist-id)))

  (GET* "/artists/:artist-id/remixes" [artist-id]
        :path-params [artist-id :- Long]
       (service/trap #(artist/get-artist-remixes artist-id)))

  (GET* "/systems" []
        :query-params [{sort-order :- String "name"}
                       {sort-dir :- String "ASC"}
                       {limit :- Long 50}
                       {offset :- Long 0}]
        (service/trap #(systems/get-systems (->params-map sort-order sort-dir limit offset))))

  (GET* "/systems/:system-id" [system-id]
       (service/trap #(system/get-system system-id)))

  (GET* "/systems/:system-id/games" [system-id]
       (service/trap #(system/get-system-games system-id)))

  (GET* "/systems/:system-id/composers" [system-id]
       (service/trap #(system/get-system-composers system-id)))

  (GET* "/systems/:system-id/albums" [system-id]
       (service/trap #(system/get-system-albums system-id)))

  (GET* "/systems/:system-id/remixes" [system-id]
       (service/trap #(system/get-system-remixes system-id)))

  (GET* "/orgs" []
        :query-params [{sort-order :- String "name"}
                       {sort-dir :- String "ASC"}
                       {limit :- Long 50}
                       {offset :- Long 0}]
        (service/trap #(organizations/get-organizations
                        (->params-map sort-order sort-dir limit offset))))

  (GET* "/orgs/:organization-id" [organization-id]
        :path-params [organization-id :- Long]
       (service/trap #(organization/get-organization organization-id)))

  (GET* "/orgs/:organization-id/systems" [organization-id]
        :path-params [organization-id :- Long]
       (service/trap #(organization/get-organization-systems organization-id)))

  (GET* "/orgs/:organization-id/games" [organization-id]
        :path-params [organization-id :- Long]
       (service/trap #(organization/get-organization-games organization-id)))

  (GET* "/orgs/:organization-id/composers" [organization-id]
        :path-params [organization-id :- Long]
       (service/trap #(organization/get-organization-composers organization-id)))

  (GET* "/orgs/:organization-id/albums" [organization-id]
        :path-params [organization-id :- Long]
       (service/trap #(organization/get-organization-albums organization-id)))

  (GET* "/orgs/:organization-id/remixes" [organization-id]
        :path-params [organization-id :- Long]
       (service/trap #(organization/get-organization-remixes organization-id)))

  (GET* "/chiptunes" []
        :query-params [{sort-order :- String "name"}
                       {sort-dir :- String "ASC"}
                       {limit :- Long 50}
                       {offset :- Long 0}]
        (service/trap #(chiptunes/get-chiptunes (->params-map sort-order sort-dir limit offset))))

  (GET* "/chiptunes/:chiptune-id" [chiptune-id]
        :path-params [chiptune-id :- Long]
       (service/trap #(chiptune/get-chiptune chiptune-id)))

  (route/not-found (service/unrecognized-path-response)))

