<?php

namespace OCR\ApiBundle\Model;

/**
 * Model for entity endpoints.
 *
 * @package OCR\ApiBundle\Model
 * @author psarando
 */
abstract class Entity
{
    protected $db;

    public function __construct($db)
    {
        $this->db = $db;
    }

    protected function dateToYear($dateStr)
    {
        if (empty($dateStr)) {
            return $dateStr;
        }

        return (int)(new \DateTime($dateStr))->format("Y");
    }
}
