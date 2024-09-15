package org.task1.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.task1.entity.Section;

@ApplicationScoped
public class SectionRepository implements PanacheRepository<Section> {}