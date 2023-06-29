import { ref, computed } from "vue";
import { defineStore } from "pinia";

export const LichessUserStore = defineStore("lichessUser", () => {
    const basicInfo = {
        id: ref(""),
        name: ref(""),
        link: ref(""),
        created: ref(""),
        seen: ref(""),
        time_played: ref(""),
    };
    const statsInfo = {
        all: ref(0),
        rated: ref(0),
        unrated: computed(() => statsInfo.all.value - statsInfo.rated.value),
        ai: ref(0),
        human: computed(() => statsInfo.all.value - statsInfo.ai.value),
        drawn: ref(0),
        lost: ref(0),
        won: ref(0),
        imported: ref(0),
        puzzle_count: ref(0),
        puzzle_rating: ref(0),
        rapid_count: ref(0),
        rapid_rating: ref(0),
        blitz_count: ref(0),
        blitz_rating: ref(0),
        bullet_count: ref(0),
        bullet_rating: ref(0),
        classical_count: ref(0),
        classical_rating: ref(0),
        correspondence_count: ref(0),
        correspondence_rating: ref(0),
    };

    async function requestUserInfo(username) {
        const fetchSettings = {
            method: "GET",
            mode: "cors",
            headers: {
                // "Access-Control-Allow-Origin": "http://localhost:8080/chess/", // DEBUG
                "Access-Control-Allow-Origin": "https://zti-backend-0ocf.onrender.com/chess",
                "Allow-Origin": "https://zti-backend-0ocf.onrender.com/chess",
            },
        };

        try {
            // await fetch(`http://localhost:8080/chess/player/${username}`, fetchSettings) // DEBUG
            await fetch(
                `https://zti-backend-0ocf.onrender.com/chess/player/${username}`,
                fetchSettings
            )
                .then((response) => response.json())
                .then((data) => {
                    basicInfo.id.value = data.id;
                    basicInfo.name.value = data.name;
                    basicInfo.link.value = data.link;
                    basicInfo.created.value = data.created;
                    basicInfo.seen.value = data.seen;
                    basicInfo.time_played.value = data.time_played;
                });
        } catch (error) {
            console.log("Error: ", error);
        }

        try {
            // await fetch(`http://localhost:8080/chess/stats/${username}`, fetchSettings) // DEBUG
            await fetch(
                `https://zti-backend-0ocf.onrender.com/chess/stats/${username}`,
                fetchSettings
            )
                .then((response) => response.json())
                .then((data) => {
                    statsInfo.all.value = data.all;
                    statsInfo.rated.value = data.rated;
                    statsInfo.ai.value = data.ai;
                    statsInfo.drawn.value = data.drawn;
                    statsInfo.lost.value = data.lost;
                    statsInfo.won.value = data.won;
                    statsInfo.imported.value = data.imported;

                    statsInfo.puzzle_count.value = data.puzzle_count;
                    statsInfo.puzzle_rating.value = data.puzzle_rating;
                    statsInfo.rapid_count.value = data.rapid_count;
                    statsInfo.rapid_rating.value = data.rapid_rating;
                    statsInfo.blitz_count.value = data.blitz_count;
                    statsInfo.blitz_rating.value = data.blitz_rating;
                    statsInfo.bullet_count.value = data.bullet_count;
                    statsInfo.bullet_rating.value = data.bullet_rating;
                    statsInfo.classical_count.value = data.classical_count;
                    statsInfo.classical_rating.value = data.classical_rating;
                    statsInfo.correspondence_count.value = data.correspondence_count;
                    statsInfo.correspondence_rating.value = data.correspondence_rating;
                });
        } catch (error) {
            console.log("Error: ", error);
        }
    }

    const data = ref({ basic: basicInfo, stats: statsInfo });
    const func = { requestUserInfo };

    return { data, func };
});
