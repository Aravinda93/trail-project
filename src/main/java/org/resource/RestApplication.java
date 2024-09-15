package org.resource;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.jboss.logging.Logger;
import org.task1.entity.FileResult;
import org.task1.entity.Job;
import org.task1.entity.Section;
import org.task1.repository.SectionRepository;
import org.task1.service.FileProcessingService;

import java.util.List;

@Path("/api")
public class RestApplication {
    private static final Logger LOG = Logger.getLogger(RestApplication.class.getName());
    @Inject
    FileProcessingService fileProcessingService;
    @Inject
    SectionRepository sectionRepository;

    @POST
    @Path("write")
    @Consumes({MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON, MediaType.MULTIPART_FORM_DATA})
    @APIResponse(responseCode = "200")
    @Transactional
    public Response upload(final String inputStream, @QueryParam("fileType") String fileType, @QueryParam("fileName") String fileName) {
        LOG.info("Parsing the CSV files");
        final Job job = new Job();
        job.status = "PENDING";
        job.persist();

        fileProcessingService.processFile(inputStream, job.id, fileName);
        return Response.ok("File processing started").build();
    }

    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(responseCode = "200")
    public Response fetchAll() {
        LOG.info("Fetching data from Database");
        final List<Section> sections = sectionRepository.findAll().stream().toList();
        return Response.ok(sections).build();
    }

    @POST
    @Path("/register-job")
    @Transactional
    public Response registerJob(final Job job) {
        job.status = "PENDING";
        job.persist();
        return Response.ok(job.id).build();
    }

    @GET
    @Path("/{jobId}")
    public Job getJob(@PathParam("jobId") Long jobId) {
        return Job.findById(jobId);
    }

    @GET
    @Path("/search")
    public List<FileResult> searchResults(@QueryParam("name") String name, @QueryParam("code") String code) {
        return FileResult.list("name = ?1 OR code = ?2", name, code);
    }
}
