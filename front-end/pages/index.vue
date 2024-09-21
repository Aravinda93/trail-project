<template>
  <!-- Upload File Section -->
  <div class="flex flex-col items-center justify-center p-6 space-y-4">
    <label
      class="w-72 flex flex-col items-center px-5 py-7 bg-gray-300 dark:bg-gray-600 text-gray-800 dark:text-gray-100 rounded-lg shadow-lg border border-transparent hover:border-blue-500 transition duration-300 cursor-pointer"
    >
      <Icon
        icon="bi:cloud-arrow-up"
        class="w-12 h-12 text-blue-500 dark:text-blue-400 mx-auto mb-3"
      />

      <span class="mt-2 text-lg font-medium"> Select a file </span>

      <input
        type="file"
        class="hidden"
        accept=".csv, .xlsx"
        @change="handleFileChange"
      />
    </label>

    <p class="text-sm text-gray-500 dark:text-gray-400">
      Supports CSV & XLSX formats
    </p>
  </div>

  <!-- Table section to display data -->
  <div class="container mx-auto px-4 py-8">
    <h2 class="text-3xl font-bold text-gray-800 dark:text-gray-100 mb-6">
      Sections and Geological Classes
    </h2>

    <div
      class="overflow-hidden shadow-lg rounded-lg border border-gray-300 dark:border-gray-700"
    >
      <table
        class="min-w-full table-auto bg-white dark:bg-gray-800 text-gray-900 dark:text-gray-100"
      >
        <thead class="bg-gray-100 dark:bg-gray-700">
          <tr>
            <th
              class="px-6 py-3 text-left text-sm font-bold text-gray-700 dark:text-gray-200 uppercase tracking-wider"
            >
              Section Name
            </th>
            <th
              class="px-6 py-3 text-left text-sm font-bold text-gray-700 dark:text-gray-200 uppercase tracking-wider"
            >
              Geological Classes
            </th>
          </tr>
        </thead>
      </table>

      <!-- Scrollable table body with limited height -->
      <div class="max-h-64 overflow-y-auto">
        <table class="min-w-full bg-white dark:bg-gray-800">
          <tbody class="divide-y divide-gray-200 dark:divide-gray-700">
            <tr
              v-for="section in sections"
              :key="section.id"
              class="hover:bg-gray-50 dark:hover:bg-gray-700 transition-colors"
            >
              <td class="px-6 py-4 text-base text-gray-800 dark:text-gray-100">
                {{ section.name }}
              </td>
              <td class="px-6 py-4 text-base text-gray-800 dark:text-gray-100">
                <ul>
                  <li
                    v-for="geoClass in section.geologicalClasses"
                    :key="geoClass.id"
                  >
                    {{ geoClass.name }} - {{ geoClass.code }}
                  </li>
                </ul>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>

    <div v-if="errors.length > 0" class="mt-4 text-red-600 dark:text-red-400">
      <p v-for="(error, index) in errors" :key="index">{{ error }}</p>
    </div>
  </div>

  <!-- Bar Chart section to display data -->
  <BarChart :chartInfo="sections" v-if="sections.length > 0" />
</template>

<script setup>
import { Icon } from "@iconify/vue";
import { ref, onBeforeMount } from "vue";
import { fetchAll } from "~/composables/fetchAll";

const sections = ref([]);
const errors = ref("");

onBeforeMount(async () => {
  try {
    const data = await fetchAll();
    sections.value = data;
  } catch (error) {
    errors.value = error.message;
  }
});

const handleFileChange = (event) => {
  const file = event.target.files[0];
  if (!file) return;

  // Check file type and process it
  const fileType = file.name.split(".").pop().toLowerCase();
  if (fileType === "csv" || fileType === "xlsx") {
    const reader = new FileReader();
    reader.onload = () => {
      const fileContent = reader.result;
      // Pass fileContent to composables
      useFileHandler(fileType, fileContent, file.name);
    };
    reader.readAsText(file);
  } else {
    alert("Please upload a valid CSV or XLSX file");
  }
};
</script>