<script setup>
    import { ref } from "vue";
    import BaseButton from "./BaseButton.vue";
    import { LichessUserStore } from "../stores/LichessUserStore.js";

    const props = defineProps({
        placeholder: String,
    });

    const lichessUserStore = LichessUserStore();
    const inputContent = ref("");

    function handleInput(event) {
        inputContent.value = event.target.value;
    }
    function setName() {
        lichessUserStore.data.basic.name = inputContent.value;
        lichessUserStore.func.requestUserInfo(inputContent.value);
    }
</script>

<template>
    <input type="text" :placeholder="placeholder" @input="handleInput" />
    <BaseButton @click="setName">Search</BaseButton>
</template>

<style scoped>
    input {
        color: var(--color-text);
        font-size: inherit;

        margin: 4px;
        padding: 5px 15px;
        background-color: var(--color-background);

        border-style: solid;
        border-radius: 6px;
        border-width: 1px;
        border-color: var(--color-border);

        transition: var(--transition-time);
    }

    input:hover {
        outline: none;
        border-color: var(--color-border-hover);
    }

    input:focus {
        outline: none;
        border-color: var(--color-selected);
    }
</style>
