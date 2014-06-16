#### If I were to design an API for OCReMix.org, I might do something like this...

This API is a rough draft I want to propose to OCR and is probably missing some parts.
Notably, I have only described endpoints for fetching single entities given an ID. I have not yet described endpoints for listing, searching, or filtering items.

#### OCReMix.org URL conversions from API IDs.

Some devs may want links to OCR's site for entities such as artists, games, songs, and composers. I have opted to leave URLs out of this spec, but I have included a conversion table below describing how an OCR URL can be reconstructed using an ID from this API. In fact, the IDs used as examples in this spec were taken from URLs on the ocremix.org site.

| API ID | URL |
| --- | --- |
| `remixes` | http://ocremix.org/remix/ `id` |
| `artists` (Remixers/Arrangers/Composers/Bands) | http://ocremix.org/artist/ `id` |
| `songs` | http://ocremix.org/song/ `id` |
| `albums` | http://ocremix.org/album/ `id` |
| `chiptunes` | http://ocremix.org/chip/ `id` |
| `games` | http://ocremix.org/game/ `id` |
| `systems` | http://ocremix.org/system/ `id` |
| `publishers` | http://ocremix.org/org/ `id` |

These table conversions can make a URL shorter than what you get navigating ocremix.org, however, a short URL constructed using this API will return an HTTP 301 response with the full URL in the `Location` header. So a request to the URL `http://ocremix.org/artist/4279` will return a 301 redirect to `http://ocremix.org/artist/4279/djpretzel`.

Alternatively, the full URLs could be included for each entity, such as the following:
```json
{
    "id": 4279,
    "name": "djpretzel",
    "url": "http://ocremix.org/artist/4279/djpretzel"
}
```

## Table of Contents
* [Remixes](#remixes)
* [Source Songs](#source-songs)
  * [Remixes by Song](#remixes-by-song)
* [Games](#games)
  * [Songs by Game](#songs-by-game)
  * [Albums by Game](#albums-by-game)
  * [Remixes by Game](#remixes-by-game)
* [Albums](#albums)
  * [Composers by Album](#composers-by-album)
  * [Remixes by Album](#remixes-by-album)
* [Composers and Artists](#composers-and-artists)
  * [Games by Artist](#games-by-artist)
  * [Albums by Artist](#albums-by-artist)
  * [Remixes by Artist](#remixes-by-artist)
* [Chiptunes](#chiptunes)
* [Systems](#systems)
  * [Composers by System](#composers-by-system)
  * [Games by System](#games-by-system)
  * [Albums by System](#albums-by-system)
  * [Remixes by System](#remixes-by-system)
* [Organizations](#organizations)
  * [Composers by Organization](#composers-by-organization)
  * [Games by Organization](#games-by-organization)
  * [Systems by Organization](#systems-by-organization)
  * [Albums by Organization](#albums-by-organization)
  * [Remixes by Organization](#remixes-by-organization)

## Remixes

### GET /remixes/OCR00000

My idea with this endpoint is to return enough information to completely tag an mp3, but also to include the info found on each mixpost page (`http://ocremix.org/remix/OCR00000`), or at least include an ID for another endpoint that can fill in the rest of the info (e.g. `http://ocremix.org/game/ID`).

Optional keys for this endpoint:

* `mixpost` Although I like the idea of including this info in this endpoint, it's also reasonable to have a separate endpoint to fetch this info given an `OCR00000` ID.
* `publisher`, `system`, `game.year` These can be fetched from the `/game/` endpoint instead.

```json
{
    "id": "OCR00000",
    "title": "remix title",
    "artists": [
        {
            "id": 0000,
            "name": "remixer"
        },
        {
            "id": 0000,
            "name": "..."
        }
    ],
    "composers": [
        {
            "id": 0000,
            "name": "Composer"
        },
        {
            "id": 0000,
            "name": "..."
        }
    ],
    "songs": [
        {
            "id": 0000,
            "name": "orig song title"
        },
        {
            "id": 0000,
            "name": "..."
        }
    ],
    "game": {
        "id": 0000,
        "name": "Video Game: perhaps some subtitle",
        "name_short": "Video Game",
        "year": 0000
    },
    "publisher": {
        "id": 0000,
        "name": "game publisher"
    },
    "system": {
        "id": "xxxx",
        "name": "game system"
    },
    "encoder": "mp3 encoder? mixer?",
    "year": 0000,
    "album": {
        "id": 00,
        "name": "remix album"
    },
    "download": {
        "size": 0000,
        "md5": "...",
        "torrent": "http://bt.ocremix.org/torrents/OC_ReMix_-_0000_to_0000_%5Bv00000000%5D.torrent",
        "links": [
            "http://ocr.blueblue.fr/files/music/remixes/..._OC_ReMix.mp3",
            "http://iterations.org/files/music/remixes/..._OC_ReMix.mp3",
            "http://ocremix.dreamhosters.com/files/music/remixes/..._OC_ReMix.mp3",
            "http://ocrmirror.org/files/music/remixes/..._OC_ReMix.mp3"
        ]
    },
    "comment": "mp3 comment tag",
    "lyrics": "...",
    "mixpost": {
        "date": "0000-00-00",
        "forum_comments": "http://ocremix.org/forums/showthread.php?t=.....",
        "evaluators": [
            {
                "id": 0000,
                "name": "evaluator artist"
            },
            "the judges"
        ],
        "review": "<p>\n\t...</p><p> ― <a href=\"/artist/4279/djpretzel\">djpretzel</a></p>"
    }
}
```

### OCReMix mp3 Tagging Standard

| Tag | Tag Description | API JSON value |
| --- | --- | --- |
| TXXX | User Text | `id` |
| TALB | Album | http://ocremix.org |
| TENC | Encoder | `encoder` |
| TIT1 | Grouping | `game.name` |
| TIT2 | Title | `game.name_short` '`title`' OC ReMix |
| TIT3 | Subtitle | `title` |
| TCOM | Composer | `composers[*].name` |
| TCON | Genre | Game |
| TCOP | Copyright | `publisher.name` |
| TOAL | Original Album | `game.name` |
| TOPE | Original Artist | `system.name` |
| TPE1 | Artist | `artists[*].name` |
| TPE2 | Album Artist | OverClocked ReMix |
| TPUB | Publisher | OverClocked ReMix |
| TRCK | Track | (int)(`id` - "OCR") |
| TYER | Year | `year` |
| TCMP | iTunes Compilaton Flag | 1 |
| WOAR | Artist Url | http://ocremix.org |
| WXXX | User Url | `artists[0].url` OR Location in HTTP 301 response from http://ocremix.org/artist/ `artists[0].id` |
| COMM | Comment | `comment` |
| USLT | Lyrics | `lyrics` |
| APIC | Album Artwork | The OCR album art |

### Example

`curl http://ocremix.org/api/v1/remixes/OCR02700`

```json
{
    "id": "OCR02700",
    "title": "Terra's Resolve"
    "artists": [
        {
            "id": 3617,
            "name": "Chad Seiter"
        }
    ],
    "composers": [
        {
            "id": 3,
            "name": "Nobuo Uematsu"
        }
    ],
    "songs": [
        {
            "id": 11,
            "name": "Terra"
        }
    ],
    "game": {
        "id": 6,
        "name": "Final Fantasy VI",
        "name_short": "Final Fantasy VI",
        "year": 1994
    },
    "publisher": {
        "id": 123,
        "name": "Square"
    },
    "system": {
        "id": "snes",
        "name": "SNES"
    },
    "encoder": "Chad Seiter",
    "year": 2013,
    "album": {
        "id": 46,
        "name": "Final Fantasy VI: Balance and Ruin"
    },
    "download": {
        "size": 7290710,
        "md5": "fb51c7a27b943499557952e96e24f4fa",
        "links": [
            "http://ocr.blueblue.fr/files/music/remixes/Final_Fantasy_6_Terra%27s_Resolve_OC_ReMix.mp3",
            "http://iterations.org/files/music/remixes/Final_Fantasy_6_Terra%27s_Resolve_OC_ReMix.mp3",
            "http://ocremix.dreamhosters.com/files/music/remixes/Final_Fantasy_6_Terra%27s_Resolve_OC_ReMix.mp3",
            "http://ocrmirror.org/files/music/remixes/Final_Fantasy_6_Terra%27s_Resolve_OC_ReMix.mp3"
        ]
    },
    "comment": "http://ff6.ocremix.org\r\nFinal Fantasy VI: Balance and Ruin\r\n\r\nChad Seiter: arrangement, orchestration\r\nSusie Benchasil Seiter: conducting\r\nWarren Brown: mixing\r\nPeter Fuchs: mixing\r\nBernie Grundman at Bernie Grundman Mastering: mastering\r\nSlovak National Orchestra: performance\r\nSlovak Radio Concert Hall (Bratislava, Slovakia): recording hall\r\n\r\nChad: To this day, Uematsu-san's \"Terra\" has remained one of my favorite themes of all time. I have been enamored with it since I was young. I wanted to musically capture Terra's strife and make sure my homage told her story accurately. I felt I could only do it with an orchestra, so I recorded \"Terra's Resolve\" with an absolutely gigantic 120 piece orchestral ensemble during the recording of my original score to the latest Star Trek video game.\r\n\r\nChad Seiter\r\nhttp://chadseiter.com",
    "mixpost": {
        "date": "2013-07-01",
        "forum_comments": "http://ocremix.org/forums/showthread.php?t=44285",
        "evaluators": [
            {
                "id": 4279,
                "name": "djpretzel"
            }
        ],
        "review": "<p>\n\tAnd now at long last we come to the end of our fifteen mix flood, designed to give you just a taste of <a href=\"http://ff6.ocremix.org\"><em><strong>Final Fantasy VI: Balance and Ruin</strong></em></a>. <strong>There are FIFTY-NINE MORE TRACKS to enjoy</strong>, including multiple arrangements from Joshua Morse and bLiNd, new music from Disco Dan &amp; Beatdrop, cuts from halc, Mattias, McVaffe, Danny B, Nutritious, Tepid, Sole Signal, Brandon Strader, Rexy, and more... a batshit epic four-part rock/metal opus from Snappleman, norg, and Captain Finbeard... hell, I've even got a mix on there myself :) If these fifteen flood mixes represented the ENTIRE album, I STILL think we'd have achieved something very special, but they are a fraction of the whole, and you'll be seeing &amp; hearing much more in the weeks to come.</p>\n<p>\n\tAndy &amp; Mike have repeated the success of <a href=\"http://ff7.ocremix.org\"><em><strong>Final Fantasy VII: Voices of the Lifestream</strong></em></a>, and I think it's fair to say that they've surpassed it; this is an album that represents everything OCR stands for. As I mentioned with the first mix we posted, I think there was a sense of inevitability that someday we'd do an FF6 album, but there was never ANY guarantee it would turn out like this. I'm floored, so damn proud, and very honored to have played my part in helping to create a community that is capable of such feats.</p>\n<p>\n\tWe close out with a special treat; while working with the <strong>Slovak National Orchestra</strong> on a game soundtrack he composed, Chad Seiter - arranger for the fantastic <a href=\"http://zelda-symphony.com/\"><em><strong>The Legend of Zelda: Symphony of the Goddesses</strong></em></a> concert tour - was able to make time for a recording of his own arrangement of Terra's theme:</p>\n<blockquote>\n\t<p>\n\t\t\"To this day, Uematsu-san's \"Terra\" has remained one of my favorite themes of all time. I have been enamored with it since I was young. I wanted to musically capture Terra's strife and make sure my homage told her story accurately. <strong>I felt I could only do it with an orchestra, so I recorded \"Terra's Resolve\" with an absolutely gigantic 120 piece orchestral ensemble during the recording of my original score to the latest Star Trek video game.</strong>\"</p>\n</blockquote>\n<p>\n\tFull details/credits on the recording:</p>\n<ul>\n\t<li>\n\t\t<strong>Chad Seiter:</strong> arrangement, orchestration</li>\n\t<li>\n\t\t<strong>Susie Benchasil Seiter:</strong> conducting</li>\n\t<li>\n\t\t<strong>Warren Brown:</strong> mixing</li>\n\t<li>\n\t\t<strong>Peter Fuchs:</strong> mixing</li>\n\t<li>\n\t\t<strong>Bernie Grundman at Bernie Grundman Mastering:</strong> mastering</li>\n\t<li>\n\t\t<strong>Slovak National Orchestra: </strong>performance</li>\n\t<li>\n\t\t<strong>Slovak Radio Concert Hall (Bratislava, Slovakia):</strong> recording hall</li>\n</ul>\n<p>\n\tAndy writes:</p>\n<blockquote>\n\t<p>\n\t\t\"How do you take a theme as memorable and nostalgic as Terra, which has been covered so many times in so many styles, and bring something new to the table? I'm extremely glad that Chad Seiter answered that question for us with this incredible rendition. <strong>Chad's professional work on some of the biggest blockbuster movies of the last 10 years speaks for itself, and all that experience shines through in \"Terra's Resolve.\"</strong> It's grand, sweeping, and cinematic in scope, but also very dynamic and emotional. It perfectly suits the character, and if Square were to ever remake Final Fantasy VI, I can't imagine a better arrangement for the theme.\"</p>\n</blockquote>\n<p>\n\tAndy said it all; so very cool that Chad could make this happen, and <strong>his piece really capitalizes on the power &amp; energy of a full symphony orchestra</strong>. This is arrangement on a scale as grand as the game itself, and I think it's an excellent way to end the proceedings and complete our introduction of <a href=\"http://ff6.ocremix.org\"><em><strong>Balance and Ruin</strong></em></a> to the world. I've said quite a bit about the album, the music, and the people who created it, but I do need to end by thanking first and foremost legendary composer <a href=\"/artist/3/nobuo-uematsu\"><strong>Nobuo Uematsu</strong></a>, who has given us so many memories and so much music that no homage could ever be too grand. Andy &amp; Mike have done an amazing job steering this monolithic project through to completion and ensuring that quality was never sacrificed for quantity, OA provided some lovely artwork, Wesley Cho chipped on the website, José came through with another brilliant trailer while working under new and challenging conditions, and all the MANY artists who poured their hearts &amp; souls into B&amp;R have done an amazing job realizing the full potential of this vision. We thank all the <a href=\"http://www.kickstarter.com/projects/ocremix/an-epic-5-disc-ff6-fan-album-from-oc-remix-take-tw\"><strong>2,509 Kickstarter backers</strong></a> who supported us and made this project possible - you've been amazingly positive &amp; patient even as we've missed a few deadlines to guarantee it would all be worth the wait. And we thank <a href=\"http://www.square-enix.com/\"><strong>Square Enix</strong></a> for working with us &amp; supporting us after some initial issues, ensuring that artists and fans alike would not be disappointed.</p>\n<p>\n\t<strong><span style=\"font-size:16px;\">We're proud to release our 40th arrangement album, <a href=\"http://ff6.ocremix.org\"><em>Final Fantasy VI: Balance and Ruin</em></a>, to all fans of video game music the world over. We truly hope you enjoy it, and that if you do, you help us spread the news about the album, about OC ReMix, and about VGM!<br />\n\t</span></strong></p><p> ― <a href=\"/artist/4279/djpretzel\">djpretzel</a></p>"
    }
}
```

## Source Songs

### GET /songs/0000

```json
{
    "id": 0000,
    "name": "Song Name",
    "ost_names": [
        "Song OST Name",
        "..."
    ],
    "aliases": [
        "Song Alias",
        "..."
    ],
    "game": {
        "id": 0000,
        "name": "Video Game"
    },
    "composers": [
        {
            "id": 0000,
            "name": "Composer"
        },
        {
            "id": 0000,
            "name": "..."
        }
    ],
    "chiptunes": [
        {
            "id": 0000,
            "name": "Chiptune-filename.rsn",
            "size": 0000
        }
    ]
}
```

### Example

`curl http://ocremix.org/api/v1/songs/11`

```json
{
    "id": 11,
    "name": "Terra",
    "ost_names": [
        "Terra",
        "ティナのテーマ"
    ],
    "game": {
        "id": 6,
        "name": "Final Fantasy VI"
    },
    "composers": [
        {
            "id": 3,
            "name": "Nobuo Uematsu"
        }
    ],
    "chiptunes": [
        {
            "id": 6384,
            "name": "final-fantasy-vi-snes-[SPC-ID6384].rsn",
            "size": 289500
        }
    ]
}
```

## Remixes by Song

### GET /songs/0000/remixes

```json
{
    "id": 0000,
    "name": "Song Name",
    "remixes": [
        {
            "id": "OCR00000",
            "title": "mix title"
        },
        {
            "id": "OCR00000",
            "title": "..."
        }
    ]
}
```

### Example

`curl http://ocremix.org/api/v1/songs/11/remixes`

```json
{
    "id": 11,
    "name": "Terra",
    "remixes": [
        {
            "id": "OCR00068",
            "title": "North Medley"
        },
        {
            "id": "OCR00205",
            "title": "Death on the Snowfield"
        },
        {
            "id": "OCR02700",
            "title": "Terra's Resolve"
        },
        {
            "id": "OCR00000",
            "title": "..."
        }
    ]
}
```

## Games

### GET /games/000

```json
{
    "id": 000,
    "name": "Video Game: perhaps some subtitle",
    "name_short": "Video Game",
    "name_jp": "...",
    "year": 0000,
    "publisher": {
        "id": 000,
        "name": "..."
    },
    "system": {
        "id": "xxxx",
        "name": "..."
    },
    "composers": [
        {
            "id": 0000,
            "name": "Composer"
        },
        {
            "id": 0000,
            "name": "..."
        }
    ],
    "artwork": [
        "http://ocremix.org/files/images/games/...-title-00000.png",
        "http://ocremix.org/files/images/games/...-cover-front-00000.jpg",
        "http://ocremix.org/files/images/games/...-cover-front-eu-00000.jpg",
        "http://ocremix.org/files/images/games/...-cover-front-jp-00000.jpg",
        "http://ocremix.org/files/images/games/...-cover-back-00000.jpg",
        "http://ocremix.org/files/images/games/...-logo-00000.png"
    ],
    "references": [
        "http://www.gamefaqs.com/...",
        "http://www.gamespot.com/...",
        "http://www.gamestats.com/...",
        "http://www.imdb.com/title/...",
        "http://www.mobygames.com/game/...",
        "http://www.uvlist.net/...",
        "http://en.wikipedia.org/wiki/..."
    ],
    "chiptunes": [
        {
            "id": 0000,
            "name": "Chiptune-filename.rsn",
            "size": 0000
        },
        {
            "id": 0000,
            "name": "...",
            "size": 0000
        }
    ]
}
```

### Example

`curl http://ocremix.org/api/v1/games/6`

```json
{
    "id": 6,
    "name": "Final Fantasy VI",
    "name_short": "Final Fantasy VI",
    "name_jp": "ファイナルファンタジーVI",
    "year": 1994,
    "publisher": {
        "id": 123,
        "name": "Square"
    },
    "system": {
        "id": "snes",
        "name": "SNES"
    },
    "composers": [
        {
            "id": 3,
            "name": "Nobuo Uematsu"
        }
    ],
    "artwork": [
        "http://ocremix.org/files/images/games/snes/6/final-fantasy-vi-snes-title-77586.png",
        "http://ocremix.org/files/images/games/snes/6/final-fantasy-iii-snes-cover-front-34274.jpg",
        "http://ocremix.org/files/images/games/snes/6/final-fantasy-iii-snes-cover-front-jp-33494.jpg",
        "http://ocremix.org/files/images/games/snes/6/final-fantasy-iii-snes-cover-back-33596.jpg",
        "http://ocremix.org/files/images/games/snes/6/final-fantasy-vi-snes-logo-73911.png"
    ],
    "references": [
        "http://www.gamefaqs.com/snes/554041-final-fantasy-iii",
        "http://www.gamespot.com/snes/rpg/finalfantasy3/index.html",
        "http://www.gamestats.com/objects/006/006865/index.html",
        "http://www.imdb.com/title/tt0210061/",
        "http://www.mobygames.com/game/snes/final-fantasy-vi",
        "http://www.uvlist.net/game-6372",
        "http://en.wikipedia.org/wiki/Final_Fantasy_VI"
    ],
    "chiptunes": [
        {
            "id": 6384,
            "name": "final-fantasy-vi-snes-[SPC-ID6384].rsn",
            "size": 289500
        }
    ]
}
```

## Songs by Game

### GET /games/000/songs

```json
{
    "id": 000,
    "name": "Video Game",
    "songs": [
        {
            "id": 0000,
            "name": "orig song title"
        },
        {
            "id": 0000,
            "name": "..."
        }
    ]
}
```

### Example

`curl http://ocremix.org/api/v1/games/6/songs`

```json
{
    "id": 6,
    "name": "Final Fantasy VI",
    "songs": [
        {
            "id": 10,
            "name": "Wild West",
            "remix_count": 7
        },
        {
            "id": 11,
            "name": "Terra",
            "remix_count": 17
        },
        {
            "id": 12,
            "name": "Devil's Lab",
            "remix_count": 8
        },
        {
            "id": 0000,
            "name": "...",
            "remix_count": 0
        }
    ]
}
```

## Albums by Game

### GET /games/000/albums

```json
{
    "id": 000,
    "name": "Video Game",
    "albums": [
        {
            "id": 00,
            "name": "remix album"
        },
        {
            "id": 00,
            "name": "..."
        }
    ]
}
```

### Example

`curl http://ocremix.org/api/v1/games/6/albums`

```json
{
    "id": 6,
    "name": "Final Fantasy VI",
    "albums": [
        {
            "id": 46,
            "name": "Final Fantasy VI: Balance and Ruin"
        },
        {
            "id": 00,
            "name": "..."
        }
    ]
}
```

## Remixes by Game

### GET /games/000/remixes

```json
{
    "id": 000,
    "name": "Video Game",
    "remixes": [
        {
            "id": "OCR00000",
            "title": "mix title"
        },
        {
            "id": "OCR00000",
            "title": "..."
        }
    ]
}
```

### Example

`curl http://ocremix.org/api/v1/games/6/remixes`

```json
{
    "id": 6,
    "name": "Final Fantasy VI",
    "remixes": [
        {
            "id": "OCR02885",
            "title": "Go-Go Gadget Gonkulator"
        },
        {
            "id": "OCR02854",
            "title": "La Montaña de los Caballos Jóvenes"
        },
        {
            "id": "OCR02700",
            "title": "Terra's Resolve"
        },
        {
            "id": "OCR00000",
            "title": "..."
        }
    ]
}
```

## Albums

### GET /albums/000

```json
{
    "id": 00,
    "name": "album name",
    "torrent": "http://bt.ocremix.org/torrents/...torrent",
    "catalog": "XXXX-0000",
    "games": [
        {
            "id": 0000,
            "name": "Video Game"
        }
    ],
    "publisher": {
        "id": 000,
        "name": "..."
    },
    "release_date": "0000-00-00",
    "media": "...",
    "vgmdb_id": 00000,
    "artists": [
        {
            "id": 0000,
            "name": "remixer"
        },
        {
            "id": 0000,
            "name": "..."
        }
    ],
    "artwork": [
        "http://ocremix.org/files/images/albums/...",
        "http://ocremix.org/files/images/albums/..."
    ],
    "references": [
        "http://homepage",
        "http://..."
    ],
    "forum_comments": "http://ocremix.org/forums/showthread.php?t=..."
}
```

### Example

`curl http://ocremix.org/api/v1/albums/46`

```json
{
    "id": 46,
    "name": "Final Fantasy VI: Balance and Ruin",
    "torrent": "http://bt.ocremix.org/torrents/Final_Fantasy_VI_-_Balance_and_Ruin.torrent",
    "catalog": "OCRA-0040",
    "games": [
        {
            "id": 6,
            "name": "Final Fantasy VI"
        }
    ],
    "publisher": {
        "id": 130,
        "name": "OverClocked ReMix"
    },
    "release_date": "2013-07-01",
    "media": "5 Digital",
    "vgmdb_id": 40213,
    "artists": [
        {
            "id": 36,
            "name": "Jake Kaufman"
        },
        {
            "id": 3617,
            "name": "Chad Seiter"
        },
        {
            "id": 4280,
            "name": "McVaffe"
        },
        {
            "id": 0000,
            "name": "..."
        }
    ],
    "artwork": [
        "http://ocremix.org/files/images/albums/6/2/46-132.png"
    ],
    "references": [
        "http://ff6.ocremix.org/"
    ],
    "forum_comments": "http://ocremix.org/forums/showthread.php?t=44287"
}
```

## Composers by Album

### GET /albums/000/composers

```json
{
    "id": 00,
    "name": "album name",
    "composers": [
        {
            "id": 0000,
            "name": "Composer"
        },
        {
            "id": 0000,
            "name": "..."
        }
    ]
}
```

### Example

`curl http://ocremix.org/api/v1/albums/46/composers`

```json
{
    "id": 46,
    "name": "Final Fantasy VI: Balance and Ruin",
    "composers": [
        {
            "id": 3,
            "name": "Nobuo Uematsu"
        }
    ]
}
```

## Remixes by Album

### GET /albums/000/remixes

```json
{
    "id": 00,
    "name": "album name",
    "remixes": [
        {
            "id": "OCR00000",
            "title": "mix title"
        },
        {
            "id": "OCR00000",
            "title": "..."
        }
    ]
}
```

### Example

`curl http://ocremix.org/api/v1/albums/46/remixes`

```json
{
    "id": 46,
    "name": "Final Fantasy VI: Balance and Ruin",
    "remixes": [
        {
            "id": "OCR02885",
            "title": "Go-Go Gadget Gonkulator"
        },
        {
            "id": "OCR02854",
            "title": "La Montaña de los Caballos Jóvenes"
        },
        {
            "id": "OCR02700",
            "title": "Terra's Resolve"
        },
        {
            "id": "OCR00000",
            "title": "..."
        }
    ]
}
```

## Composers and Artists

### GET /artists/000

```json
{
    "id": 000,
    "name": "Artist Name",
    "real_name": "...",
    "gender": "M/F",
    "credits": [
        {
            "role": "Composer",
            "credits": 00
        },
        {
            "role": "ReMixer",
            "credits": 00
        }
    ],
    "aliases": [
        "...",
        "..."
    ],
    "birthdate": "0000-00-00",
    "birthplace": "city/state/provence, country",
    "groups": [
        {
            "id": 0000,
            "name": "Band Name"
        },
        {
            "id": 0000,
            "name": "..."
        }
    ],
    "forum_profile": "http://ocremix.org/forums/member.php?u=...",
    "images": [
        "http://ocremix.org/files/images/artists/...",
        "http://ocremix.org/files/images/artists/..."
    ],
    "references": [
        "http://www.facebook.com/...",
        "http://twitter.com/...",
        "http://..."
    ]
}
```

### Example

`curl http://ocremix.org/api/v1/artists/3`

```json
{
    "id": 3,
    "name": "Nobuo Uematsu",
    "real_name": "植松伸夫",
    "gender": "M",
    "credits": [
        {
            "role": "Composer",
            "credits": 26
        }
    ],
    "aliases": [
        "ノビヨ"
    ],
    "birthdate": "1959-03-21",
    "birthplace": "Kōchi, Japan",
    "images": [
        "http://ocremix.org/files/images/artists/nobuo-uematsu-3.jpg",
        "http://ocremix.org/files/images/artists/nobuo-uematsu-205.jpg",
        "http://ocremix.org/files/images/artists/nobuo-uematsu-206.jpg",
        "http://ocremix.org/files/images/artists/nobuo-uematsu-207.jpg",
        "http://ocremix.org/files/images/artists/nobuo-uematsu-208.jpg",
        "http://ocremix.org/files/images/artists/nobuo-uematsu-760.jpg"
    ],
    "references": [
        "http://www.facebook.com/UematsuNobuo",
        "http://www.square-enix-usa.com/uematsu/",
        "http://www.imdb.com/name/nm0879965/",
        "http://www.last.fm/music/%E6%A4%8D%E6%9D%BE%E4%BC%B8%E5%A4%AB",
        "http://www.mobygames.com/developer/sheet/view/developerId,33339/",
        "http://musicbrainz.org/artist/92bb085a-2924-4479-b627-181a1835d2f5",
        "http://squarehaven.com/people/Nobuo-Uematsu/",
        "http://twitter.com/UematsuNobuo",
        "http://vgmdb.net/artist/77",
        "http://en.wikipedia.org/wiki/Nobuo_Uematsu"
    ]
}
```

## Games by Artist

### GET /artists/000/games

```json
{
    "id": 000,
    "name": "Artist Name",
    "games": [
        {
            "id": 0000,
            "name": "Video Game"
        },
        {
            "id": 00,
            "name": "..."
        }
    ]
}
```

### Example

`curl http://ocremix.org/api/v1/artists/3/games`

```json
{
    "id": 3,
    "name": "Nobuo Uematsu",
    "games": [
        {
            "id": 6,
            "name": "Final Fantasy VI"
        },
        {
            "id": 16,
            "name": "Chrono Trigger"
        },
        {
            "id": 00,
            "name": "..."
        }
    ]
}
```

## Albums by Artist

### GET /artists/000/albums

```json
{
    "id": 000,
    "name": "Artist Name",
    "albums": [
        {
            "id": 00,
            "name": "remix album"
        },
        {
            "id": 00,
            "name": "..."
        }
    ]
}
```

### Example

`curl http://ocremix.org/api/v1/artists/3/albums`

```json
{
    "id": 3,
    "name": "Nobuo Uematsu",
    "albums": [
        {
            "id": 1,
            "name": "Final Fantasy VII: Voices of the Lifestream"
        },
        {
            "id": 46,
            "name": "Final Fantasy VI: Balance and Ruin"
        },
        {
            "id": 00,
            "name": "..."
        }
    ]
}
```

## Remixes by Artist

### GET /artists/000/remixes

```json
{
    "id": 000,
    "name": "Artist Name",
    "remixes": [
        {
            "id": "OCR00000",
            "title": "mix title"
        },
        {
            "id": "OCR00000",
            "title": "..."
        }
    ]
}
```

### Example

`curl http://ocremix.org/api/v1/artists/3/remixes`

```json
{
    "id": 3,
    "name": "Nobuo Uematsu",
    "remixes": [
        {
            "id": "OCR02885",
            "title": "Go-Go Gadget Gonkulator"
        },
        {
            "id": "OCR02854",
            "title": "La Montaña de los Caballos Jóvenes"
        },
        {
            "id": "OCR02700",
            "title": "Terra's Resolve"
        },
        {
            "id": "OCR00000",
            "title": "..."
        }
    ]
}
```

## Chiptunes

### GET /chiptunes/000

```json
{
    "id": 0000,
    "name": "Chiptune Name",
    "size": 000000,
    "file": "....rsn",
    "url": "http://ocremix.dreamhosters.com/files/music/chiptunes/archives/...",
    "md5": "...",
    "format": "...",
    "songs": 00,
    "composers": [
        {
            "id": 0000,
            "name": "Composer"
        },
        {
            "id": 0000,
            "name": "..."
        }
    ],
    "game": {
        "id": 0000,
        "name": "Video Game"
    }
}
```

### Example

`curl http://ocremix.org/api/v1/chiptunes/6384`

```json
{
    "id": 6384,
    "name": "Final Fantasy VI",
    "size": 289500,
    "file": "final-fantasy-vi-snes-[SPC-ID6384].rsn",
    "url": "http://ocremix.dreamhosters.com/files/music/chiptunes/archives/f/final-fantasy-vi-snes-%5BSPC-ID6384%5D.rsn",
    "md5": "1d40a983b305fbcf9c392433f78c5a22",
    "format": "SPC (RAR)",
    "songs": 82,
    "composers": [
        {
            "id": 3,
            "name": "Nobuo Uematsu"
        }
    ],
    "game": {
        "id": 6,
        "name": "Final Fantasy VI"
    }
}
```

## Systems

### GET /systems/xxxx

```json
{
    "id": "xxxx",
    "name": "System",
    "name_jp": "...",
    "year": 0000,
    "publisher": {
        "id": 000,
        "name": "..."
    },
    "references": [
        "http://www.gamefaqs.com/...",
        "http://www.uvlist.net/...",
        "http://en.wikipedia.org/wiki/..."
    ]
}
```

### Example

`curl http://ocremix.org/api/v1/systems/snes`

```json
{
    "id": "snes",
    "name": "SNES",
    "year": 1990,
    "publisher": {
        "id": 2,
        "name": "Nintendo"
    },
    "references": [
        "http://www.gamefaqs.com/console/snes/",
        "http://www.uvlist.net/platforms/detail/6-SNES",
        "http://en.wikipedia.org/wiki/Super_Nintendo_Entertainment_System"
    ]
}
```

## Composers by System

### GET /systems/xxxx/composers

```json
{
    "id": "xxxx",
    "name": "System",
    "composers": [
        {
            "id": 0000,
            "name": "Composer"
        },
        {
            "id": 0000,
            "name": "..."
        }
    ]
}
```

### Example

`curl http://ocremix.org/api/v1/systems/snes/composers`

```json
{
    "id": "snes",
    "name": "SNES",
    "composers": [
        {
            "id": 2,
            "name": "Koji Kondo"
        },
        {
            "id": 3,
            "name": "Nobuo Uematsu"
        },
        {
            "id": 0000,
            "name": "..."
        }
    ]
}
```

## Games by System

### GET /systems/xxxx/games

```json
{
    "id": "xxxx",
    "name": "System",
    "games": [
        {
            "id": 0000,
            "name": "Video Game"
        },
        {
            "id": 0000,
            "name": "..."
        }
    ]
}
```

### Example

`curl http://ocremix.org/api/v1/systems/snes/games`

```json
{
    "id": "snes",
    "name": "SNES",
    "games": [
        {
            "id": 6,
            "name": "Final Fantasy VI"
        },
        {
            "id": 16,
            "name": "Chrono Trigger"
        },
        {
            "id": 0000,
            "name": "..."
        }
    ]
}
```

## Albums by System

### GET /systems/xxxx/albums

```json
{
    "id": "xxxx",
    "name": "System",
    "albums": [
        {
            "id": 00,
            "name": "remix album"
        },
        {
            "id": 00,
            "name": "..."
        }
    ]
}
```

### Example

`curl http://ocremix.org/api/v1/systems/snes/albums`

```json
{
    "id": "snes",
    "name": "SNES",
    "albums": [
        {
            "id": 46,
            "name": "Final Fantasy VI: Balance and Ruin"
        },
        {
            "id": 00,
            "name": "..."
        }
    ]
}
```

## Remixes by System

### GET /systems/xxxx/remixes

```json
{
    "id": "xxxx",
    "name": "System",
    "remixes": [
        {
            "id": "OCR00000",
            "title": "mix title"
        },
        {
            "id": "OCR00000",
            "title": "..."
        }
    ]
}
```

### Example

`curl http://ocremix.org/api/v1/systems/snes/remixes`

```json
{
    "id": "snes",
    "name": "SNES",
    "remixes": [
        {
            "id": "OCR02885",
            "title": "Go-Go Gadget Gonkulator"
        },
        {
            "id": "OCR02854",
            "title": "La Montaña de los Caballos Jóvenes"
        },
        {
            "id": "OCR02700",
            "title": "Terra's Resolve"
        },
        {
            "id": "OCR00000",
            "title": "..."
        }
    ]
}
```

## Organizations

### GET /orgs/000

```json
{
    "id": 000,
    "name": "Org",
    "name_jp": "...",
    "references": [
        "http://www.gamefaqs.com/...",
        "http://www.uvlist.net/...",
        "http://en.wikipedia.org/wiki/..."
    ]
}
```

### Example

`curl http://ocremix.org/api/v1/orgs/2`

```json
{
    "id": 2,
    "name": "Nintendo",
    "references": [
        "http://www.gamefaqs.com/features/company/1143.html",
        "http://www.nintendo.com/",
        "http://www.mobygames.com/company/nintendo-co-ltd",
        "http://twitter.com/NintendoAmerica",
        "http://www.youtube.com/user/Nintendo"
    ]
}
```

## Composers by Organization

### GET /orgs/000/artists

```json
{
    "id": 000,
    "name": "Org",
    "composers": [
        {
            "id": 0000,
            "name": "Composer"
        },
        {
            "id": 0000,
            "name": "..."
        }
    ]
}
```

### Example

`curl http://ocremix.org/api/v1/orgs/2/artists`

```json
{
    "id": 2,
    "name": "Nintendo",
    "composers": [
        {
            "id": 2,
            "name": "Koji Kondo"
        },
        {
            "id": 3,
            "name": "Nobuo Uematsu"
        },
        {
            "id": 0000,
            "name": "..."
        }
    ]
}
```

## Games by Organization

### GET /orgs/000/games

```json
{
    "id": 000,
    "name": "Org",
    "games": [
        {
            "id": 0000,
            "name": "Video Game"
        },
        {
            "id": 0000,
            "name": "..."
        }
    ]
}
```

### Example

`curl http://ocremix.org/api/v1/orgs/2/games`

```json
{
    "id": 2,
    "name": "Nintendo",
    "games": [
        {
            "id": 6,
            "name": "Final Fantasy VI"
        },
        {
            "id": 16,
            "name": "Chrono Trigger"
        },
        {
            "id": 0000,
            "name": "..."
        }
    ]
}
```

## Systems by Organization

### GET /orgs/000/systems

```json
{
    "id": 000,
    "name": "Org",
    "systems": [
        {
            "id": "xxxx",
            "name": "System"
        },
        {
            "id": "xxxx",
            "name": "..."
        }
    ]
}
```

### Example

`curl http://ocremix.org/api/v1/orgs/2/systems`

```json
{
    "id": 2,
    "name": "Nintendo",
    "systems": [
        {
            "id": "snes",
            "name": "SNES"
        },
        {
            "id": "xxxx",
            "name": "..."
        }
    ]
}
```

## Albums by Organization

### GET /orgs/000/albums

```json
{
    "id": 000,
    "name": "Org",
    "albums": [
        {
            "id": 00,
            "name": "remix album"
        },
        {
            "id": 00,
            "name": "..."
        }
    ]
}
```

### Example

`curl http://ocremix.org/api/v1/orgs/2/albums`

```json
{
    "id": 2,
    "name": "Nintendo",
    "albums": [
        {
            "id": 46,
            "name": "Final Fantasy VI: Balance and Ruin"
        },
        {
            "id": 00,
            "name": "..."
        }
    ]
}
```

## Remixes by Organization

### GET /orgs/000/remixes

```json
{
    "id": 000,
    "name": "Org",
    "remixes": [
        {
            "id": "OCR00000",
            "title": "mix title"
        },
        {
            "id": "OCR00000",
            "title": "..."
        }
    ]
}
```

### Example

`curl http://ocremix.org/api/v1/orgs/2/remixes`

```json
{
    "id": 2,
    "name": "Nintendo",
    "remixes": [
        {
            "id": "OCR02885",
            "title": "Go-Go Gadget Gonkulator"
        },
        {
            "id": "OCR02854",
            "title": "La Montaña de los Caballos Jóvenes"
        },
        {
            "id": "OCR02700",
            "title": "Terra's Resolve"
        },
        {
            "id": "OCR00000",
            "title": "..."
        }
    ]
}
```
