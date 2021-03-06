<?php

namespace OCR\ApiBundle\Model;

use OCR\ApiBundle\Model\Entity;

/**
 * Model for Remix download details.
 *
 * @package OCR\ApiBundle\Model
 * @author psarando
 */
class RemixDownloadInfo
{
    public $size;
    public $md5;
    public $torrent;
    public $links;

    public function __construct($info, array $links)
    {
        $this->size = intval($info['size']);
        $this->md5 = $info['md5'];
        if (!empty($info['torrent'])) {
            $this->torrent = $info['torrent'];
        }
        $this->links = $links;
    }
}
