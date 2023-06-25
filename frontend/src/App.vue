<script setup>
    import HeaderBar from "./components/HeaderBar.vue";
    import BaseButton from "./components/BaseButton.vue";
    import { LichessUserStore } from "./stores/LichessUserStore.js";

    const lichessUserStore = LichessUserStore();
</script>

<template>
    <header>
        <HeaderBar />
    </header>

    <main>
        <BaseButton>Button</BaseButton>
        {{ lichessUserStore.data.basic.name }}
    </main>
</template>

<style scoped>
    header {
        display: flex;
        flex-direction: column;
        place-items: center;

        padding: 1rem;
        margin-bottom: 2rem;

        border-style: solid;
        border-radius: 6px;
        border-width: 1px;
        border-color: var(--color-border);
    }

    main {
        display: flex;
        flex-direction: column;
        place-items: center;

        padding: 1rem;
        margin-top: 2rem;

        border-style: solid;
        border-radius: 6px;
        border-width: 1px;
        border-color: var(--color-border);
    }
</style>
