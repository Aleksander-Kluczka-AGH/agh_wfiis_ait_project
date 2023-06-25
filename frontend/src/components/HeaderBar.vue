<script setup>
    import SearchBar from "./SearchBar.vue";
</script>

<template>
    <div>
        <h1>Lichess Statistics Viewer</h1>
        <h3>(by Aleksander Kluczka)</h3>
    </div>
    <!-- <nav></nav> -->
    <div>
        <SearchBar placeholder="Enter username here..."></SearchBar>
    </div>
</template>

<style scoped>
    h1 {
        padding: 0;
        margin: 0.5rem;
        margin-bottom: 0;
        color: var(--vt-c-green);
        text-align: center;
    }

    h3 {
        margin-top: 0;
        font-weight: normal;
        text-align: center;
    }

    /* nav {
        padding: 0.5rem;
        width: 50%;
        min-width: fit-content;
        text-align: center;

        border-style: solid;
        border-radius: 6px;
        border-width: 1px;
        border-color: var(--color-border);
    }

    nav a {
        display: inline-block;
        padding: 0 1rem;
        border-left: 1px solid var(--color-border);
    }

    nav a:first-of-type {
        border: 0;
    } */
</style>
