<?php

namespace OCR\ApiBundle\Controller;

use OCR\ApiBundle\Service\Remixes;
use OCR\ApiBundle\Service\Songs;

use FOS\RestBundle\Controller\Annotations;
use FOS\RestBundle\Controller\FOSRestController;
use FOS\RestBundle\Request\ParamFetcherInterface;

use Nelmio\ApiDocBundle\Annotation\ApiDoc;

use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpKernel\Exception\NotFoundHttpException;

/**
 * Rest controller for remixes
 *
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
     *   output = "OCR\ApiBundle\Model\RemixCollection",
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
        $validSortFields = array('id', 'title', 'year', 'size');

        $remixes = new Remixes($this->get('database_connection'));

        return $remixes->getRemixes($paramFetcher, $validSortFields, "id");
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
        $remixes = new Remixes($this->get('database_connection'));

        $remix = $remixes->getRemix($id);
        if (empty($remix)) {
            throw $this->createNotFoundException("Remix does not exist with ID " . $id);
        }

        return $remix;
    }

    /**
     * List all songs.
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
     *      description="How many songs to return."
     * )
     * @Annotations\QueryParam(
     *      name="offset",
     *      requirements="\d+",
     *      default="0",
     *      description="Offset from which to start listing songs."
     *  )
     * @Annotations\QueryParam(
     *      name="sort-order",
     *      requirements="id|name",
     *      default="name",
     *      description="The field by which to sort songs."
     *  )
     * @Annotations\QueryParam(
     *      name="sort-dir",
     *      requirements="ASC|DESC",
     *      default="DESC",
     *      description="The direction in which to sort songs."
     *  )
     *
     * @param Request               $request      the request object
     * @param ParamFetcherInterface $paramFetcher param fetcher service
     *
     * @return array
     */
    public function getSongsAction(Request $request, ParamFetcherInterface $paramFetcher)
    {
        $validSortFields = array('id', 'name');

        $songs = new Songs($this->get('database_connection'));

        return $songs->getSongs($paramFetcher, $validSortFields, 'name');
    }

    /**
     * Get a single song.
     *
     * @ApiDoc(
     *   output = "OCR\ApiBundle\Model\Song",
     *   statusCodes = {
     *     200 = "Returned when successful",
     *     404 = "Returned when the song is not found"
     *   }
     * )
     *
     * @param Request $request the request object
     * @param int     $id      the song id
     *
     * @return array
     *
     * @throws NotFoundHttpException when song does not exist
     */
    public function getSongAction(Request $request, $id)
    {
        $songs = new Songs($this->get('database_connection'));

        $song = $songs->getSong($id);
        if (empty($song)) {
            throw $this->createNotFoundException("Song does not exist with ID " . $id);
        }

        return $song;
    }

    /**
     * Get remixes for a single song.
     *
     * @ApiDoc(
     *   output = "OCR\ApiBundle\Model\Song",
     *   statusCodes = {
     *     200 = "Returned when successful",
     *     404 = "Returned when the song is not found"
     *   }
     * )
     *
     * @param Request $request the request object
     * @param string  $id      the song id
     *
     * @return array
     *
     * @throws NotFoundHttpException when song does not exist
     */
    public function getSongRemixesAction(Request $request, $id)
    {
        $songs = new Songs($this->get('database_connection'));

        $song = $songs->getSongRemixes($id);
        if (empty($song)) {
            throw $this->createNotFoundException("Song does not exist with ID " . $id);
        }

        return $song;
    }
}
