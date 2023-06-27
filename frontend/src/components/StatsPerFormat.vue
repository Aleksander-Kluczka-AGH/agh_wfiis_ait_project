<script setup>
    import { LichessUserStore } from "../stores/LichessUserStore.js";

    const lichessUserStore = LichessUserStore();
</script>

<template>
    <div class="table_wrapper">
        <table>
            <tr>
                <th>Format</th>
                <th>Times played</th>
                <th>Rating</th>
            </tr>
            <tr>
                <td>Puzzles</td>
                <td>{{ lichessUserStore.data.stats.puzzle_count }}</td>
                <td>{{ lichessUserStore.data.stats.puzzle_rating }}</td>
            </tr>
            <tr>
                <td>Rapid</td>
                <td>{{ lichessUserStore.data.stats.rapid_count }}</td>
                <td>{{ lichessUserStore.data.stats.rapid_rating }}</td>
            </tr>
            <tr>
                <td>Blitz</td>
                <td>{{ lichessUserStore.data.stats.blitz_count }}</td>
                <td>{{ lichessUserStore.data.stats.blitz_rating }}</td>
            </tr>
            <tr>
                <td>Bullet</td>
                <td>{{ lichessUserStore.data.stats.bullet_count }}</td>
                <td>{{ lichessUserStore.data.stats.bullet_rating }}</td>
            </tr>
            <tr>
                <td>Classical</td>
                <td>{{ lichessUserStore.data.stats.classical_count }}</td>
                <td>{{ lichessUserStore.data.stats.classical_rating }}</td>
            </tr>
            <tr>
                <td>Correspondence</td>
                <td>{{ lichessUserStore.data.stats.correspondence_count }}</td>
                <td>{{ lichessUserStore.data.stats.correspondence_rating }}</td>
            </tr>
        </table>
    </div>
</template>

<style scoped>
    .table_wrapper {
        margin: 4px;

        min-width: fit-content;
        width: 70%;

        display: table;
        border-radius: 6px;
        border-style: solid;
        border-width: 1px;
        border-color: var(--color-border);
    }

    table {
        width: 100%;
        border-collapse: collapse;
    }

    tr {
        border-bottom-style: solid;
        border-width: 1px;
        border-color: var(--color-border);
    }

    tr:last-of-type {
        border-bottom-style: none;
    }

    th {
        border-left-style: solid;
        border-width: 1px;
        border-color: var(--color-border);
        padding: 2px 15px;
    }

    th:first-of-type {
        width: 40%;
        border-left-style: none;
    }

    td {
        text-align: center;
        padding: 2px 15px;

        border-left-style: solid;
        border-color: var(--color-border);
        border-width: 1px;
    }
</style>
