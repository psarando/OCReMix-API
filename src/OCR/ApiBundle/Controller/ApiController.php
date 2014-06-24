<?php

namespace OCR\ApiBundle\Controller;

use OCR\ApiBundle\Service\Albums;
use OCR\ApiBundle\Service\Artists;
use OCR\ApiBundle\Service\Games;
use OCR\ApiBundle\Service\Remixes;
use OCR\ApiBundle\Service\Songs;
use OCR\ApiBundle\Service\Systems;

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

    /**
     * List all games.
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
     *      description="How many games to return."
     * )
     * @Annotations\QueryParam(
     *      name="offset",
     *      requirements="\d+",
     *      default="0",
     *      description="Offset from which to start listing games."
     *  )
     * @Annotations\QueryParam(
     *      name="sort-order",
     *      requirements="id|name|name_short|name_jp|year|system",
     *      default="name",
     *      description="The field by which to sort games."
     *  )
     * @Annotations\QueryParam(
     *      name="sort-dir",
     *      requirements="ASC|DESC",
     *      default="DESC",
     *      description="The direction in which to sort games."
     *  )
     *
     * @param Request               $request      the request object
     * @param ParamFetcherInterface $paramFetcher param fetcher service
     *
     * @return array
     */
    public function getGamesAction(Request $request, ParamFetcherInterface $paramFetcher)
    {
        $validSortFields = array('id', 'name', 'name_short', 'name_jp', 'year', 'system');

        $games = new Games($this->get('database_connection'));

        return $games->getGames($paramFetcher, $validSortFields, 'name');
    }

    /**
     * Get a single game.
     *
     * @ApiDoc(
     *   output = "OCR\ApiBundle\Model\Game",
     *   statusCodes = {
     *     200 = "Returned when successful",
     *     404 = "Returned when the game is not found"
     *   }
     * )
     *
     * @param Request $request the request object
     * @param int     $id      the game id
     *
     * @return array
     *
     * @throws NotFoundHttpException when game does not exist
     */
    public function getGameAction(Request $request, $id)
    {
        $games = new Games($this->get('database_connection'));

        $game = $games->getGame($id);
        if (empty($game)) {
            throw $this->createNotFoundException("Game does not exist with ID " . $id);
        }

        return $game;
    }

    /**
     * Get songs for a single game.
     *
     * @ApiDoc(
     *   output = "OCR\ApiBundle\Model\Game",
     *   statusCodes = {
     *     200 = "Returned when successful",
     *     404 = "Returned when the game is not found"
     *   }
     * )
     *
     * @param Request $request the request object
     * @param string  $id      the game id
     *
     * @return array
     *
     * @throws NotFoundHttpException when game does not exist
     */
    public function getGameSongsAction(Request $request, $id)
    {
        $games = new Games($this->get('database_connection'));

        $game = $games->getGameSongs($id);
        if (empty($game)) {
            throw $this->createNotFoundException("Game does not exist with ID " . $id);
        }

        return $game;
    }

    /**
     * Get albums for a single game.
     *
     * @ApiDoc(
     *   output = "OCR\ApiBundle\Model\Game",
     *   statusCodes = {
     *     200 = "Returned when successful",
     *     404 = "Returned when the game is not found"
     *   }
     * )
     *
     * @param Request $request the request object
     * @param string  $id      the game id
     *
     * @return array
     *
     * @throws NotFoundHttpException when game does not exist
     */
    public function getGameAlbumsAction(Request $request, $id)
    {
        $games = new Games($this->get('database_connection'));

        $game = $games->getGameAlbums($id);
        if (empty($game)) {
            throw $this->createNotFoundException("Game does not exist with ID " . $id);
        }

        return $game;
    }

    /**
     * Get remixes for a single game.
     *
     * @ApiDoc(
     *   output = "OCR\ApiBundle\Model\Game",
     *   statusCodes = {
     *     200 = "Returned when successful",
     *     404 = "Returned when the game is not found"
     *   }
     * )
     *
     * @param Request $request the request object
     * @param string  $id      the game id
     *
     * @return array
     *
     * @throws NotFoundHttpException when game does not exist
     */
    public function getGameRemixesAction(Request $request, $id)
    {
        $games = new Games($this->get('database_connection'));

        $game = $games->getGameRemixes($id);
        if (empty($game)) {
            throw $this->createNotFoundException("Game does not exist with ID " . $id);
        }

        return $game;
    }

    /**
     * List all albums.
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
     *      description="How many albums to return."
     * )
     * @Annotations\QueryParam(
     *      name="offset",
     *      requirements="\d+",
     *      default="0",
     *      description="Offset from which to start listing albums."
     *  )
     * @Annotations\QueryParam(
     *      name="sort-order",
     *      requirements="id|name|catalog|release_date|media|vgmdb_id",
     *      default="name",
     *      description="The field by which to sort albums."
     *  )
     * @Annotations\QueryParam(
     *      name="sort-dir",
     *      requirements="ASC|DESC",
     *      default="DESC",
     *      description="The direction in which to sort albums."
     *  )
     *
     * @param Request               $request      the request object
     * @param ParamFetcherInterface $paramFetcher param fetcher service
     *
     * @return array
     */
    public function getAlbumsAction(Request $request, ParamFetcherInterface $paramFetcher)
    {
        $validSortFields = array('id', 'name', 'catalog', 'release_date', 'media', 'vgmdb_id');

        $albums = new Albums($this->get('database_connection'));

        return $albums->getAlbums($paramFetcher, $validSortFields, 'name');
    }

    /**
     * Get a single album.
     *
     * @ApiDoc(
     *   output = "OCR\ApiBundle\Model\Album",
     *   statusCodes = {
     *     200 = "Returned when successful",
     *     404 = "Returned when the album is not found"
     *   }
     * )
     *
     * @param Request $request the request object
     * @param int     $id      the album id
     *
     * @return array
     *
     * @throws NotFoundHttpException when album does not exist
     */
    public function getAlbumAction(Request $request, $id)
    {
        $albums = new Albums($this->get('database_connection'));

        $album = $albums->getAlbum($id);
        if (empty($album)) {
            throw $this->createNotFoundException("Album does not exist with ID " . $id);
        }

        return $album;
    }

    /**
     * Get composers for a single album.
     *
     * @ApiDoc(
     *   output = "OCR\ApiBundle\Model\Album",
     *   statusCodes = {
     *     200 = "Returned when successful",
     *     404 = "Returned when the album is not found"
     *   }
     * )
     *
     * @param Request $request the request object
     * @param string  $id      the album id
     *
     * @return array
     *
     * @throws NotFoundHttpException when album does not exist
     */
    public function getAlbumComposersAction(Request $request, $id)
    {
        $albums = new Albums($this->get('database_connection'));

        $album = $albums->getAlbumComposers($id);
        if (empty($album)) {
            throw $this->createNotFoundException("Album does not exist with ID " . $id);
        }

        return $album;
    }

    /**
     * Get remixes for a single album.
     *
     * @ApiDoc(
     *   output = "OCR\ApiBundle\Model\Album",
     *   statusCodes = {
     *     200 = "Returned when successful",
     *     404 = "Returned when the album is not found"
     *   }
     * )
     *
     * @param Request $request the request object
     * @param string  $id      the album id
     *
     * @return array
     *
     * @throws NotFoundHttpException when album does not exist
     */
    public function getAlbumRemixesAction(Request $request, $id)
    {
        $albums = new Albums($this->get('database_connection'));

        $album = $albums->getAlbumRemixes($id);
        if (empty($album)) {
            throw $this->createNotFoundException("Album does not exist with ID " . $id);
        }

        return $album;
    }

    /**
     * List all artists.
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
     *      description="How many artists to return."
     * )
     * @Annotations\QueryParam(
     *      name="offset",
     *      requirements="\d+",
     *      default="0",
     *      description="Offset from which to start listing artists."
     *  )
     * @Annotations\QueryParam(
     *      name="sort-order",
     *      requirements="id|name|real_name|gender|birthdate|birthplace",
     *      default="name",
     *      description="The field by which to sort artists."
     *  )
     * @Annotations\QueryParam(
     *      name="sort-dir",
     *      requirements="ASC|DESC",
     *      default="DESC",
     *      description="The direction in which to sort artists."
     *  )
     *
     * @param Request               $request      the request object
     * @param ParamFetcherInterface $paramFetcher param fetcher service
     *
     * @return array
     */
    public function getArtistsAction(Request $request, ParamFetcherInterface $paramFetcher)
    {
        $validSortFields = array('id', 'name', 'real_name', 'gender', 'birthdate', 'birthplace');

        $artists = new Artists($this->get('database_connection'));

        return $artists->getArtists($paramFetcher, $validSortFields, 'name');
    }

    /**
     * Get a single artist.
     *
     * @ApiDoc(
     *   output = "OCR\ApiBundle\Model\Artist",
     *   statusCodes = {
     *     200 = "Returned when successful",
     *     404 = "Returned when the artist is not found"
     *   }
     * )
     *
     * @param Request $request the request object
     * @param int     $id      the artist id
     *
     * @return array
     *
     * @throws NotFoundHttpException when artist does not exist
     */
    public function getArtistAction(Request $request, $id)
    {
        $artists = new Artists($this->get('database_connection'));

        $artist = $artists->getArtist($id);
        if (empty($artist)) {
            throw $this->createNotFoundException("Artist does not exist with ID " . $id);
        }

        return $artist;
    }

    /**
     * Get games for a single artist.
     *
     * @ApiDoc(
     *   output = "OCR\ApiBundle\Model\Artist",
     *   statusCodes = {
     *     200 = "Returned when successful",
     *     404 = "Returned when the artist is not found"
     *   }
     * )
     *
     * @param Request $request the request object
     * @param string  $id      the artist id
     *
     * @return array
     *
     * @throws NotFoundHttpException when artist does not exist
     */
    public function getArtistGamesAction(Request $request, $id)
    {
        $artists = new Artists($this->get('database_connection'));

        $artist = $artists->getArtistGames($id);
        if (empty($artist)) {
            throw $this->createNotFoundException("Artist does not exist with ID " . $id);
        }

        return $artist;
    }

    /**
     * Get albums for a single artist.
     *
     * @ApiDoc(
     *   output = "OCR\ApiBundle\Model\Artist",
     *   statusCodes = {
     *     200 = "Returned when successful",
     *     404 = "Returned when the artist is not found"
     *   }
     * )
     *
     * @param Request $request the request object
     * @param string  $id      the artist id
     *
     * @return array
     *
     * @throws NotFoundHttpException when artist does not exist
     */
    public function getArtistAlbumsAction(Request $request, $id)
    {
        $artists = new Artists($this->get('database_connection'));

        $artist = $artists->getArtistAlbums($id);
        if (empty($artist)) {
            throw $this->createNotFoundException("Artist does not exist with ID " . $id);
        }

        return $artist;
    }

    /**
     * Get remixes for a single artist.
     *
     * @ApiDoc(
     *   output = "OCR\ApiBundle\Model\Artist",
     *   statusCodes = {
     *     200 = "Returned when successful",
     *     404 = "Returned when the artist is not found"
     *   }
     * )
     *
     * @param Request $request the request object
     * @param string  $id      the artist id
     *
     * @return array
     *
     * @throws NotFoundHttpException when artist does not exist
     */
    public function getArtistRemixesAction(Request $request, $id)
    {
        $artists = new Artists($this->get('database_connection'));

        $artist = $artists->getArtistRemixes($id);
        if (empty($artist)) {
            throw $this->createNotFoundException("Artist does not exist with ID " . $id);
        }

        return $artist;
    }

    /**
     * List all systems.
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
     *      description="How many systems to return."
     * )
     * @Annotations\QueryParam(
     *      name="offset",
     *      requirements="\d+",
     *      default="0",
     *      description="Offset from which to start listing systems."
     *  )
     * @Annotations\QueryParam(
     *      name="sort-order",
     *      requirements="id|name|name_jp|year|release_date",
     *      default="name",
     *      description="The field by which to sort systems."
     *  )
     * @Annotations\QueryParam(
     *      name="sort-dir",
     *      requirements="ASC|DESC",
     *      default="DESC",
     *      description="The direction in which to sort systems."
     *  )
     *
     * @param Request               $request      the request object
     * @param ParamFetcherInterface $paramFetcher param fetcher service
     *
     * @return array
     */
    public function getSystemsAction(Request $request, ParamFetcherInterface $paramFetcher)
    {
        $validSortFields = array('id', 'name', 'name_jp', 'year', 'release_date');

        $systems = new Systems($this->get('database_connection'));

        return $systems->getSystems($paramFetcher, $validSortFields, 'name');
    }

    /**
     * Get a single system.
     *
     * @ApiDoc(
     *   output = "OCR\ApiBundle\Model\System",
     *   statusCodes = {
     *     200 = "Returned when successful",
     *     404 = "Returned when the system is not found"
     *   }
     * )
     *
     * @param Request $request the request object
     * @param int     $id      the system id
     *
     * @return array
     *
     * @throws NotFoundHttpException when system does not exist
     */
    public function getSystemAction(Request $request, $id)
    {
        $systems = new Systems($this->get('database_connection'));

        $system = $systems->getSystem($id);
        if (empty($system)) {
            throw $this->createNotFoundException("System does not exist with ID " . $id);
        }

        return $system;
    }

    /**
     * Get composers for a single system.
     *
     * @ApiDoc(
     *   output = "OCR\ApiBundle\Model\System",
     *   statusCodes = {
     *     200 = "Returned when successful",
     *     404 = "Returned when the system is not found"
     *   }
     * )
     *
     * @param Request $request the request object
     * @param string  $id      the system id
     *
     * @return array
     *
     * @throws NotFoundHttpException when system does not exist
     */
    public function getSystemComposersAction(Request $request, $id)
    {
        $systems = new Systems($this->get('database_connection'));

        $system = $systems->getSystemComposers($id);
        if (empty($system)) {
            throw $this->createNotFoundException("System does not exist with ID " . $id);
        }

        return $system;
    }

    /**
     * Get games for a single system.
     *
     * @ApiDoc(
     *   output = "OCR\ApiBundle\Model\System",
     *   statusCodes = {
     *     200 = "Returned when successful",
     *     404 = "Returned when the system is not found"
     *   }
     * )
     *
     * @param Request $request the request object
     * @param string  $id      the system id
     *
     * @return array
     *
     * @throws NotFoundHttpException when system does not exist
     */
    public function getSystemGamesAction(Request $request, $id)
    {
        $systems = new Systems($this->get('database_connection'));

        $system = $systems->getSystemGames($id);
        if (empty($system)) {
            throw $this->createNotFoundException("System does not exist with ID " . $id);
        }

        return $system;
    }

    /**
     * Get albums for a single system.
     *
     * @ApiDoc(
     *   output = "OCR\ApiBundle\Model\System",
     *   statusCodes = {
     *     200 = "Returned when successful",
     *     404 = "Returned when the system is not found"
     *   }
     * )
     *
     * @param Request $request the request object
     * @param string  $id      the system id
     *
     * @return array
     *
     * @throws NotFoundHttpException when system does not exist
     */
    public function getSystemAlbumsAction(Request $request, $id)
    {
        $systems = new Systems($this->get('database_connection'));

        $system = $systems->getSystemAlbums($id);
        if (empty($system)) {
            throw $this->createNotFoundException("System does not exist with ID " . $id);
        }

        return $system;
    }

    /**
     * Get remixes for a single system.
     *
     * @ApiDoc(
     *   output = "OCR\ApiBundle\Model\System",
     *   statusCodes = {
     *     200 = "Returned when successful",
     *     404 = "Returned when the system is not found"
     *   }
     * )
     *
     * @param Request $request the request object
     * @param string  $id      the system id
     *
     * @return array
     *
     * @throws NotFoundHttpException when system does not exist
     */
    public function getSystemRemixesAction(Request $request, $id)
    {
        $systems = new Systems($this->get('database_connection'));

        $system = $systems->getSystemRemixes($id);
        if (empty($system)) {
            throw $this->createNotFoundException("System does not exist with ID " . $id);
        }

        return $system;
    }
}
