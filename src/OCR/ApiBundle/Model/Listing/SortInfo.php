<?php

namespace OCR\ApiBundle\Model\Listing;

/**
 * Model containing sorting parameters for listing endpoints.
 *
 * @package OCR\ApiBundle\Model\Listing
 * @author psarando
 */
class SortInfo
{
    /**
     * @var string
     */
    public $sortOrder;

    /**
     * @var string
     */
    public $sortDir;

    /**
     * @var integer
     */
    public $offset;

    /**
     * @var integer
     */
    public $limit;

    /**
     * @param string $sortOrder
     * @param string $sortDir
     * @param integer $limit
     * @param integer $offset
     */
    public function __construct($sortOrder = null, $sortDir = 'DESC', $limit = 50, $offset = 0)
    {
        $this->sortOrder = $sortOrder;
        $this->sortDir = $sortDir;
        $this->limit = $limit;
        $this->offset = $offset;
    }
}
