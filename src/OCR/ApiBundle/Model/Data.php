<?php

namespace OCR\ApiBundle\Model;

/**
 * Data fetcher for remixes
 *
 * @package OCR\ApiBundle\Model
 * @author psarando
 */
class Data
{
    private $db;

    public function __construct($db)
    {
        $this->db = $db;
    }

    public function getRemixes($limit, $offset, $sortOrder, $sortDir)
    {
        return $this->db->fetchAll("SELECT * FROM remixes ORDER BY $sortOrder $sortDir LIMIT $limit OFFSET $offset");
    }

    public function getRemix($remix_id)
    {
        return $this->db->fetchAssoc(
            'SELECT * FROM remixes WHERE id = :id',
            array('id' => $remix_id)
        );
    }

    public function getGameInfo($game_id)
    {
        return $this->db->fetchAssoc(
            'SELECT * FROM games WHERE id = :id',
            array('id' => $game_id)
        );
    }

    public function getOrgInfo($org_id)
    {
        return $this->db->fetchAssoc(
            'SELECT id, name FROM organizations WHERE id = :id',
            array('id' => $org_id)
        );
    }

    public function getSystemInfo($system_id)
    {
        return $this->db->fetchAssoc(
            'SELECT id, name FROM systems WHERE id = :id',
            array('id' => $system_id)
        );
    }

    public function getAlbumInfo($album_id)
    {
        return $this->db->fetchAssoc(
            'SELECT id, name FROM albums WHERE id = :id',
            array('id' => $album_id)
        );
    }

    public function getRemixArtists($remix_id)
    {
        return $this->db->fetchAll(
            'SELECT a.id, a.name FROM artists a'
            .' JOIN remix_artist ra ON a.id = ra.artist_id'
            .' JOIN remixes r ON ra.remix_id = r.id'
            .' WHERE r.id = :id',
            array('id' => $remix_id)
        );
    }

    public function getRemixSongs($remix_id)
    {
        return $this->db->fetchAll(
            'SELECT s.id, s.name FROM songs s'
            .' JOIN remix_song rs ON s.id = rs.song_id'
            .' JOIN remixes r ON rs.remix_id = r.id'
            .' WHERE r.id = :id',
            array('id' => $remix_id)
        );
    }

    public function getSongComposers($song_ids)
    {
        return $this->db->fetchAll(
            'SELECT a.id, a.name FROM artists a'
            .' JOIN composer_song cs ON a.id = cs.composer_id'
            .' JOIN songs s ON cs.song_id = s.id'
            .' WHERE s.id IN (?)',
            array($song_ids),
            array(\Doctrine\DBAL\Connection::PARAM_STR_ARRAY)
        );
    }

    public function getDownloadUrls($remix_id)
    {
        return $this->db->fetchAll(
            'SELECT url FROM remix_download WHERE remix_id = :id',
            array('id' => $remix_id)
        );
    }

    public function getMixPost($remix_id)
    {
        return $this->db->fetchAssoc(
            'SELECT posted, forum_link AS forum_comments, review'
            .' FROM mixposts WHERE remix_id = :id',
            array('id' => $remix_id)
        );
    }

    public function getMixPostEvaluators($remix_id)
    {
        return $this->db->fetchAll(
            'SELECT a.id, a.name FROM artists a'
            .' JOIN mixpost_evaluator me ON a.id = me.artist_id'
            .' JOIN remixes r ON me.remix_id = r.id'
            .' WHERE r.id = :id',
            array('id' => $remix_id)
        );
    }
}
