<?php

namespace OCR\ApiBundle\Service;

use OCR\ApiBundle\Model\Album;
use OCR\ApiBundle\Model\Artist;
use OCR\ApiBundle\Model\Game;
use OCR\ApiBundle\Model\Organization;
use OCR\ApiBundle\Model\Remix;
use OCR\ApiBundle\Model\System;
use OCR\ApiBundle\Model\SystemCollection;
use OCR\ApiBundle\Persistence\SystemData;
use OCR\ApiBundle\Service;

use FOS\RestBundle\Request\ParamFetcherInterface;

/**
 * Service for listing Systems and System details.
 *
 * @package OCR\ApiBundle\Service
 * @author psarando
 */
class Systems extends Service
{
    private $data;

    public function __construct($db)
    {
        parent::__construct($db);
        $this->data = new SystemData($db);
    }

    public function getSystems(ParamFetcherInterface $paramFetcher, array $validSortFields, $defaultSort)
    {
        $sortInfo = $this->parseParams($paramFetcher, $validSortFields, $defaultSort);

        $systems = $this->data->getListing('systems', $sortInfo);
        $systems = array_map(array($this, 'formatSystemListing'), $systems);

        return new SystemCollection($systems, $sortInfo);
    }

    public function getSystem($id)
    {
        $system = $this->data->getEntity('systems', $id);
        if (!empty($system)) {
            $system = $this->formatSystem($system);
        }

        return $system;
    }

    public function getSystemGames($id)
    {
        $system = $this->data->getIdName('systems', $id);
        if (!empty($system)) {
            $system = $this->formatSystemGames($system);
        }

        return $system;
    }

    public function getSystemComposers($id)
    {
        $system = $this->data->getIdName('systems', $id);
        if (!empty($system)) {
            $system = $this->formatSystemComposers($system);
        }

        return $system;
    }

    public function getSystemAlbums($id)
    {
        $system = $this->data->getIdName('systems', $id);
        if (!empty($system)) {
            $system = $this->formatSystemAlbums($system);
        }

        return $system;
    }

    public function getSystemRemixes($id)
    {
        $system = $this->data->getIdName('systems', $id);
        if (!empty($system)) {
            $system = $this->formatSystemRemixes($system);
        }

        return $system;
    }

    private function formatSystemListing($info)
    {
        $system = new System($info);

        $system->publisher = new Organization($this->data->getIdName('organizations', $info["publisher"]));

        return $system;
    }

    private function formatSystem($info)
    {
        $system = $this->formatSystemListing($info);

        $system->references = array_map(
            function($reference) { return $reference['url']; },
            $this->data->getSystemReferences($system->id)
        );

        return $system;
    }

    private function formatSystemGames($info)
    {
        $system = new System($info);

        $system->games = array_map(
            function($game) { return new Game($game); },
            $this->data->getSystemGames($system->id)
        );

        return $system;
    }

    private function formatSystemComposers($info)
    {
        $system = new System($info);

        $system->composers = array_map(
            function($artist) { return new Artist($artist); },
            $this->data->getSystemComposers($system->id)
        );

        return $system;
    }

    private function formatSystemAlbums($info)
    {
        $system = new System($info);

        $system->albums = array_map(
            function($album) { return new Album($album); },
            $this->data->getSystemAlbums($system->id)
        );

        return $system;
    }

    private function formatSystemRemixes($info)
    {
        $system = new System($info);

        $system->remixes = array_map(
            function($remix) { return new Remix($remix); },
            $this->data->getSystemRemixes($system->id)
        );

        return $system;
    }
}
