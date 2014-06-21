<?php

namespace OCR\ApiBundle\Model;

use OCR\ApiBundle\Model\SortInfo;

class RemixCollection
{
    /**
     * @var Remix[]
     */
    public $remixes;

    /**
     * @var integer
     */
    public $offset;

    /**
     * @var integer
     */
    public $limit;

    /**
     * @param Remix[]  $remixes
     * @param SortInfo $sortInfo
     */
    public function __construct($remixes = array(), SortInfo $sortInfo = null)
    {
        $this->remixes = $remixes;

        if (!empty($sortInfo)) {
            $this->offset = $sortInfo->offset;
            $this->limit = $sortInfo->limit;
        }
    }
}
