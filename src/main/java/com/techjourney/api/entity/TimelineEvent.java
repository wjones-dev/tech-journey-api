package com.techjourney.api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "timeline_event")
public class TimelineEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "event_year")
    private Integer year;

    @Enumerated(EnumType.STRING)
    private TimelineCategory category;

    private String title;

    private String description;

    private String technology;

    public TimelineEvent() {
        // Required by JPA
    }

    public TimelineEvent(Integer year, TimelineCategory category, String title,
            String description, String technology) {

        this.year = year;
        this.category = category;
        this.title = title;
        this.description = description;
        this.technology = technology;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public TimelineCategory getCategory() {
        return category;
    }

    public void setCategory(TimelineCategory category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTechnology() {
        return technology;
    }

    public void setTechnology(String technology) {
        this.technology = technology;
    }
}