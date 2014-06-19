<?php

namespace OCR\ApiBundle\Persistence;

use OCR\ApiBundle\Persistence;

/**
 * Data fetcher for Song Models.
 *
 * @package OCR\ApiBundle\Persistence
 * @author psarando
 */
class SongData extends Persistence
{
    public function getSongComposers($song_ids)
    {
        return $this->db->fetchAll(
            'SELECT a.id, a.name FROM artists a'
            .' JOIN composer_song cs ON a.id = cs.composer_id'
            .' WHERE cs.song_id IN (?)',
            array($song_ids),
            array(\Doctrine\DBAL\Connection::PARAM_STR_ARRAY)
        );
    }
}
