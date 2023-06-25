<template>
    <button type="button">
        <slot>Button</slot>
    </button>
</template>

<style scoped>
    button {
        text-align: center;
        color: var(--color-text);
        font-size: inherit;

        margin: 4px;
        padding: 5px 15px;
        background-color: var(--color-background);

        border-style: solid;
        border-radius: 6px;
        border-width: 1px;
        border-color: var(--color-border);

        cursor: pointer;

        transition: var(--transition-time);
    }

    button:hover {
        border-color: var(--color-border-hover);
    }

    button:active {
        border-color: var(--color-selected);
        color: var(--color-selected);
    }
</style>
