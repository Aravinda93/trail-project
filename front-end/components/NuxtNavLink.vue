<template>
  <NuxtLink
    :to="to"
    :class="[
      'block w-full px-3 py-2 rounded-md text-sm font-medium text-blue-900 dark:text-blue-100 hover:bg-gradient-to-r hover:from-blue-200 hover:to-blue-400 dark:hover:from-blue-800 dark:hover:to-blue-600 transition-colors duration-150 ease-in-out',
      {
        'underline underline-offset-4 decoration-2 lg:underline-offset-8 lg:decoration-4 !text-secondary hover:bg-transparent':
          isActive,
      },
      mdScreen ? 'hidden md:block' : '',
    ]"
  >
    {{ label }}
  </NuxtLink>
</template>

<script setup>
import { useRoute } from "vue-router";

const route = useRoute();

const props = defineProps({
  to: {
    type: String, // path for the NuxtLink
    required: true,
  },
  label: {
    type: String, // label for the NuxtLink
    required: true,
  },
  mdScreen: {
    type: Boolean, // To show/hide the NuxtLink in small screens
    required: true,
  },
  routeStart: {
    type: Boolean, // If matching NuxtLink route needs to highlighted by checking route.path.startsWith
    required: false,
    default: true,
  },
});

// To check and highlight the active NuxtLink
const isActive = computed(() => {
  return (
    route.path === props.to ||
    (props.routeStart ? route.path.startsWith(`${props.to}/`) : false)
  );
});
</script>