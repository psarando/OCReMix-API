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
    public function getSongComposers($songIds)
    {
        return $this->db->fetchAll(
            'SELECT a.id, a.name FROM artists a'
            .' JOIN composer_song cs ON a.id = cs.composer_id'
            .' WHERE cs.song_id IN (?)',
            array($songIds),
            array(\Doctrine\DBAL\Connection::PARAM_STR_ARRAY)
        );
    }

    public function getSongRemixes($songId)
    {
        return $this->db->fetchAll(
            'SELECT r.id, r.title FROM remixes r'
            .' JOIN remix_song rs ON rs.remix_id = r.id'
            .' WHERE rs.song_id = :id',
            array('id' => $songId)
        );
    }

    public function getSongOstNames($songId)
    {
        return $this->db->fetchAll(
            'SELECT name FROM song_ost_name WHERE song_id = :id',
            array('id' => $songId)
        );
    }

    public function getSongAliases($songId)
    {
        return $this->db->fetchAll(
            'SELECT alias FROM song_alias WHERE song_id = :id',
            array('id' => $songId)
        );
    }
}
