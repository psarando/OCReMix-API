<?php

namespace OCR\ApiBundle\Service;

use OCR\ApiBundle\Model\Album;
use OCR\ApiBundle\Model\Artist;
use OCR\ApiBundle\Model\Game;
use OCR\ApiBundle\Model\Organization;
use OCR\ApiBundle\Model\OrganizationCollection;
use OCR\ApiBundle\Model\Remix;
use OCR\ApiBundle\Model\System;
use OCR\ApiBundle\Persistence\OrganizationData;
use OCR\ApiBundle\Service;

use FOS\RestBundle\Request\ParamFetcherInterface;

/**
 * Service for listing Organizations and Organization details.
 *
 * @package OCR\ApiBundle\Service
 * @author psarando
 */
class Organizations extends Service
{
    private $data;

    public function __construct($db)
    {
        $this->data = new OrganizationData($db);
    }

    public function getOrganizations(ParamFetcherInterface $paramFetcher, array $validSortFields, $defaultSort)
    {
        $listing = new OrganizationCollection($defaultSort);
        $this->parseParams($paramFetcher, $validSortFields, $listing);

        $listing->total = $this->data->getListingTotal('organizations');
        $listing->organizations = array_map(
            array($this, 'formatOrganizationListing'),
            $this->data->getListing('organizations', $listing)
        );

        return $listing;
    }

    public function getOrganization($id)
    {
        $organization = $this->data->getEntity('organizations', $id);
        if (!empty($organization)) {
            $organization = $this->formatOrganization($organization);
        }

        return $organization;
    }

    public function getOrganizationSystems($id)
    {
        $organization = $this->data->getIdName('organizations', $id);
        if (!empty($organization)) {
            $organization = $this->formatOrganizationSystems($organization);
        }

        return $organization;
    }

    public function getOrganizationGames($id)
    {
        $organization = $this->data->getIdName('organizations', $id);
        if (!empty($organization)) {
            $organization = $this->formatOrganizationGames($organization);
        }

        return $organization;
    }

    public function getOrganizationComposers($id)
    {
        $organization = $this->data->getIdName('organizations', $id);
        if (!empty($organization)) {
            $organization = $this->formatOrganizationComposers($organization);
        }

        return $organization;
    }

    public function getOrganizationAlbums($id)
    {
        $organization = $this->data->getIdName('organizations', $id);
        if (!empty($organization)) {
            $organization = $this->formatOrganizationAlbums($organization);
        }

        return $organization;
    }

    public function getOrganizationRemixes($id)
    {
        $organization = $this->data->getIdName('organizations', $id);
        if (!empty($organization)) {
            $organization = $this->formatOrganizationRemixes($organization);
        }

        return $organization;
    }

    private function formatOrganizationListing($info)
    {
        return new Organization($info);
    }

    private function formatOrganization($info)
    {
        $organization = new Organization($info);

        $organization->references = array_map(
            function($reference) { return $reference['url']; },
            $this->data->getOrganizationReferences($organization->id)
        );

        return $organization;
    }

    private function formatOrganizationSystems($info)
    {
        $organization = new Organization($info);

        $organization->systems = array_map(
            function($system) { return new System($system); },
            $this->data->getOrganizationSystems($organization->id)
        );

        return $organization;
    }

    private function formatOrganizationGames($info)
    {
        $organization = new Organization($info);

        $organization->games = array_map(
            function($game) { return new Game($game); },
            $this->data->getOrganizationGames($organization->id)
        );

        return $organization;
    }

    private function formatOrganizationComposers($info)
    {
        $organization = new Organization($info);

        $organization->composers = array_map(
            function($artist) { return new Artist($artist); },
            $this->data->getOrganizationComposers($organization->id)
        );

        return $organization;
    }

    private function formatOrganizationAlbums($info)
    {
        $organization = new Organization($info);

        $organization->albums = array_map(
            function($album) { return new Album($album); },
            $this->data->getOrganizationAlbums($organization->id)
        );

        return $organization;
    }

    private function formatOrganizationRemixes($info)
    {
        $organization = new Organization($info);

        $organization->remixes = array_map(
            function($remix) { return new Remix($remix); },
            $this->data->getOrganizationRemixes($organization->id)
        );

        return $organization;
    }
}
