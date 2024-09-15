package org.task1.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Section {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    @JsonManagedReference
    @OneToMany(mappedBy = "section", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<GeologicalClass> geologicalClasses;
}