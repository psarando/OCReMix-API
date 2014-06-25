<?php

namespace OCR\ApiBundle\Model;

use OCR\ApiBundle\Model\SortInfo;

/**
 * System Listing.
 *
 * @package OCR\ApiBundle\Model
 * @author psarando
 */
class SystemCollection extends Listing
{
    /**
     * @var System[]
     */
    public $systems;

    /**
     * @param System[]  $systems
     * @param SortInfo $sortInfo
     */
    public function __construct($systems = array(), SortInfo $sortInfo = null)
    {
        parent::__construct($sortInfo);
        $this->systems = $systems;
    }
}
