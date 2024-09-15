<template>
  <div
    class="flex flex-col min-h-screen bg-white dark:bg-gray-900 text-gray-900 dark:text-gray-100"
    @click="onPageClick"
    :class="`${colorMode.preference === 'dark' ? 'dark' : ''}`"
  >
    <nav class="sticky top-0 z-50 shadow-sm bg-white dark:bg-gray-900">
      <div class="w-full flex items-center justify-between p-3">
        <!-- Logo Image -->
        <NuxtLink to="/" class="flex items-center rtl:space-x-reverse">
          <span
            class="self-center text-2xl font-semibold whitespace-nowrap dark:text-white mt-2"
          >
            Front-End
          </span>
        </NuxtLink>

        <div class="md:w-auto">
          <ul
            class="flex space-x-2 md:p-0 rounded-lg md:flex-row md:space-x-7 md:mt-0 md:border-0 bg-white dark:bg-gray-900 dark:border-gray-700"
          >
            <!-- Home -->
            <li>
              <NuxtNavLink :to="'/'" label="Home" :md-screen="true" />
            </li>

            <li>
              <ThemeToggle />
            </li>

            <!-- Button to show navbar menu for small screens -->
            <li class="md:hidden">
              <div @click="toggleNav" class="cursor-pointer">
                <Icon
                  v-if="showMenu"
                  icon="akar-icons:cross"
                  width="35"
                  height="35"
                />
                <Icon v-else icon="mdi:hamburger-menu" width="35" height="35" />
              </div>
            </li>
          </ul>
        </div>
      </div>

      <!-- Mobile Menu -->
      <ul
        :class="[
          showMenu ? 'flex' : 'hidden',
          'md:hidden flex-col py-6 justify-center items-center space-y-6 dark:border dark:border-slate-700 dark:bg-slate-800',
        ]"
      >
        <!-- Home -->
        <li>
          <NuxtNavLink :to="'/'" label="Home" :md-screen="false" />
        </li>
      </ul>
    </nav>

    <!-- Show the loading indicator during initial load and when navigating to different pages -->
    <NuxtLoadingIndicator :height="4" :duration="3000" />

    <!-- Page Content -->
    <div class="w-full flex flex-col flex-grow">
      <main>
        <slot>
          <NuxtPage />
        </slot>
      </main>
    </div>

    <footer
      class="border-t lg:border-gray-900/10 dark:border-gray-50/[0.2] dark:bg-gray-900 flex items-center justify-center"
    >
      <section class="px-4 lg:px-8">
        <div
          class="w-full py-4 text-center md:text-left flex flex-col space-y-4 md:space-y-0 md:space-x-20 items-center md:flex-row"
        >
          <div class="text-xs text-gray-900 dark:text-gray-400">
            <div>Quarkus MYSQL Project</div>
            <div
              class="flex flex-col md:flex-row space-x-2 items-center md:float-right"
            />
          </div>
        </div>
      </section>
    </footer>
  </div>
</template>
    
  <script setup>
import { Icon } from "@iconify/vue";

//Common content to be displayed on all page
useHead({
  meta: [
    {
      charset: "UTF-8",
    },
    {
      name: "viewport",
      content: "width=device-width, initial-scale=1, shrink-to-fit=no",
    },
    { name: "author", content: "benelog GmbH & Co. KG" },
  ],
});

const colorMode = useColorMode(); //To toggle the theme using the nuxtjs colormode
const showMenu = useState("showMenu", () => false);
const isMenuOpen = ref(false);
const isMobileMenuOpen = ref(false);
const DropDownMobileElement = ref(null);

const onPageClick = (event) => {
  if (isMenuOpen.value) {
    isMenuOpen.value = false;
  }

  if (
    DropDownMobileElement.value?.contains &&
    !DropDownMobileElement.value.contains(event.target)
  ) {
    isMobileMenuOpen.value = false;
  }
};

function toggleNav() {
  showMenu.value = !showMenu.value;
}
</script>
    