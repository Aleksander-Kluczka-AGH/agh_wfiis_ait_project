<script setup>
    import { LichessUserStore } from "../stores/LichessUserStore.js";

    const lichessUserStore = LichessUserStore();
</script>

<template>
    <div class="table_wrapper">
        <table>
            <tr>
                <th>ID</th>
                <td>{{ lichessUserStore.data.basic.id }}</td>
            </tr>
            <tr>
                <th>Name</th>
                <td>{{ lichessUserStore.data.basic.name }}</td>
            </tr>
            <tr>
                <th>Created on</th>
                <td>{{ lichessUserStore.data.basic.created }}</td>
            </tr>
            <tr>
                <th>Last seen</th>
                <td>{{ lichessUserStore.data.basic.seen }}</td>
            </tr>
            <tr>
                <th>Time played</th>
                <td>{{ lichessUserStore.data.basic.time_played }}</td>
            </tr>
            <tr>
                <th>Account link</th>
                <td>
                    <a :href="lichessUserStore.data.basic.link" target="_blank">{{
                        lichessUserStore.data.basic.link
                    }}</a>
                </td>
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
