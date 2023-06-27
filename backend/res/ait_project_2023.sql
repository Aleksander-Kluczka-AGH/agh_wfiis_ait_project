CREATE TABLE "game_format" (
	"name" TEXT NOT NULL UNIQUE PRIMARY KEY
);

CREATE TABLE "player" (
	"id" TEXT NOT NULL UNIQUE PRIMARY KEY,
	"name" TEXT NOT NULL,
	"link" TEXT NOT NULL,
	"created" TIMESTAMP NOT NULL,
	"seen" TIMESTAMP NOT NULL,
	"time_played" TEXT NOT NULL
);

CREATE TABLE "stats" (
	"id" SERIAL NOT NULL UNIQUE PRIMARY KEY,
	"player_id" TEXT NOT NULL UNIQUE,
	"all" INT NOT NULL,
	"rated" INT NOT NULL,
	"ai" INT NOT NULL,
	"drawn" INT NOT NULL,
	"lost" INT NOT NULL,
	"won" INT NOT NULL,
	"imported" INT NOT NULL,
	"puzzle_count" INT NOT NULL,
	"puzzle_rating" INT,
	"rapid_count" INT NOT NULL,
	"rapid_rating" INT,
	"blitz_count" INT NOT NULL,
	"blitz_rating" INT,
	"bullet_count" INT NOT NULL,
	"bullet_rating" INT,
	"classical_count" INT NOT NULL,
	"classical_rating" INT,
	"correspondence_count" INT NOT NULL,
	"correspondence_rating" INT
);

CREATE TABLE "game_result" (
	"id" SERIAL NOT NULL UNIQUE PRIMARY KEY,
	"player_id" TEXT NOT NULL UNIQUE,
	"format" TEXT NOT NULL,
	"date" TIMESTAMP NOT NULL,
	"points" INT NOT NULL
);

CREATE TABLE "metadata" (
	"id" SERIAL NOT NULL UNIQUE PRIMARY KEY,
	"player_id" TEXT NOT NULL UNIQUE,
	"player_cache_datetime" TIMESTAMP NOT NULL DEFAULT NOW(),
	"stats_cache_datetime" TIMESTAMP DEFAULT NULL,
	"game_results_cache_datetime" TIMESTAMP DEFAULT NULL,
	"are_stats_populated" BOOLEAN NOT NULL DEFAULT False,
	"are_game_results_populated" BOOLEAN NOT NULL DEFAULT False
);

ALTER TABLE "stats" ADD CONSTRAINT "stats_fk0" FOREIGN KEY ("player_id") REFERENCES "player"("id");
ALTER TABLE "game_result" ADD CONSTRAINT "game_result_fk0" FOREIGN KEY ("format") REFERENCES "game_format"("name");
ALTER TABLE "game_result" ADD CONSTRAINT "game_result_fk1" FOREIGN KEY ("player_id") REFERENCES "player"("id");
ALTER TABLE "metadata" ADD CONSTRAINT "metadata_fk0" FOREIGN KEY ("player_id") REFERENCES "player"("id");

-- Check whether metadata cache timestamp is too old

CREATE OR REPLACE FUNCTION check_player_cache_outdated(arg_player_id TEXT)
RETURNS BOOLEAN AS $$
BEGIN
	RETURN NOT EXISTS (
		SELECT 1
			FROM metadata AS md
			WHERE md.player_id = arg_player_id
	) OR (
		SELECT (NOW() - md.player_cache_datetime) > INTERVAL '1 day'
			FROM metadata AS md
			WHERE md.player_id = arg_player_id
	);
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION check_stats_cache_outdated(arg_player_id TEXT)
RETURNS BOOLEAN AS $$
BEGIN
	RETURN NOT EXISTS (
		SELECT 1
			FROM metadata AS md
			WHERE md.player_id = arg_player_id
			AND md.stats_cache_datetime IS NOT NULL
	) OR (
		SELECT (NOW() - md.stats_cache_datetime) > INTERVAL '1 day'
			FROM metadata AS md
			WHERE md.player_id = arg_player_id
	);
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION check_game_results_cache_outdated(arg_player_id TEXT)
RETURNS BOOLEAN AS $$
BEGIN
	RETURN NOT EXISTS (
		SELECT 1
			FROM metadata AS md
			WHERE md.player_id = arg_player_id
			AND md.game_results_cache_datetime IS NOT NULL
	) OR (
		SELECT (NOW() - md.game_results_cache_datetime) > INTERVAL '1 day'
			FROM metadata AS md
			WHERE md.player_id = arg_player_id
	);
END;
$$ LANGUAGE plpgsql;

-- Automatically create metadata when a new player is added

CREATE OR REPLACE FUNCTION create_metadata()
RETURNS TRIGGER AS $$
BEGIN
	IF NEW.id IS NULL THEN
		RAISE EXCEPTION 'Player ID is null for some reason: %, %', NEW, NEW.name;
	END IF;
	INSERT INTO metadata (player_id) VALUES (CAST(NEW.id AS TEXT));
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_metadata_creation
AFTER INSERT ON "player"
FOR EACH ROW
EXECUTE FUNCTION create_metadata();

-- Automatically update metadata when any watched table is updated

CREATE OR REPLACE FUNCTION update_player_metadata()
RETURNS TRIGGER AS $$
BEGIN
    UPDATE metadata
		SET player_cache_datetime = NOW()
		WHERE metadata.player_id = NEW.id;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION update_stats_metadata()
RETURNS TRIGGER AS $$
BEGIN
    UPDATE metadata
		SET stats_cache_datetime = NOW(), are_stats_populated = True
		WHERE metadata.player_id = NEW.player_id;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION update_game_results_metadata()
RETURNS TRIGGER AS $$
BEGIN
    UPDATE metadata
		SET stats_game_results_datetime = NOW(), are_game_results_populated = True
		WHERE metadata.player_id = NEW.player_id;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_player_cache_update
AFTER INSERT OR UPDATE ON "player"
FOR EACH ROW
EXECUTE FUNCTION update_player_metadata();

CREATE TRIGGER trigger_stats_cache_update
AFTER INSERT OR UPDATE ON "stats"
FOR EACH ROW
EXECUTE FUNCTION update_stats_metadata();

CREATE TRIGGER trigger_game_results_cache_update
AFTER INSERT OR UPDATE ON "game_result"
FOR EACH ROW
EXECUTE FUNCTION update_game_results_metadata();

-- Helper setup functions

INSERT INTO game_format(name)
	VALUES ('chess960'),
		('atomic'),
		('racingkings'),
		('ultrabullet'),
		('blitz'),
		('kingofthehill'),
		('bullet'),
		('correspondence'),
		('horde'),
		('puzzle'),
		('classical'),
		('rapid'),
		('storm'),
		('racer'),
		('streak');

-- Helper cleanup functions

DROP TRIGGER IF EXISTS trigger_metadata_creation on player;
DROP TRIGGER IF EXISTS trigger_player_cache_update on player;
DROP TRIGGER IF EXISTS trigger_stats_cache_update on stats;
DROP TRIGGER IF EXISTS trigger_game_results_cache_update on game_result;

DROP FUNCTION IF EXISTS check_player_cache_outdated(text);
DROP FUNCTION IF EXISTS check_stats_cache_outdated(text);
DROP FUNCTION IF EXISTS check_game_results_cache_outdated(text);
DROP FUNCTION IF EXISTS create_metadata(text);
DROP FUNCTION IF EXISTS update_player_metadata(text);
DROP FUNCTION IF EXISTS update_stats_metadata(text);
DROP FUNCTION IF EXISTS update_game_results_metadata(text);

DROP TABLE IF EXISTS "game_format" CASCADE;
DROP TABLE IF EXISTS "player" CASCADE;
DROP TABLE IF EXISTS "stats" CASCADE;
DROP TABLE IF EXISTS "game_result" CASCADE;
DROP TABLE IF EXISTS "metadata" CASCADE;
