package org.task1.service;


import com.opencsv.CSVReader;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import org.task1.entity.GeologicalClass;
import org.task1.entity.Job;
import org.task1.entity.Section;
import org.task1.repository.SectionRepository;
import org.jboss.logging.Logger;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class FileProcessingService {

    private static final Logger LOG = Logger.getLogger(FileProcessingService.class);
    @Inject
    SectionRepository sectionRepository;

    @Transactional
    public void processFile(final String inputStream, final Long jobId, final String fileName) {
        final Job job = Job.findById(jobId);
        System.out.println(job.toString());

        try (CSVReader reader = new CSVReader(new StringReader(inputStream))) {
            // Skip the first line (header)
            reader.readNext();

            String[] line;
            final Map<String, Section> sectionMap = new HashMap<>();

            while ((line = reader.readNext()) != null) {
                final String sectionName = line[0];

                if (!sectionMap.containsKey(sectionName)) {
                    final Section section = new Section();
                    section.setName(sectionName);
                    section.setGeologicalClasses(new ArrayList<>());
                    sectionMap.put(sectionName, section);
                }

                final Section section = sectionMap.get(sectionName);

                for (int i = 1; i < line.length; i += 2) {
                    if (i + 1 < line.length) {
                        String className = line[i];
                        String classCode = line[i + 1];

                        if (!className.isEmpty() && !classCode.isEmpty()) {
                            final GeologicalClass geoClass = new GeologicalClass();
                            geoClass.setName(className);
                            geoClass.setCode(classCode);
                            geoClass.setSection(section);
                            section.getGeologicalClasses().add(geoClass);
                        }
                    }
                }
            }

            LOG.info("Completed parsing of CSV file, now writing to Database");
            for (Section section : sectionMap.values()) {
                try {
                    sectionRepository.persist(section);
                    LOG.info("Writing to database : " + section.getName());
                } catch (PersistenceException e) {
                    LOG.error("Writing to database failed : " + e.getMessage());
                    throw new RuntimeException("Failed to persist Section", e);
                }
            }

            // After successfully persisting, mark the job as completed
            job.status = "COMPLETED";
            job.filename = fileName;

        } catch (Exception e) {
            LOG.error("Writing to database failed : " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
