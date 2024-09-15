package org.task1.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

import java.time.LocalDateTime;

@Entity
public class FileResult extends PanacheEntity {
    public String jobId;
    public String name;
    public String code;
    public String result;
    public LocalDateTime processedAt;
}
