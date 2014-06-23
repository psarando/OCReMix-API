<?php

namespace OCR\ApiBundle\Model;

use OCR\ApiBundle\Model\Entity;

class ArtistCredits
{
    public $composer;
    public $remixer;

    public function __construct(array $composerCredits, array $remixCredits)
    {
        $this->composer = intval($composerCredits['count']);
        $this->remixer = intval($remixCredits['count']);
    }
}
