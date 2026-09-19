package com.techjourney.api.dto;

import java.time.LocalDateTime;

import com.techjourney.api.entity.TimelineCategory;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SandboxEventDto {

	private Long id;
	private Integer year;
	private TimelineCategory category;
	private String title;
	private String description;
	private String technology;
	private LocalDateTime createdAt;

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

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public SandboxEventDto(Long id, Integer year, TimelineCategory category, String title, String description,
			String technology, LocalDateTime createdAt) {

		this.id = id;
		this.year = year;
		this.category = category;
		this.title = title;
		this.description = description;
		this.technology = technology;
		this.createdAt = createdAt;
	}
}