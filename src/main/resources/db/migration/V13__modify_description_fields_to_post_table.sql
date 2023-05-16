ALTER TABLE post
    DROP COLUMN short_description;

ALTER TABLE post
    RENAME COLUMN long_description TO description;