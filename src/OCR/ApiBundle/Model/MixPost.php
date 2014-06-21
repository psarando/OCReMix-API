<?php

namespace OCR\ApiBundle\Model;

use OCR\ApiBundle\Model\Entity;

class MixPost extends Entity
{
    public $posted;
    public $forum_comments;
    public $evaluators;
    public $review;

    public function __construct($info)
    {
        if (!empty($info['posted'])) {
            $this->posted = $info['posted'];
        }
        if (!empty($info['forum_comments'])) {
            $this->forum_comments = $info['forum_comments'];
        }
        if (!empty($info['review'])) {
            $this->review = $info['review'];
        }
    }
}
