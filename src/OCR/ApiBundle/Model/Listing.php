<?php

namespace OCR\ApiBundle\Model;

use OCR\ApiBundle\Model\Entity;
use OCR\ApiBundle\Model\Listing\SortInfo;

use FOS\RestBundle\Request\ParamFetcherInterface;

/**
 * Model for listing endpoints.
 *
 * @package OCR\ApiBundle\Model
 * @author psarando
 */
abstract class Listing extends Entity
{
    protected function parseParams(ParamFetcherInterface $paramFetcher, array $validSortFields, $defaultSort)
    {
        $limit = intval($paramFetcher->get('limit'));
        $offset = intval($paramFetcher->get('offset'));
        $sortOrder = strtolower($paramFetcher->get('sort-order'));
        $sortDir = strtoupper($paramFetcher->get('sort-dir'));

        $sortInfo = new SortInfo($defaultSort);

        if ($limit > 0) {
            $sortInfo->limit = $limit;
        }
        if ($offset > 0) {
            $sortInfo->offset = $offset;
        }
        if (in_array($sortOrder, $validSortFields)) {
            $sortInfo->sortOrder = $sortOrder;
        }
        if ($sortDir == "ASC") {
            $sortInfo->sortDir = "ASC";
        }

        return $sortInfo;
    }
}
