<?php

namespace OCR\ApiBundle\Model;

use OCR\ApiBundle\Model\Entity;

/**
 * Model for Organization details.
 *
 * @package OCR\ApiBundle\Model
 * @author psarando
 */
class Organization extends Entity
{
    public $name_jp;
    public $references;

    public $systems;
    public $games;
    public $composers;
    public $albums;
    public $remixes;

    public function __construct($info)
    {
        parent::__construct($info);

        if (!empty($info['name_jp'])) {
            $this->name_jp = $info['name_jp'];
        }
    }
}
