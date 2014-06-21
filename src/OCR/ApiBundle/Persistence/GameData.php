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

}
