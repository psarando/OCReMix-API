<?php

namespace OCR\ApiBundle\Model;

use OCR\ApiBundle\Model\Entity;

class Chiptune extends Entity
{
    public $size;
    public $file;
    public $url;
    public $md5;
    public $format;
    public $songs;

    public $game;
    public $composers;

    public function __construct($info)
    {
        parent::__construct($info);

        if (!empty($info['size'])) {
            $this->size = intval($info['size']);
        }
        if (!empty($info['file'])) {
            $this->file = $info['file'];
        }
        if (!empty($info['url'])) {
            $this->url = $info['url'];
        }
        if (!empty($info['md5'])) {
            $this->md5 = $info['md5'];
        }
        if (!empty($info['format'])) {
            $this->format = $info['format'];
        }
        if (!empty($info['songs'])) {
            $this->songs = intval($info['songs']);
        }
    }
}
