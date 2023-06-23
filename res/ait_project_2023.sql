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
	"id" TEXT NOT NULL PRIMARY KEY,
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
	"id" SERIAL UNIQUE PRIMARY KEY,
	"format" TEXT NOT NULL,
	"player_id" TEXT NOT NULL,
	"date" TIMESTAMP NOT NULL,
	"points" INT NOT NULL,
);

CREATE TABLE "metadata" (
	"id" SERIAL NOT NULL UNIQUE PRIMARY KEY,
	"player_id" TEXT NOT NULL FOREIGN KEY REFERENCES "player"("id"),
	"player_cache_datetime" TIMESTAMP NOT NULL DEFAULT NOW(),
	"stats_cache_datetime" TIMESTAMP NOT NULL DEFAULT NOW(),
	"game_results_cache_datetime" TIMESTAMP NOT NULL DEFAULT NOW(),
	"are_stats_populated" BOOLEAN NOT NULL DEFAULT False,
	"are_game_results_populated" BOOLEAN NOT NULL DEFAULT False
);

ALTER TABLE "stats" ADD CONSTRAINT "stats_fk0" FOREIGN KEY ("id") REFERENCES "player"("id");

ALTER TABLE "game_result" ADD CONSTRAINT "game_result_fk0" FOREIGN KEY ("format") REFERENCES "game_format"("name");

ALTER TABLE "game_result" ADD CONSTRAINT "game_result_fk1" FOREIGN KEY ("player_id") REFERENCES "player"("id");

ALTER TABLE "metadata" ADD CONSTRAINT "metadata_fk0" FOREIGN KEY ("player_id") REFERENCES "player"("id");
