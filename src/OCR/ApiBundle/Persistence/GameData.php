<?php

namespace OCR\ApiBundle\Persistence;

use OCR\ApiBundle\Persistence;

/**
 * Data fetcher for Game Models.
 *
 * @package OCR\ApiBundle\Persistence
 * @author psarando
 */
class GameData extends Persistence
{
    public function getChiptunes($gameId)
    {
        return $this->db->fetchAll(
            'SELECT id, name, size FROM chiptunes WHERE game = :id',
            array('id' => $gameId)
        );
    }

    public function getGameComposers($gameId)
    {
        return $this->db->fetchAll(
            'SELECT a.id, a.name FROM artists a'
            .' JOIN composer_song cs ON a.id = cs.composer_id'
            .' JOIN songs s ON s.id = cs.song_id'
            .' WHERE s.game = :id'
            .' GROUP BY a.id, a.name',
            array('id' => $gameId)
        );
    }

    public function getGameSongs($gameId)
    {
        return $this->db->fetchAll(
            'SELECT s.id, s.name, COUNT(rs.remix_id) AS remix_count FROM songs s'
            .' JOIN remix_song rs ON rs.song_id = s.id'
            .' WHERE s.game = :id'
            .' GROUP BY s.id, s.name',
            array('id' => $gameId)
        );
    }

    public function getGameAlbums($gameId)
    {
        return $this->db->fetchAll(
            'SELECT a.id, a.name FROM albums a'
            .' JOIN album_game ag ON ag.album_id = a.id'
            .' WHERE ag.game_id = :id',
            array('id' => $gameId)
        );
    }

    public function getGameRemixes($gameId)
    {
        return $this->db->fetchAll(
            'SELECT r.id, r.title FROM remixes r'
            .' JOIN remix_song rs ON rs.remix_id = r.id'
            .' JOIN songs s ON rs.song_id = s.id'
            .' WHERE s.game = :id',
            array('id' => $gameId)
        );
    }

    public function getGameArtwork($gameId)
    {
        return $this->db->fetchAll(
            'SELECT url FROM game_art WHERE game_id = :id',
            array('id' => $gameId)
        );
    }

    public function getGameReferences($gameId)
    {
        return $this->db->fetchAll(
            'SELECT url FROM game_reference WHERE game_id = :id',
            array('id' => $gameId)
        );
    }
}
