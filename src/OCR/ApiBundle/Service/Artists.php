<?php

namespace OCR\ApiBundle\Service;

use OCR\ApiBundle\Model\Album;
use OCR\ApiBundle\Model\Artist;
use OCR\ApiBundle\Model\ArtistCollection;
use OCR\ApiBundle\Model\ArtistCredits;
use OCR\ApiBundle\Model\Game;
use OCR\ApiBundle\Model\Remix;
use OCR\ApiBundle\Persistence\ArtistData;
use OCR\ApiBundle\Service;

use FOS\RestBundle\Request\ParamFetcherInterface;

/**
 * Service for listing Artists and Artist details.
 *
 * @package OCR\ApiBundle\Service
 * @author psarando
 */
class Artists extends Service
{
    private $data;

    public function __construct($db)
    {
        $this->data = new ArtistData($db);
    }

    public function getArtists(ParamFetcherInterface $paramFetcher, array $validSortFields, $defaultSort)
    {
        $listing = new ArtistCollection($defaultSort);
        $this->parseParams($paramFetcher, $validSortFields, $listing);

        $listing->total = $this->data->getListingTotal('artists');
        $listing->artists = array_map(
            array($this, 'formatArtistListing'),
            $this->data->getListing('artists', $listing)
        );

        return $listing;
    }

    public function getArtist($id)
    {
        $artist = $this->data->getEntity('artists', $id);
        if (!empty($artist)) {
            $artist = $this->formatArtist($artist);
        }

        return $artist;
    }

    public function getArtistGames($id)
    {
        $artist = $this->data->getIdName('artists', $id);
        if (!empty($artist)) {
            $artist = $this->formatArtistGames($artist);
        }

        return $artist;
    }

    public function getArtistAlbums($id)
    {
        $artist = $this->data->getIdName('artists', $id);
        if (!empty($artist)) {
            $artist = $this->formatArtistAlbums($artist);
        }

        return $artist;
    }

    public function getArtistRemixes($id)
    {
        $artist = $this->data->getIdName('artists', $id);
        if (!empty($artist)) {
            $artist = $this->formatArtistRemixes($artist);
        }

        return $artist;
    }

    private function formatArtistListing($info)
    {
        $artist = new Artist($info);

        return $artist;
    }

    private function formatArtist($info)
    {
        $artist = $this->formatArtistListing($info);

        $composerCredits = $this->data->getArtistComposerCredits($artist->id);
        $remixCredits = $this->data->getArtistRemixCredits($artist->id);
        $artist->credits = new ArtistCredits($composerCredits, $remixCredits);

        $artist->groups = array_map(
            function($group) { return new Artist($group); },
            $this->data->getArtistGroups($artist->id)
        );

        $artist->aliases = array_map(
            function($alias) { return $alias['alias']; },
            $this->data->getArtistAliases($artist->id)
        );

        $artist->images = array_map(
            function($artwork) { return $artwork['url']; },
            $this->data->getArtistImages($artist->id)
        );

        $artist->references = array_map(
            function($reference) { return $reference['url']; },
            $this->data->getArtistReferences($artist->id)
        );

        return $artist;
    }

    private function formatArtistGames($info)
    {
        $artist = new Artist($info);

        $artist->games = array_map(
            function($game) { return new Game($game); },
            $this->data->getArtistGames($artist->id)
        );

        return $artist;
    }

    private function formatArtistAlbums($info)
    {
        $artist = new Artist($info);

        $artist->albums = array_map(
            function($album) { return new Album($album); },
            $this->data->getArtistAlbums($artist->id)
        );

        return $artist;
    }

    private function formatArtistRemixes($info)
    {
        $artist = new Artist($info);

        $artist->remixes = array_map(
            function($remix) { return new Remix($remix); },
            $this->data->getArtistRemixes($artist->id)
        );

        return $artist;
    }
}
