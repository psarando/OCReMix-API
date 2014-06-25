<?php

namespace OCR\ApiBundle;

use OCR\ApiBundle\Model\Listing;

use FOS\RestBundle\Request\ParamFetcherInterface;

/**
 * Base Service for endpoints.
 *
 * @package OCR\ApiBundle
 * @author psarando
 */
abstract class Service
{
    protected function parseParams(ParamFetcherInterface $paramFetcher, array $validSortFields, Listing $listing)
    {
        $limit = intval($paramFetcher->get('limit'));
        $offset = intval($paramFetcher->get('offset'));
        $sortOrder = strtolower($paramFetcher->get('sort-order'));
        $sortDir = strtoupper($paramFetcher->get('sort-dir'));

        if ($limit > 0) {
            $listing->limit = $limit;
        }
        if ($offset > 0) {
            $listing->offset = $offset;
        }
        if (in_array($sortOrder, $validSortFields)) {
            $listing->sortOrder = $sortOrder;
        }
        if ($sortDir == "ASC") {
            $listing->sortDir = "ASC";
        }
    }
}
