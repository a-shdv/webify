ALTER TABLE post
    RENAME COLUMN description TO long_description;

ALTER TABLE post
    ALTER COLUMN long_description TYPE text;


ALTER TABLE post
    ADD COLUMN short_description VARCHAR(250) NOT NULL;

