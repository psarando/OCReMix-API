<?php

namespace OCR\ApiBundle\Model;

use OCR\ApiBundle\Model\Entity;

class Song extends Entity
{
    public $ostNames;
    public $aliases;
    public $composers;
    public $game;
    public $chiptunes;
    public $remixes;
    public $remixCount;

    public function __construct($info)
    {
        parent::__construct($info);

        if (!empty($info['remix_count'])) {
            $this->remixCount = intval($info['remix_count']);
        }
    }
}
