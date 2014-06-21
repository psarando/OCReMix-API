<?php

namespace OCR\ApiBundle\Model;

use OCR\ApiBundle\Model\Entity;

class Chiptune extends Entity
{
    public $size;

    public function __construct($info)
    {
        parent::__construct($info);

        if (!empty($info['size'])) {
            $this->size = intval($info['size']);
        }
    }
}
