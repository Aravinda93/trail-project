<template>
  <div class="container mx-auto px-4 py-8">
    <!-- Chart Section -->
    <div class="bg-white dark:bg-gray-800 rounded-lg shadow-lg p-6">
      <h2 class="text-2xl font-bold mb-4 text-gray-800 dark:text-gray-100">
        Chart Data
      </h2>
      <div class="relative">
        <Bar id="my-chart-id" :options="chartOptions" :data="chartData" />
      </div>
    </div>

    <!-- Export Button -->
    <div class="flex justify-end mt-6">
      <button
        @click="exportChartAsPDF"
        class="px-6 py-3 bg-blue-500 dark:bg-blue-600 text-white rounded-lg shadow hover:bg-blue-600 dark:hover:bg-blue-700 transition-colors duration-300"
      >
        Export as PDF
      </button>
    </div>
  </div>
</template>
  
<script setup>
import { ref, onMounted } from "vue";
import { Bar } from "vue-chartjs";
import {
  Chart as ChartJS,
  Title,
  Tooltip,
  Legend,
  BarElement,
  CategoryScale,
  LinearScale,
} from "chart.js";
import html2canvas from "html2canvas";
import jsPDF from "jspdf";

// Register the components you need from Chart.js
ChartJS.register(
  Title,
  Tooltip,
  Legend,
  BarElement,
  CategoryScale,
  LinearScale
);

const props = defineProps({
  chartInfo: {
    type: Array,
    required: true, // Ensure this is set to true if you are passing the data
  },
});

// Chart data and options
const chartData = ref({
  labels: [], //X-axis labels
  datasets: [
    {
      label: "Count of Geological Classes",
      backgroundColor: "#42A5F5",
      data: [], // Y-axis data
    },
  ],
});

const chartOptions = ref({
  responsive: true,
  plugins: {
    legend: {
      position: "top",
    },
    tooltip: {
      callbacks: {
        label: function (tooltipItem) {
          return tooltipItem.label + ": " + tooltipItem.raw;
        },
      },
    },
  },
});

onBeforeMount(() => {
  updateChart(props.chartInfo);
});

function updateChart(data) {
  // Aggregate data by section name
  const sectionCounts = data.reduce((acc, item) => {
    const name = item.name;
    if (!acc[name]) {
      acc[name] = 0;
    }
    acc[name] += item.geologicalClasses.length;
    return acc;
  }, {});

  // Convert aggregated data to chart format
  chartData.value.labels.push(...Object.keys(sectionCounts));
  chartData.value.datasets[0].data = Object.values(sectionCounts);
}

// Function to export the chart as a PDF
async function exportChartAsPDF() {
  const chartElement = document.getElementById("my-chart-id");
  const canvas = await html2canvas(chartElement); // Capture chart as canvas
  const imgData = canvas.toDataURL("image/png"); // Convert canvas to image

  const pdf = new jsPDF();
  const imgWidth = 190; // Adjust to fit PDF size
  const imgHeight = (canvas.height * imgWidth) / canvas.width; // Maintain aspect ratio

  pdf.addImage(imgData, "PNG", 10, 10, imgWidth, imgHeight); // Add image to PDF
  pdf.save("chart.pdf"); // Save PDF
}
</script>