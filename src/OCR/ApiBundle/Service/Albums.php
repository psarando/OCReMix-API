<?php

namespace OCR\ApiBundle\Service;

use OCR\ApiBundle\Model\Album;
use OCR\ApiBundle\Model\AlbumCollection;
use OCR\ApiBundle\Model\Artist;
use OCR\ApiBundle\Model\Game;
use OCR\ApiBundle\Model\Organization;
use OCR\ApiBundle\Model\Remix;
use OCR\ApiBundle\Persistence\AlbumData;
use OCR\ApiBundle\Service;

use FOS\RestBundle\Request\ParamFetcherInterface;

/**
 * Service for listing Albums and Album details.
 *
 * @package OCR\ApiBundle\Service
 * @author psarando
 */
class Albums extends Service
{
    private $data;

    public function __construct($db)
    {
        parent::__construct($db);
        $this->data = new AlbumData($db);
    }

    public function getAlbums(ParamFetcherInterface $paramFetcher, array $validSortFields, $defaultSort)
    {
        $sortInfo = $this->parseParams($paramFetcher, $validSortFields, $defaultSort);

        $albums = $this->data->getListing('albums', $sortInfo);
        $albums = array_map(array($this, 'formatAlbumListing'), $albums);

        return new AlbumCollection($albums, $sortInfo);
    }

    public function getAlbum($id)
    {
        $album = $this->data->getEntity('albums', $id);
        if (!empty($album)) {
            $album = $this->formatAlbum($album);
        }

        return $album;
    }

    public function getAlbumComposers($id)
    {
        $album = $this->data->getIdName('albums', $id);
        if (!empty($album)) {
            $album = $this->formatAlbumComposers($album);
        }

        return $album;
    }


    public function getAlbumRemixes($id)
    {
        $album = $this->data->getIdName('albums', $id);
        if (!empty($album)) {
            $album = $this->formatAlbumRemixes($album);
        }

        return $album;
    }

    private function formatAlbumListing($info)
    {
        $album = new Album($info);

        $album->publisher = new Organization($this->data->getIdName('organizations', $info["publisher"]));

        return $album;
    }

    private function formatAlbum($info)
    {
        $album = $this->formatAlbumListing($info);

        $album->games = array_map(
            function($game) { return new Game($game); },
            $this->data->getAlbumGames($album->id)
        );

        $album->artists = array_map(
            function($artist) { return new Artist($artist); },
            $this->data->getAlbumArtists($album->id)
        );

        $album->artwork = array_map(
            function($artwork) { return $artwork['url']; },
            $this->data->getAlbumArtwork($album->id)
        );

        $album->references = array_map(
            function($reference) { return $reference['url']; },
            $this->data->getAlbumReferences($album->id)
        );

        return $album;
    }

    private function formatAlbumComposers($info)
    {
        $album = new Album($info);

        $album->composers = array_map(
            function($artist) { return new Artist($artist); },
            $this->data->getAlbumComposers($album->id)
        );

        return $album;
    }

    private function formatAlbumRemixes($info)
    {
        $album = new Album($info);

        $album->remixes = array_map(
            function($remix) { return new Remix($remix); },
            $this->data->getAlbumRemixes($album->id)
        );

        return $album;
    }
}
