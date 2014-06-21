<?php

namespace OCR\ApiBundle\Service;

use OCR\ApiBundle\Model\Album;
use OCR\ApiBundle\Model\Artist;
use OCR\ApiBundle\Model\Game;
use OCR\ApiBundle\Model\MixPost;
use OCR\ApiBundle\Model\Organization;
use OCR\ApiBundle\Model\Remix;
use OCR\ApiBundle\Model\RemixCollection;
use OCR\ApiBundle\Model\RemixDownloadInfo;
use OCR\ApiBundle\Model\Song;
use OCR\ApiBundle\Model\System;
use OCR\ApiBundle\Persistence\RemixData;
use OCR\ApiBundle\Persistence\SongData;
use OCR\ApiBundle\Service;

use FOS\RestBundle\Request\ParamFetcherInterface;

/**
 * Model for listing Remixes.
 *
 * @package OCR\ApiBundle\Service
 * @author psarando
 */
class Remixes extends Service
{
    private $data;
    private $songData;

    public function __construct($db)
    {
        parent::__construct($db);
        $this->data = new RemixData($db);
        $this->songData = new SongData($db);
    }

    public function getRemixes(ParamFetcherInterface $paramFetcher, array $validSortFields, $defaultSort)
    {
        $sortInfo = $this->parseParams($paramFetcher, $validSortFields, $defaultSort);

        $remixes = $this->data->getListing('remixes', $sortInfo);
        $remixes = array_map(array($this, 'formatRemixListing'), $remixes);

        return new RemixCollection($remixes, $sortInfo);
    }

    public function getRemix($id)
    {
        $remix = $this->data->getEntity('remixes', $id);
        if (!empty($remix)) {
            $remix = $this->formatRemix($remix);
        }

        return $remix;
    }

    private function formatRemixListing($info)
    {
        $remix = new Remix(array_intersect_key(
            $info,
            array_flip(array("id", "title"))
        ));

        $game = $this->data->getEntity('games', $info["game"]);
        $remix->game = new Game(array_intersect_key(
            $game,
            array_flip(array("id", "name", "year"))
        ));
        $remix->system = new System($this->data->getIdName('systems', $game["system"]));

        if (!empty($info["album"])) {
            $remix->album = new Album($this->data->getIdName('albums', $info["album"]));
        }

        $remix->artists = array_map(
            function($artist) { return new Artist($artist); },
            $this->data->getRemixArtists($remix->id)
        );

        $remix->songs = array_map(
            function($song) { return new Song($song); },
            $this->data->getRemixSongs($remix->id)
        );

        $songIds = array_map(function($song) { return $song->id; }, $remix->songs);
        $remix->composers = array_map(
            function($artist) { return new Artist($artist); },
            $this->songData->getSongComposers($songIds)
        );

        $mixpost = $this->data->getMixPost($remix->id);
        $remix->date = $mixpost["posted"];

        return $remix;
    }

    private function formatRemix($info)
    {
        $remix = new Remix($info);

        $game = $this->data->getEntity('games', $info["game"]);
        $remix->game = new Game(array_intersect_key(
            $game,
            array_flip(array("id", "name", "name_short", "year"))
        ));
        $remix->publisher = new Organization($this->data->getIdName('organizations', $game["publisher"]));
        $remix->system = new System($this->data->getIdName('systems', $game["system"]));

        if (!empty($info["album"])) {
            $remix->album = new Album($this->data->getIdName('albums', $info["album"]));
        }

        $remix->artists = array_map(
            function($artist) { return new Artist($artist); },
            $this->data->getRemixArtists($remix->id)
        );

        $remix->songs = array_map(
            function($song) { return new Song($song); },
            $this->data->getRemixSongs($remix->id)
        );

        $songIds = array_map(function($song) { return $song->id; }, $remix->songs);
        $remix->composers = array_map(
            function($artist) { return new Artist($artist); },
            $this->songData->getSongComposers($songIds)
        );

        $remix->download = new RemixDownloadInfo($info, array_map(
            function($download) { return $download['url']; },
            $this->data->getDownloadUrls($remix->id)
        ));

        $remix->mixpost = new MixPost($this->data->getMixPost($remix->id));
        $remix->mixpost->evaluators = array_map(
            function($artist) { return new Artist($artist); },
            $this->data->getMixPostEvaluators($remix->id)
        );

        return $remix;
    }
}
