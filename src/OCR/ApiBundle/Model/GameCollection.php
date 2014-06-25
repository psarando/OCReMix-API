<?php

namespace OCR\ApiBundle\Model;

use OCR\ApiBundle\Model\SortInfo;

/**
 * Game Listing.
 *
 * @package OCR\ApiBundle\Model
 * @author psarando
 */
class GameCollection extends Listing
{
    /**
     * @var Game[]
     */
    public $games;

    /**
     * @param Game[]  $games
     * @param SortInfo $sortInfo
     */
    public function __construct($games = array(), SortInfo $sortInfo = null)
    {
        parent::__construct($sortInfo);
        $this->games = $games;
    }
}
