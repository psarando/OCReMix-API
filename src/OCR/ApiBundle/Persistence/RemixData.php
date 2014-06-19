<?php

namespace OCR\ApiBundle\Persistence;

use OCR\ApiBundle\Persistence;

/**
 * Data fetcher for Remix Models.
 *
 * @package OCR\ApiBundle\Persistence
 * @author psarando
 */
class RemixData extends Persistence
{
    public function getRemixArtists($remix_id)
    {
        return $this->db->fetchAll(
            'SELECT a.id, a.name FROM artists a'
            .' JOIN remix_artist ra ON a.id = ra.artist_id'
            .' WHERE ra.remix_id = :id',
            array('id' => $remix_id)
        );
    }

    public function getRemixSongs($remix_id)
    {
        return $this->db->fetchAll(
            'SELECT s.id, s.name FROM songs s'
            .' JOIN remix_song rs ON s.id = rs.song_id'
            .' WHERE rs.remix_id = :id',
            array('id' => $remix_id)
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
            .' WHERE me.remix_id = :id',
            array('id' => $remix_id)
        );
    }
}
