<?php

namespace OCR\ApiBundle\Model;

use OCR\ApiBundle\Model\Entity;

class Artist extends Entity
{
    public $real_name;
    public $aliases;
    public $gender;
    public $birthdate;
    public $birthplace;
    public $credits;
    public $groups;
    public $forum_profile;
    public $images;
    public $references;

    public $games;
    public $albums;
    public $remixes;

    public function __construct($info)
    {
        parent::__construct($info);

        if (!empty($info['real_name'])) {
            $this->real_name = $info['real_name'];
        }
        if (!empty($info['gender'])) {
            $this->gender = $info['gender'];
        }
        if (!empty($info['birthdate'])) {
            $this->birthdate = $info['birthdate'];
        }
        if (!empty($info['birthplace'])) {
            $this->birthplace = $info['birthplace'];
        }
        if (!empty($info['forum_link'])) {
            $this->forum_profile = $info['forum_link'];
        }
    }
}
