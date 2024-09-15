package org.tasks;


import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.MediaType;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.task1.entity.Job;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
public class Task1Test {

    @Test
    public void testRegisterJob() {
        Job job = new Job();
        job.status = "COMPLETED";

        given()
                .contentType("application/json")
                .body(job)
                .when()
                .post("/api/register-job")
                .then()
                .statusCode(200)
                .body(is(notNullValue())); // Check that the job ID is returned
    }

    @Test
    public void testProcessCsvFile() throws Exception {
        final InputStream csvFile = getClass().getClassLoader().getResourceAsStream("Task1.csv");
        final String csvContent = IOUtils.toString(csvFile, StandardCharsets.UTF_8);
        assertNotNull(csvContent, "CSV file should not be null");

        // Use RestAssured to send the file to the Quarkus endpoint
        given()
                .contentType("text/plain")
                .queryParam("fileType", "csv")
                .queryParam("fileName", "sample.csv")
                .body(csvContent)
                .contentType(String.valueOf(MediaType.TEXT_PLAIN_TYPE))
                .when()
                .post("/api/write")
                .then()
                .statusCode(200)
                .body(is("File processing started"));
    }

    @Test
    public void testFetchAllSections() {
        given()
                .when()
                .get("/api/read")
                .then()
                .statusCode(200)
                .body(is(notNullValue())); // Check that some data is returned
    }
}