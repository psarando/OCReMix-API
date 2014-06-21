<?php

namespace OCR\ApiBundle\Model;

use OCR\ApiBundle\Model\Entity;

class Album extends Entity
{
    public $artists;
    public $games;
    public $torrent;
    public $catalog;
    public $publisher;
    public $release_date;
    public $media;
    public $vgmdb_id;
    public $forum_link;
    public $composers;
    public $remixes;
    public $artwork;
    public $references;

    public function __construct($info)
    {
        parent::__construct($info);

        if (!empty($info['torrent'])) {
            $this->torrent = $info['torrent'];
        }
        if (!empty($info['catalog'])) {
            $this->catalog = $info['catalog'];
        }
        if (!empty($info['release_date'])) {
            $this->release_date = $info['release_date'];
        }
        if (!empty($info['media'])) {
            $this->media = $info['media'];
        }
        if (!empty($info['vgmdb_id'])) {
            $this->vgmdb_id = $info['vgmdb_id'];
        }
        if (!empty($info['forum_link'])) {
            $this->forum_link = $info['forum_link'];
        }
    }
}
