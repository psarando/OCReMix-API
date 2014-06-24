<?php

namespace OCR\ApiBundle\Persistence;

use OCR\ApiBundle\Persistence;

/**
 * Data fetcher for System Models.
 *
 * @package OCR\ApiBundle\Persistence
 * @author psarando
 */
class SystemData extends Persistence
{
    public function getSystemGames($systemId)
    {
        return $this->db->fetchAll(
            'SELECT id, name FROM games WHERE system = :id',
            array('id' => $systemId)
        );
    }

    public function getSystemComposers($systemId)
    {
        return $this->db->fetchAll(
            'SELECT a.id, a.name FROM artists a'
            .' JOIN composer_song cs ON cs.composer_id = a.id'
            .' JOIN songs s ON s.id = cs.song_id'
            .' JOIN games g ON g.id = s.game'
            .' WHERE g.system = :id'
            .' GROUP BY a.id, a.name',
            array('id' => $systemId)
        );
    }

    public function getSystemAlbums($systemId)
    {
        return $this->db->fetchAll(
            'SELECT a.id, a.name FROM albums a'
            .' JOIN album_game ag ON ag.album_id = a.id'
            .' JOIN games g ON g.id = ag.game_id'
            .' WHERE g.system = :id'
            .' GROUP BY a.id, a.name',
            array('id' => $systemId)
        );
    }

    public function getSystemRemixes($systemId)
    {
        return $this->db->fetchAll(
            'SELECT r.id, r.title FROM remixes r'
            .' JOIN games g ON g.id = r.game'
            .' WHERE g.system = :id',
            array('id' => $systemId)
        );
    }

    public function getSystemReferences($systemId)
    {
        return $this->db->fetchAll(
            'SELECT url FROM system_reference WHERE system_id = :id',
            array('id' => $systemId)
        );
    }
}
