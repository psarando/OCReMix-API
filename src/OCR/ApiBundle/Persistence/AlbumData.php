<?php

namespace OCR\ApiBundle\Persistence;

use OCR\ApiBundle\Persistence;

/**
 * Data fetcher for Album Models.
 *
 * @package OCR\ApiBundle\Persistence
 * @author psarando
 */
class AlbumData extends Persistence
{
    public function getAlbumGames($albumId)
    {
        return $this->db->fetchAll(
            'SELECT g.id, g.name FROM games g'
            .' JOIN album_game ag ON ag.game_id = g.id'
            .' WHERE ag.album_id = :id',
            array('id' => $albumId)
        );
    }

    public function getAlbumArtists($albumId)
    {
        return $this->db->fetchAll(
            'SELECT a.id, a.name FROM artists a'
            .' JOIN album_artist aa ON aa.artist_id = a.id'
            .' WHERE aa.album_id = :id',
            array('id' => $albumId)
        );
    }

    public function getAlbumComposers($albumId)
    {
        return $this->db->fetchAll(
            'SELECT a.id, a.name FROM artists a'
            .' JOIN composer_song cs ON cs.composer_id = a.id'
            .' JOIN songs s ON s.id = cs.song_id'
            .' JOIN album_game ag ON ag.game_id = s.game'
            .' WHERE ag.album_id = :id'
            .' GROUP BY a.id, a.name',
            array('id' => $albumId)
        );
    }

    public function getAlbumRemixes($albumId)
    {
        return $this->db->fetchAll(
            'SELECT id, title FROM remixes WHERE album = :id',
            array('id' => $albumId)
        );
    }

    public function getAlbumArtwork($albumId)
    {
        return $this->db->fetchAll(
            'SELECT url FROM album_art WHERE album_id = :id',
            array('id' => $albumId)
        );
    }

    public function getAlbumReferences($albumId)
    {
        return $this->db->fetchAll(
            'SELECT url FROM album_reference WHERE album_id = :id',
            array('id' => $albumId)
        );
    }
}
