const runtimeConfig = useRuntimeConfig();

export const useFileHandler = async (fileType, fileContent, fileName) => {
  const contentType = fileType === "csv" ? "text/plain" : "application/json";

  try {
    const response = await fetch(
      `${runtimeConfig.public.apiBase}api/write?fileType=` +
        fileType +
        `&fileName=` +
        fileName,
      {
        method: "POST",
        headers: {
          "Content-Type": contentType,
        },
        body: fileContent,
      }
    );

    const result = await response.text(); // Get the response from Quarkus
    console.log("Response from Quarkus:", result);
  } catch (error) {
    console.error("Error uploading file to Quarkus:", error);
  }
};
