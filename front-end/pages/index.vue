<template>
  <!-- Upload File Section -->
  <div class="flex flex-col items-center justify-center p-4">
    <label
      class="w-64 flex flex-col items-center px-4 py-6 dark:bg-white bg-gray-900 text-blue rounded-lg shadow-lg tracking-wide uppercase border border-blue cursor-pointer hover:bg-blue-500 hover:text-white"
    >
      <Icon
        icon="bi:cloud-arrow-up"
        color="#F26334"
        class="w-12 h-11 mx-auto mb-2"
      ></Icon>
      <span class="mt-2 text-base leading-normal dark:text-black text-white"
        >Select a file</span
      >
      <input
        type="file"
        class="hidden"
        accept=".csv, .xlsx"
        @change="handleFileChange"
      />
    </label>
  </div>

  <!-- Table section to display data -->
  <div class="container mx-auto px-4 py-8">
    <h2 class="text-2xl font-bold mb-4">Sections and Geological Classes</h2>

    <div class="overflow-x-auto">
      <table class="min-w-full bg-white border border-gray-200 text-black">
        <thead class="bg-gray-100">
          <tr>
            <th
              class="border border-gray-300 px-6 py-3 text-left text-xs leading-4 font-medium text-gray-700 uppercase tracking-wider"
            >
              Section Name
            </th>
            <th
              class="border border-gray-300 px-6 py-3 text-left text-xs leading-4 font-medium text-gray-700 uppercase tracking-wider"
            >
              Geological Classes
            </th>
          </tr>
        </thead>
        <tbody class="divide-y divide-gray-200">
          <tr v-for="section in sections" :key="section.id">
            <td class="border border-gray-300 px-6 py-4 whitespace-no-wrap">
              {{ section.name }}
            </td>
            <td class="border border-gray-300 px-6 py-4 whitespace-pre">
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

    <div v-if="errors.length > 0" class="mt-4 text-red-600">
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