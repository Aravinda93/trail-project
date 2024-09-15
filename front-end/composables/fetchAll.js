const runtimeConfig = useRuntimeConfig();

export async function fetchAll() {
  try {
    const { data, error } = await useFetch(
      `${runtimeConfig.public.apiBase}api/read`,
      {
        method: "GET",
      }
    );

    if (error.value) {
      throw new Error(error.value);
    }

    return data.value;
  } catch (error) {
    throw new Error(`Unable to read the data: ${error.message}`);
  }
}
