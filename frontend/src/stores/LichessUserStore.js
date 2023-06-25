import { ref } from "vue";
import { defineStore } from "pinia";

export const LichessUserStore = defineStore("lichessUser", () => {
    const basicInfo = {
        id: ref(""),
        name: ref("test123"),
        link: ref(""),
        created: ref(Date()),
        seen: ref(Date()),
        time_played: ref(""),
    };
    const statsInfo = {
        all: ref(0),
        rated: ref(0),
        ai: ref(0),
        drawm: ref(0),
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
                "Access-Control-Allow-Origin": "http://localhost:8080/chess/player/",
            },
        };

        try {
            await fetch(`http://localhost:8080/chess/player/${username}`, fetchSettings)
                .then((response) => response.json())
                .then((data) => {
                    // TODO: fill store with meaningful data
                    // TODO: retrieve the rest data from other urls
                    console.log(data.link);
                });
        } catch (error) {
            console.log("Error: ", error);
        }
    }

    const data = ref({ basic: basicInfo, stats: statsInfo });
    const func = { requestUserInfo };

    return { data, func };
});
