<?php

namespace OCR\ApiBundle\Model;

use OCR\ApiBundle\Model\SortInfo;

abstract class Listing
{
    /**
     * @var integer
     */
    public $offset;

    /**
     * @var integer
     */
    public $limit;

    /**
     * @param SortInfo $sortInfo
     */
    public function __construct(SortInfo $sortInfo = null)
    {
        if (!empty($sortInfo)) {
            $this->offset = $sortInfo->offset;
            $this->limit = $sortInfo->limit;
        }
    }
}
