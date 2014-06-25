<?php

namespace OCR\ApiBundle;

use OCR\ApiBundle\Model\Listing;

/**
 * Data fetcher for Model classes.
 *
 * @package OCR\ApiBundle
 * @author psarando
 */
class Persistence
{
    protected $db;

    public function __construct($db)
    {
        $this->db = $db;
    }

    public function getListing($table, Listing $sortInfo)
    {
        $sortOrder = $sortInfo->sortOrder;
        $sortDir = $sortInfo->sortDir;
        $limit = $sortInfo->limit;
        $offset = $sortInfo->offset;

        return $this->db->fetchAll("SELECT * FROM $table ORDER BY $sortOrder $sortDir LIMIT $limit OFFSET $offset");
    }

    public function getListingTotal($table)
    {
        $count =  $this->db->fetchAssoc("SELECT COUNT(*) AS total FROM $table");
        return intval($count['total']);
    }

    public function getEntity($table, $id)
    {
        return $this->db->fetchAssoc(
            "SELECT * FROM $table WHERE id = :id",
            array('id' => $id)
        );
    }

    public function getIdName($table, $id)
    {
        return $this->db->fetchAssoc(
            "SELECT id, name FROM $table WHERE id = :id",
            array('id' => $id)
        );
    }
}
