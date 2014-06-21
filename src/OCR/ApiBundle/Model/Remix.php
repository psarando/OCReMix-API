<?php

namespace OCR\ApiBundle\Model;

use OCR\ApiBundle\Model\Entity;

/**
 * Model for Remix details.
 *
 * @package OCR\ApiBundle\Model
 * @author psarando
 */
class Remix extends Entity
{
    public $title;
    public $year;
    public $date;

    public $game;
    public $publisher;
    public $system;
    public $album;
    public $artists;
    public $songs;
    public $composers;

    public $encoder;
    public $comment;
    public $lyrics;
    public $mixpost;
    public $download;

    public function __construct($info)
    {
        $this->id = $info['id'];
        $this->title = $info['title'];

        if (!empty($info['year'])) {
            $this->year = $this->dateToYear($info['year']);
        }
        if (!empty($info['encoder'])) {
            $this->encoder = $info['encoder'];
        }
        if (!empty($info['torrent'])) {
            $this->torrent = $info['torrent'];
        }
        if (!empty($info['comment'])) {
            $this->comment = $info['comment'];
        }
        if (!empty($info['lyrics'])) {
            $this->lyrics = $info['lyrics'];
        }
    }

    /**
     * String representation for a remix
     *
     * @return string
     */
    public function __toString()
    {
        return $this->id;
    }
}
