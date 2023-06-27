<script setup>
    import { LichessUserStore } from "../stores/LichessUserStore.js";

    const lichessUserStore = LichessUserStore();
</script>

<template>
    <div class="table_wrapper">
        <table>
            <tr>
                <th>All</th>
                <td>{{ lichessUserStore.data.stats.all }}</td>
            </tr>
            <tr>
                <th>Rated</th>
                <td>{{ lichessUserStore.data.stats.rated }}</td>
            </tr>
            <tr>
                <th>Unrated</th>
                <td>{{ lichessUserStore.data.stats.unrated }}</td>
            </tr>
            <tr>
                <th>Against Computer</th>
                <td>{{ lichessUserStore.data.stats.ai }}</td>
            </tr>
            <tr>
                <th>Against Human</th>
                <td>{{ lichessUserStore.data.stats.human }}</td>
            </tr>
            <tr>
                <th>Draws</th>
                <td>{{ lichessUserStore.data.stats.drawn }}</td>
            </tr>
            <tr>
                <th>Losses</th>
                <td>{{ lichessUserStore.data.stats.lost }}</td>
            </tr>
            <tr>
                <th>Wins</th>
                <td>{{ lichessUserStore.data.stats.won }}</td>
            </tr>
            <tr>
                <th>Imported</th>
                <td>{{ lichessUserStore.data.stats.imported }}</td>
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
        width: 40%;
        padding: 2px 15px;
    }

    td {
        text-align: center;
        padding: 2px 15px;

        border-left-style: solid;
        border-color: var(--color-border);
        border-width: 1px;
    }
</style>
