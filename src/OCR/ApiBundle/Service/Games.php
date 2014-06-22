<?php

namespace OCR\ApiBundle\Service;

use OCR\ApiBundle\Model\Album;
use OCR\ApiBundle\Model\Artist;
use OCR\ApiBundle\Model\Chiptune;
use OCR\ApiBundle\Model\Game;
use OCR\ApiBundle\Model\GameCollection;
use OCR\ApiBundle\Model\Organization;
use OCR\ApiBundle\Model\Remix;
use OCR\ApiBundle\Model\Song;
use OCR\ApiBundle\Model\System;
use OCR\ApiBundle\Persistence\GameData;
use OCR\ApiBundle\Service;

use FOS\RestBundle\Request\ParamFetcherInterface;

/**
 * Service for listing Games and Game details.
 *
 * @package OCR\ApiBundle\Service
 * @author psarando
 */
class Games extends Service
{
    private $data;

    public function __construct($db)
    {
        parent::__construct($db);
        $this->data = new GameData($db);
    }

    public function getGames(ParamFetcherInterface $paramFetcher, array $validSortFields, $defaultSort)
    {
        $sortInfo = $this->parseParams($paramFetcher, $validSortFields, $defaultSort);

        $games = $this->data->getListing('games', $sortInfo);
        $games = array_map(array($this, 'formatGameListing'), $games);

        return new GameCollection($games, $sortInfo);
    }

    public function getGame($id)
    {
        $game = $this->data->getEntity('games', $id);
        if (!empty($game)) {
            $game = $this->formatGame($game);
        }

        return $game;
    }

    public function getGameSongs($id)
    {
        $game = $this->data->getIdName('games', $id);
        if (!empty($game)) {
            $game = $this->formatGameSongs($game);
        }

        return $game;
    }

    public function getGameAlbums($id)
    {
        $game = $this->data->getIdName('games', $id);
        if (!empty($game)) {
            $game = $this->formatGameAlbums($game);
        }

        return $game;
    }

    public function getGameRemixes($id)
    {
        $game = $this->data->getIdName('games', $id);
        if (!empty($game)) {
            $game = $this->formatGameRemixes($game);
        }

        return $game;
    }

    private function formatGameListing($info)
    {
        $game = new Game($info);

        $game->publisher = new Organization($this->data->getIdName('organizations', $info["publisher"]));
        $game->system = new System($this->data->getIdName('systems', $info["system"]));

        return $game;
    }

    private function formatGame($info)
    {
        $game = $this->formatGameListing($info);

        $game->composers = array_map(
            function($artist) { return new Artist($artist); },
            $this->data->getGameComposers($game->id)
        );

        $game->chiptunes = array_map(
            function($chiptune) { return new Chiptune($chiptune); },
            $this->data->getChiptunes($game->id)
        );

        $game->artwork = array_map(
            function($artwork) { return $artwork['url']; },
            $this->data->getGameArtwork($game->id)
        );

        $game->references = array_map(
            function($reference) { return $reference['url']; },
            $this->data->getGameReferences($game->id)
        );

        return $game;
    }

    private function formatGameSongs($info)
    {
        $game = new Game($info);

        $game->songs = array_map(
            function($song) { return new Song($song); },
            $this->data->getGameSongs($game->id)
        );

        return $game;
    }

    private function formatGameAlbums($info)
    {
        $game = new Game($info);

        $game->albums = array_map(
            function($album) { return new Album($album); },
            $this->data->getGameAlbums($game->id)
        );

        return $game;
    }

    private function formatGameRemixes($info)
    {
        $game = new Game($info);

        $game->remixes = array_map(
            function($remix) { return new Remix($remix); },
            $this->data->getGameRemixes($game->id)
        );

        return $game;
    }
}
