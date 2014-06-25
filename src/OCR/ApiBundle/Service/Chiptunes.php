<?php

namespace OCR\ApiBundle\Service;

use OCR\ApiBundle\Model\Artist;
use OCR\ApiBundle\Model\Chiptune;
use OCR\ApiBundle\Model\ChiptuneCollection;
use OCR\ApiBundle\Model\Game;
use OCR\ApiBundle\Persistence\GameData;
use OCR\ApiBundle\Service;

use FOS\RestBundle\Request\ParamFetcherInterface;

/**
 * Service for listing Chiptunes and Chiptune details.
 *
 * @package OCR\ApiBundle\Service
 * @author psarando
 */
class Chiptunes extends Service
{
    private $data;

    public function __construct($db)
    {
        parent::__construct($db);
        $this->data = new GameData($db);
    }

    public function getChiptunes(ParamFetcherInterface $paramFetcher, array $validSortFields, $defaultSort)
    {
        $sortInfo = $this->parseParams($paramFetcher, $validSortFields, $defaultSort);

        $chiptunes = $this->data->getListing('chiptunes', $sortInfo);
        $chiptunes = array_map(array($this, 'formatChiptuneListing'), $chiptunes);

        return new ChiptuneCollection($chiptunes, $sortInfo);
    }

    public function getChiptune($id)
    {
        $chiptune = $this->data->getEntity('chiptunes', $id);
        if (!empty($chiptune)) {
            $chiptune = $this->formatChiptune($chiptune);
        }

        return $chiptune;
    }

    private function formatChiptuneListing($info)
    {
        $chiptune = new Chiptune($info);

        $chiptune->game = new Game($this->data->getIdName('games', $info["game"]));

        return $chiptune;
    }

    private function formatChiptune($info)
    {
        $chiptune = $this->formatChiptuneListing($info);

        $chiptune->composers = array_map(
            function($artist) { return new Artist($artist); },
            $this->data->getGameComposers($chiptune->game->id)
        );

        return $chiptune;
    }
}
