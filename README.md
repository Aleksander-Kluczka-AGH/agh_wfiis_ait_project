# Advanced internet techniques - Project

Project written for Applied Computer Science **Advanced internet techniques** course at [AGH UST](https://www.agh.edu.pl/en) [WFiIS](https://www.fis.agh.edu.pl/en/).

Full-stack application displaying statistics of a user from [Lichess](lichess.org).

- Database: PostgreSQL.
- Backend: [Java Spring, Spring Boot, Spring Data](https://spring.io/), [Chariot](https://github.com/tors42/chariot).
- Frontend: [Vue3](https://vuejs.org/), [Vite](https://vitejs.dev/), [Pinia](https://pinia.vuejs.org/).

Semester `8`, year `2023`.

## Run locally

**Backend** is setup as a maven project, packaged as `.war`. Build the artifact with the following commands:

```bash
cd backend/
./mvnw clean install
```

This should create a file `backend/target/lichess_stats-0.0.1-SNAPSHOT.war`. WARs don't ship entire execution environment, so a server is needed, i.e. TomCat.

**Frontend** can be run with `npm`, either by launching a development server:

```bash
cd frontend/
npm run dev
```

... or by building distribution package and launching a preview server:

```bash
cd frontend/
npm run build
npm run preview
```

## Run with docker

Docker is a more convenient option as it does not require an installation of tools like `maven` or `npm` on local machine. Docker environment is required, however.

Backend docker image uses `maven` to build `.war` file and then creates TomCat setup with the loaded artifact. To launch backend with docker, follow instructions below:

```bash
cd backend/
docker build -t lichess_stats:backend .
docker run --name lichess_stats_backend -it lichess_stats:backend
```

Similarly, build frontend docker image and launch it:

```bash
cd frontend/
docker build -t lichess_stats:frontend .
docker run --name lichess_stats_frontend -it lichess_stats:frontend
```

Frontend docker image uses nginx server to launch the application.

Optionally, above steps can be replaced by using `compose` command:

```bash
docker compose up
```

, which builds the images if necessary and launches both containers simultanously.

> NOTE: Unfortunately simultanous launch of both containers will not result in working application, because frontend is hardcoded to send requests to already deployed backend app (for grading at university), which (at the time of writing) has been shut down. This can be resolved by defining and reading environment variables and passing them to a container on launch.

> NOTE: Backend container will also refuse to work for the similar reason - this time regarding externally hosted PostgreSQL database. For safety reasons this database has been shut down. Of course, this issue can be resolved by using environment variables as well, but due to very limited time resources, some convenience features had to be skipped at the time of development.


## License

Sources are licensed under MIT, a free and open-source license. For details, please see [the license file](LICENSE.md).
