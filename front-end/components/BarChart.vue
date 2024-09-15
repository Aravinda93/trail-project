<template>
  <Bar id="my-chart-id" :options="chartOptions" :data="chartData" />
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
</script>