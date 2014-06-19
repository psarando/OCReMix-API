<?php

namespace OCR\ApiBundle\Model\Entity;

use OCR\ApiBundle\Model\Entity;
use OCR\ApiBundle\Persistence\RemixData;
use OCR\ApiBundle\Persistence\SongData;

/**
 * Model for Remix details.
 *
 * @package OCR\ApiBundle\Model\Entity
 * @author psarando
 */
class Remix extends Entity
{
    private $data;
    private $songData;

    public function __construct($db)
    {
        parent::__construct($db);
        $this->data = new RemixData($db);
        $this->songData = new SongData($db);
    }

    public function getRemix($id)
    {
        $remix = $this->data->getEntity('remixes', $id);
        if (!empty($remix)) {
            $remix = $this->formatRemix($remix);
        }

        return $remix;
    }

    private function formatRemix($remix)
    {
        $remix["year"] = $this->dateToYear($remix["year"]);

        $game = $this->data->getEntity('games', $remix["game"]);
        $remix["game"] = array_intersect_key(
            $game,
            array_flip(array(
                "id",
                "name",
                "name_short",
                "year",
            ))
        );
        $remix["game"]["year"] = $this->dateToYear($game["year"]);
        $remix["publisher"] = $this->data->getIdName('organizations', $game["publisher"]);
        $remix["system"] = $this->data->getIdName('systems', $game["system"]);

        if (!empty($remix["album"])) {
            $remix["album"] = $this->data->getIdName('albums', $remix["album"]);
        }

        $remix["artists"] = $this->data->getRemixArtists($remix["id"]);

        $songs = $this->data->getRemixSongs($remix["id"]);
        $remix["songs"] = $songs;
        $songIds = array_map(function($song) { return $song['id']; }, $songs);
        $remix["composers"] = $this->songData->getSongComposers($songIds);

        $remix["download"] = $this->formatDownloadInfo(
            $remix,
            $this->data->getDownloadUrls($remix["id"])
        );
        unset($remix["size"]);
        unset($remix["md5"]);
        unset($remix["torrent"]);

        $mixpost = $this->data->getMixPost($remix["id"]);
        $mixpost["evaluators"] = $this->data->getMixPostEvaluators($remix["id"]);
        $remix["mixpost"] = $mixpost;

        return $remix;
    }

    private function formatDownloadInfo($remix, $downloadUrls)
    {
        $download = array_intersect_key(
            $remix,
            array_flip(array(
                "size",
                "md5",
                "torrent",
            ))
        );
        $download["links"] = array_map(
            function($download) { return $download['url']; },
            $downloadUrls
        );

        return $download;
    }
}
