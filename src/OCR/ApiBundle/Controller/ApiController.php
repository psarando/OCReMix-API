<?php

namespace OCR\ApiBundle\Controller;

use OCR\ApiBundle\Model\Data;

use FOS\RestBundle\Controller\Annotations;
use FOS\RestBundle\Controller\FOSRestController;
use FOS\RestBundle\Request\ParamFetcherInterface;

use Nelmio\ApiDocBundle\Annotation\ApiDoc;

use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpKernel\Exception\NotFoundHttpException;

/**
 * Rest controller for remixes
 *
 * @todo Use models instead of arrays for endpoint responses.
 * @package OCR\ApiBundle\Controller
 * @author psarando
 */
class ApiController extends FOSRestController
{
    private $data;

    /**
     * List all remixes.
     *
     * @ApiDoc(
     *   resource = true,
     *   statusCodes = {
     *     200 = "Returned when successful"
     *   }
     * )
     *
     * @Annotations\QueryParam(
     *      name="limit",
     *      requirements="\d+",
     *      default="50",
     *      description="How many remixes to return."
     * )
     * @Annotations\QueryParam(
     *      name="offset",
     *      requirements="\d+",
     *      default="0",
     *      description="Offset from which to start listing remixes."
     *  )
     * @Annotations\QueryParam(
     *      name="sort-order",
     *      requirements="id|title|year|size",
     *      default="id",
     *      description="The field by which to sort remixes."
     *  )
     * @Annotations\QueryParam(
     *      name="sort-dir",
     *      requirements="ASC|DESC",
     *      default="DESC",
     *      description="The direction in which to sort remixes."
     *  )
     *
     * @param Request               $request      the request object
     * @param ParamFetcherInterface $paramFetcher param fetcher service
     *
     * @return array
     */
    public function getRemixesAction(Request $request, ParamFetcherInterface $paramFetcher)
    {
        $data = new Data($this->get('database_connection'));
        $this->data = $data;

        $limit = intval($paramFetcher->get('limit'));
        $offset = intval($paramFetcher->get('offset'));
        $sortOrder = strtolower($paramFetcher->get('sort-order'));
        $sortDir = strtoupper($paramFetcher->get('sort-dir'));
        $validSortFields = array('id', 'title', 'year', 'size');

        if ($limit < 1) {
            $limit = 50;
        }
        if ($offset < 0) {
            $offset = 0;
        }
        if (!in_array($sortOrder, $validSortFields)) {
            $sortOrder = "id";
        }
        if ($sortDir != "ASC") {
            $sortDir = "DESC";
        }

        $remixes = $data->getRemixes($limit, $offset, $sortOrder, $sortDir);
        $remixes = array_map(array($this, 'formatRemixListing'), $remixes);

        return array(
            'remixes' => $remixes,
            'limit' => $limit,
            'offset' => $offset,
        );
    }

    private function formatRemixListing($remix)
    {
        $remix = array_intersect_key(
            $remix,
            array_flip(array(
                "id",
                "title",
                "game",
                "album",
            ))
        );

        $game = $this->data->getGameInfo($remix["game"]);
        $remix["game"] = array_intersect_key(
            $game,
            array_flip(array(
                "id",
                "name",
                "year",
                "album",
            ))
        );
        $remix["game"]["year"] = $this->dateToYear($remix["game"]["year"]);
        $remix["system"] = $this->data->getSystemInfo($game["system"]);

        if (!empty($remix["album"])) {
            $remix["album"] = $this->data->getAlbumInfo($remix["album"]);
        }

        $remix["artists"] = $this->data->getRemixArtists($remix["id"]);

        $songs = $this->data->getRemixSongs($remix["id"]);
        $remix["songs"] = $songs;
        $song_ids = array_map(function($song) { return $song['id']; }, $songs);
        $remix["composers"] = $this->data->getSongComposers($song_ids);

        $mixpost = $this->data->getMixPost($remix["id"]);
        $remix["date"] = $mixpost["posted"];

        return $remix;
    }

    /**
     * Get a single remix.
     *
     * @ApiDoc(
     *   output = "OCR\ApiBundle\Model\Remix",
     *   statusCodes = {
     *     200 = "Returned when successful",
     *     404 = "Returned when the remix is not found"
     *   }
     * )
     *
     * @param Request $request the request object
     * @param string  $id      the remix id
     *
     * @return array
     *
     * @throws NotFoundHttpException when remix does not exist
     */
    public function getRemixAction(Request $request, $id)
    {
        $data = new Data($this->get('database_connection'));
        $remix = $data->getRemix($id);
        if (empty($remix)) {
            throw $this->createNotFoundException("Remix does not exist with ID " . $id);
        }

        $remix["year"] = $this->dateToYear($remix["year"]);

        $game = $data->getGameInfo($remix["game"]);
        $remix["game"] = array_intersect_key(
            $game,
            array_flip(array(
                "id",
                "name",
                "name_short",
                "year",
                "album",
            ))
        );
        $remix["game"]["year"] = $this->dateToYear($game["year"]);
        $remix["publisher"] = $data->getOrgInfo($game["publisher"]);
        $remix["system"] = $data->getSystemInfo($game["system"]);

        if (!empty($remix["album"])) {
            $remix["album"] = $data->getAlbumInfo($remix["album"]);
        }

        $remix["artists"] = $data->getRemixArtists($remix["id"]);

        $songs = $data->getRemixSongs($remix["id"]);
        $remix["songs"] = $songs;
        $song_ids = array_map(function($song) { return $song['id']; }, $songs);
        $remix["composers"] = $data->getSongComposers($song_ids);

        $remix["download"] = $this->formatDownloadInfo(
            $remix,
            $data->getDownloadUrls($remix["id"])
        );
        unset($remix["size"]);
        unset($remix["md5"]);
        unset($remix["torrent"]);

        $mixpost = $data->getMixPost($remix["id"]);
        $mixpost["evaluators"] = $data->getMixPostEvaluators($remix["id"]);
        $remix["mixpost"] = $mixpost;

        return $remix;
    }

    private function dateToYear($dateStr)
    {
        return (new \DateTime($dateStr))->format("Y");
    }

    public function formatDownloadInfo($remix, $download_urls)
    {
        $download = array_intersect_key(
            $remix,
            array_flip(array(
                "size",
                "md5",
                "torrent",
            ))
        );
        $download["links"] = array_map(
            function($download) { return $download['url']; },
            $download_urls
        );

        return $download;
    }
}
