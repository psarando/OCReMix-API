<?php

namespace OCR\ApiBundle;

use OCR\ApiBundle\Model\SortInfo;

use FOS\RestBundle\Request\ParamFetcherInterface;

/**
 * Base Service for endpoints.
 *
 * @package OCR\ApiBundle
 * @author psarando
 */
abstract class Service
{
    protected $db;

    public function __construct($db)
    {
        $this->db = $db;
    }

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
