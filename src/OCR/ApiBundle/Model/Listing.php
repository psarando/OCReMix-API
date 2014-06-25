<?php

namespace OCR\ApiBundle\Model;

/**
 * Base Listing Model containing sorting parameters for listing endpoints.
 *
 * @package OCR\ApiBundle\Model
 * @author psarando
 */
abstract class Listing
{
    /**
     * @var integer
     */
    public $total;

    /**
     * @var integer
     */
    public $limit;

    /**
     * @var integer
     */
    public $offset;

    /**
     * @var string
     */
    public $sortOrder;

    /**
     * @var string
     */
    public $sortDir;

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
