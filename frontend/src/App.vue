<script setup>
    import HeaderBar from "./components/HeaderBar.vue";
    import BasicInfo from "./components/BasicInfo.vue";
    import StatsSummary from "./components/StatsSummary.vue";
    import StatsPerFormat from "./components/StatsPerFormat.vue";
</script>

<template>
    <header>
        <HeaderBar />
    </header>

    <main>
        <BasicInfo />
        <StatsSummary />
        <StatsPerFormat />
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
