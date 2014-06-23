<?php

namespace OCR\ApiBundle\Model;

use OCR\ApiBundle\Model\SortInfo;

class ArtistCollection extends Listing
{
    /**
     * @var Artist[]
     */
    public $artists;

    /**
     * @param Artist[]  $artists
     * @param SortInfo $sortInfo
     */
    public function __construct($artists = array(), SortInfo $sortInfo = null)
    {
        parent::__construct($sortInfo);
        $this->artists = $artists;
    }
}
