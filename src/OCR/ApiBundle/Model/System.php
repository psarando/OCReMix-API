<?php

namespace OCR\ApiBundle\Model;

use OCR\ApiBundle\Model\Entity;

/**
 * Model for System details.
 *
 * @package OCR\ApiBundle\Model
 * @author psarando
 */
class System extends Entity
{
    public $name_jp;
    public $year;
    public $release_date;
    public $publisher;
    public $references;

    public $games;
    public $composers;
    public $albums;
    public $remixes;

    public function __construct($info)
    {
        parent::__construct($info);

        $this->id = $info['id'];

        if (!empty($info['name_jp'])) {
            $this->name_jp = $info['name_jp'];
        }
        if (!empty($info['year'])) {
            $this->year = $this->dateToYear($info['year']);
        }
        if (!empty($info['release_date'])) {
            $this->release_date = $info['release_date'];
        }
    }
}
