package org.task1.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

import java.time.LocalDateTime;

@Entity
public class Job extends PanacheEntity {
    public String filename;
    public String status; // e.g., PENDING, COMPLETED, ERROR
    public LocalDateTime createdAt = LocalDateTime.now();
}
