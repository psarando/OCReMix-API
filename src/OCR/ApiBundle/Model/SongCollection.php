<?php

namespace OCR\ApiBundle\Model;

use OCR\ApiBundle\Model\SortInfo;

class SongCollection extends Listing
{
    /**
     * @var Song[]
     */
    public $songs;

    /**
     * @param Song[]  $songs
     * @param SortInfo $sortInfo
     */
    public function __construct($songs = array(), SortInfo $sortInfo = null)
    {
        parent::__construct($sortInfo);
        $this->songs = $songs;
    }
}