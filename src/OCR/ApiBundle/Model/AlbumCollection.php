<?php

namespace OCR\ApiBundle\Model;

use OCR\ApiBundle\Model\SortInfo;

class AlbumCollection extends Listing
{
    /**
     * @var Album[]
     */
    public $albums;

    /**
     * @param Album[]  $albums
     * @param SortInfo $sortInfo
     */
    public function __construct($albums = array(), SortInfo $sortInfo = null)
    {
        parent::__construct($sortInfo);
        $this->albums = $albums;
    }
}
