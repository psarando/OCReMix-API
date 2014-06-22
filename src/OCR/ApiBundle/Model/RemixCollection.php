<?php

namespace OCR\ApiBundle\Model;

use OCR\ApiBundle\Model\SortInfo;

class RemixCollection extends Listing
{
    /**
     * @var Remix[]
     */
    public $remixes;

    /**
     * @param Remix[]  $remixes
     * @param SortInfo $sortInfo
     */
    public function __construct($remixes = array(), SortInfo $sortInfo = null)
    {
        parent::__construct($sortInfo);
        $this->remixes = $remixes;
    }
}