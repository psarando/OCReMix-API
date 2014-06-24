<?php

namespace OCR\ApiBundle\Model;

use OCR\ApiBundle\Model\SortInfo;

class OrganizationCollection extends Listing
{
    /**
     * @var Organization[]
     */
    public $organizations;

    /**
     * @param Organization[]  $organizations
     * @param SortInfo $sortInfo
     */
    public function __construct($organizations = array(), SortInfo $sortInfo = null)
    {
        parent::__construct($sortInfo);
        $this->organizations = $organizations;
    }
}
