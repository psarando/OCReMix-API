<?php

namespace OCR\ApiBundle\Model;

use OCR\ApiBundle\Model\SortInfo;

class ChiptuneCollection extends Listing
{
    /**
     * @var Chiptune[]
     */
    public $chiptunes;

    /**
     * @param Chiptune[]  $chiptunes
     * @param SortInfo $sortInfo
     */
    public function __construct($chiptunes = array(), SortInfo $sortInfo = null)
    {
        parent::__construct($sortInfo);
        $this->chiptunes = $chiptunes;
    }
}
