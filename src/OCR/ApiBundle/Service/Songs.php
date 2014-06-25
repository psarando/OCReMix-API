<?php

namespace OCR\ApiBundle\Service;

use OCR\ApiBundle\Model\Artist;
use OCR\ApiBundle\Model\Chiptune;
use OCR\ApiBundle\Model\Game;
use OCR\ApiBundle\Model\Remix;
use OCR\ApiBundle\Model\Song;
use OCR\ApiBundle\Model\SongCollection;
use OCR\ApiBundle\Persistence\GameData;
use OCR\ApiBundle\Persistence\SongData;
use OCR\ApiBundle\Service;

use FOS\RestBundle\Request\ParamFetcherInterface;

/**
 * Service for listing Songs and Song details.
 *
 * @package OCR\ApiBundle\Service
 * @author psarando
 */
class Songs extends Service
{
    private $data;
    private $gameData;

    public function __construct($db)
    {
        $this->data = new SongData($db);
        $this->gameData = new GameData($db);
    }

    public function getSongs(ParamFetcherInterface $paramFetcher, array $validSortFields, $defaultSort)
    {
        $listing = new SongCollection($defaultSort);
        $this->parseParams($paramFetcher, $validSortFields, $listing);

        $listing->total = $this->data->getListingTotal('songs');
        $listing->songs = array_map(
            array($this, 'formatSongListing'),
            $this->data->getListing('songs', $listing)
        );

        return $listing;
    }

    public function getSong($id)
    {
        $song = $this->data->getEntity('songs', $id);
        if (!empty($song)) {
            $song = $this->formatSong($song);
        }

        return $song;
    }

    public function getSongRemixes($id)
    {
        $song = $this->data->getEntity('songs', $id);
        if (!empty($song)) {
            $song = $this->formatSongRemixes($song);
        }

        return $song;
    }

    private function formatSongListing($info)
    {
        $song = new Song($info);

        $game = $this->data->getEntity('games', $info['game']);
        $song->game = new Game(array_intersect_key(
            $game,
            array_flip(array('id', 'name', 'year'))
        ));

        return $song;
    }

    private function formatSong($info)
    {
        $song = $this->formatSongListing($info);

        $song->ostNames = array_map(
            function($ostName) { return $ostName['name']; },
            $this->data->getSongOstNames($song->id)
        );

        $song->aliases = array_map(
            function($alias) { return $alias['alias']; },
            $this->data->getSongAliases($song->id)
        );

        $song->composers = array_map(
            function($artist) { return new Artist($artist); },
            $this->data->getSongComposers(array($song->id))
        );

        $song->chiptunes = array_map(
            function($chiptune) { return new Chiptune($chiptune); },
            $this->gameData->getChiptunes($song->game->id)
        );

        return $song;
    }

    private function formatSongRemixes($info)
    {
        $song = new Song($info);

        $song->remixes = array_map(
            function($remix) { return new Remix($remix); },
            $this->data->getSongRemixes($song->id)
        );

        return $song;
    }
}