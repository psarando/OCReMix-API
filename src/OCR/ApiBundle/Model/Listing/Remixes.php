<?php

namespace OCR\ApiBundle\Model\Listing;

use OCR\ApiBundle\Model\Listing;
use OCR\ApiBundle\Persistence\RemixData;
use OCR\ApiBundle\Persistence\SongData;

use FOS\RestBundle\Request\ParamFetcherInterface;

/**
 * Model for listing Remixes.
 *
 * @package OCR\ApiBundle\Model\Listing
 * @author psarando
 */
class Remixes extends Listing
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

        return array(
            'remixes' => $remixes,
            'limit' => $sortInfo->limit,
            'offset' => $sortInfo->offset,
        );
    }

    private function formatRemixListing($remix)
    {
        $remix = array_intersect_key(
            $remix,
            array_flip(array(
                "id",
                "title",
                "game",
                "album",
            ))
        );

        $game = $this->data->getEntity('games', $remix["game"]);
        $remix["game"] = array_intersect_key(
            $game,
            array_flip(array(
                "id",
                "name",
                "year",
            ))
        );
        $remix["game"]["year"] = $this->dateToYear($remix["game"]["year"]);
        $remix["system"] = $this->data->getIdName('systems', $game["system"]);

        if (!empty($remix["album"])) {
            $remix["album"] = $this->data->getIdName('albums', $remix["album"]);
        }

        $remix["artists"] = $this->data->getRemixArtists($remix["id"]);

        $songs = $this->data->getRemixSongs($remix["id"]);
        $remix["songs"] = $songs;
        $song_ids = array_map(function($song) { return $song['id']; }, $songs);
        $remix["composers"] = $this->songData->getSongComposers($song_ids);

        $mixpost = $this->data->getMixPost($remix["id"]);
        $remix["date"] = $mixpost["posted"];

        return $remix;
    }
}
