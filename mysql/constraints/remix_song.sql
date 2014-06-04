ALTER TABLE remix_song
    ADD CONSTRAINT remix_song_remix_id_fkey
    FOREIGN KEY (remix_id)
    REFERENCES remixes(id);

ALTER TABLE remix_song
    ADD CONSTRAINT remix_song_song_id_fkey
    FOREIGN KEY (song_id)
    REFERENCES songs(id);

