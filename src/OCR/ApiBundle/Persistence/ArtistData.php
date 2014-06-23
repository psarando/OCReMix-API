<?php

namespace OCR\ApiBundle\Persistence;

use OCR\ApiBundle\Persistence;

/**
 * Data fetcher for Artist Models.
 *
 * @package OCR\ApiBundle\Persistence
 * @author psarando
 */
class ArtistData extends Persistence
{
    public function getArtistComposerCredits($artistId)
    {
        return $this->db->fetchAssoc(
            'SELECT COUNT(g.id) AS count FROM games g'
            .' WHERE g.id IN '
            .$this->getComposerGameSubselect(),
            array('id' => $artistId)
        );
    }

    public function getArtistRemixCredits($artistId)
    {
        return $this->db->fetchAssoc(
            'SELECT COUNT(ra.remix_id) AS count FROM remix_artist ra'
            .' JOIN artists a ON a.id = ra.artist_id'
            .' WHERE a.id = :id',
            array('id' => $artistId)
        );
    }

    public function getArtistGroups($artistId)
    {
        return $this->db->fetchAll(
            'SELECT a.id, a.name FROM artists a'
            .' JOIN artist_group g ON g.group_id = a.id'
            .' WHERE g.artist_id = :id',
            array('id' => $artistId)
        );
    }

    public function getArtistGames($artistId)
    {
        return $this->db->fetchAll(
            'SELECT g.id, g.name FROM games g'
            .' JOIN songs s ON s.game = g.id'
            .' JOIN composer_song cs ON cs.song_id = s.id'
            .' WHERE cs.composer_id = :id'
            .' GROUP BY g.id, g.name',
            array('id' => $artistId)
        );
    }

    public function getArtistAlbums($artistId)
    {
        return $this->db->fetchAll(
            'SELECT a.id, a.name FROM albums a'
            .' JOIN album_game ag ON ag.album_id = a.id'
            .' WHERE ag.game_id IN '
            .$this->getComposerGameSubselect(),
            array('id' => $artistId)
        );
    }

    public function getArtistRemixes($artistId)
    {
        return $this->db->fetchAll(
            'SELECT r.id, r.title FROM remixes r'
            .' JOIN remix_artist ra ON r.id = ra.remix_id'
            .' WHERE ra.artist_id = :id',
            array('id' => $artistId)
        );
    }

    public function getArtistAliases($artistId)
    {
        return $this->db->fetchAll(
            'SELECT alias FROM artist_alias WHERE artist_id = :id',
            array('id' => $artistId)
        );
    }

    public function getArtistImages($artistId)
    {
        return $this->db->fetchAll(
            'SELECT url FROM artist_art WHERE artist_id = :id',
            array('id' => $artistId)
        );
    }

    public function getArtistReferences($artistId)
    {
        return $this->db->fetchAll(
            'SELECT url FROM artist_reference WHERE artist_id = :id',
            array('id' => $artistId)
        );
    }

    private function getComposerGameSubselect()
    {
        return '('
            .'SELECT s.game FROM songs s'
            .' JOIN composer_song cs ON cs.song_id = s.id'
            .' WHERE cs.composer_id = :id'
            .' GROUP BY s.game'
            .')';
    }
}
