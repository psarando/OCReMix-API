<?php

namespace OCR\ApiBundle\Persistence;

use OCR\ApiBundle\Persistence;

/**
 * Data fetcher for Organization Models.
 *
 * @package OCR\ApiBundle\Persistence
 * @author psarando
 */
class OrganizationData extends Persistence
{
    public function getOrganizationSystems($orgId)
    {
        return $this->db->fetchAll(
            'SELECT id, name FROM systems WHERE publisher = :id',
            array('id' => $orgId)
        );
    }

    public function getOrganizationGames($orgId)
    {
        return $this->db->fetchAll(
            'SELECT id, name FROM games WHERE publisher = :id',
            array('id' => $orgId)
        );
    }

    public function getOrganizationComposers($orgId)
    {
        return $this->db->fetchAll(
            'SELECT a.id, a.name FROM artists a'
            .' JOIN composer_song cs ON cs.composer_id = a.id'
            .' JOIN songs s ON s.id = cs.song_id'
            .' JOIN games g ON g.id = s.game'
            .' WHERE g.publisher = :id'
            .' GROUP BY a.id, a.name',
            array('id' => $orgId)
        );
    }

    public function getOrganizationAlbums($orgId)
    {
        return $this->db->fetchAll(
            'SELECT a.id, a.name FROM albums a'
            .' JOIN album_game ag ON ag.album_id = a.id'
            .' JOIN games g ON g.id = ag.game_id'
            .' WHERE g.publisher = :id'
            .' GROUP BY a.id, a.name',
            array('id' => $orgId)
        );
    }

    public function getOrganizationRemixes($orgId)
    {
        return $this->db->fetchAll(
            'SELECT r.id, r.title FROM remixes r'
            .' JOIN games g ON g.id = r.game'
            .' WHERE g.publisher = :id',
            array('id' => $orgId)
        );
    }

    public function getOrganizationReferences($orgId)
    {
        return $this->db->fetchAll(
            'SELECT url FROM organization_reference WHERE organization_id = :id',
            array('id' => $orgId)
        );
    }
}
