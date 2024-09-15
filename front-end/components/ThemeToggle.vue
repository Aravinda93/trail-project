<template>
  <button
    aria-label="Toggle Color Mode"
    @click="changeColor"
    class="p-2 rounded-full transition duration-300 ease-in-out transform hover:scale-105 hover:ring-2 ring-gray-400 dark:ring-yellow-500 bg-transparent text-blue-900 dark:text-blue-100"
  >
    <ColorScheme>
      <Icon
        v-if="colorMode.value === 'light'"
        icon="heroicons:moon-16-solid"
        width="1.5em"
        height="1.5em"
        class="text-gray-400 transition duration-300 ease-in-out transform hover:rotate-45 dark:hover:scale-110"
      />

      <Icon
        v-if="colorMode.value === 'dark'"
        icon="heroicons:sun-16-solid"
        width="1.5em"
        height="1.5em"
        class="text-yellow-500 transition duration-300 ease-in-out transform hover:rotate-45 hover:scale-110"
      />
    </ColorScheme>
  </button>
</template>

<script setup>
import { Icon } from "@iconify/vue";

const colorMode = useColorMode();
const changeColor = () => {
  colorMode.preference = colorMode.value === "light" ? "dark" : "light";
};
</script>