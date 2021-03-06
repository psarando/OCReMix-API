<?php

namespace OCR\ApiBundle\Model;

use OCR\ApiBundle\Model\Entity;

/**
 * Model for Game details.
 *
 * @package OCR\ApiBundle\Model
 * @author psarando
 */
class Game extends Entity
{
    public $name_jp;
    public $name_short;
    public $year;
    public $publisher;
    public $system;
    public $composers;
    public $chiptunes;
    public $artwork;
    public $references;

    public $songs;
    public $albums;
    public $remixes;

    public function __construct($info)
    {
        parent::__construct($info);

        if (!empty($info['year'])) {
            $this->year = $this->dateToYear($info['year']);
        }
        if (!empty($info['name_jp'])) {
            $this->name_jp = $info['name_jp'];
        }
        if (!empty($info['name_short'])) {
            $this->name_short = $info['name_short'];
        }
    }
}
