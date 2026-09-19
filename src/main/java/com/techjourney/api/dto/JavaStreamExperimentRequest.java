package com.techjourney.api.dto;

/*
 * Represents the configuration sent from the Angular Engineering Lab
 * to the Spring Boot backend when running the Java Stream experiment.
 *
 * The request controls how the timeline dataset should be processed:
 *
 * category:
 * Filters timeline events by category. A value such as "ALL" can be used
 * to indicate that no category filter should be applied.
 *
 * sortDirection:
 * Determines whether the timeline events are sorted in ascending or
 * descending year order.
 *
 * resultType:
 * Determines how the Stream maps the final results, such as returning
 * complete objects, titles, or technology names.
 *
 * limit:
 * Optionally limits the number of final results returned. A null value
 * means no limit should be applied.
 *
 * This DTO contains only experiment input. The actual Stream processing
 * will be performed inside the Engineering Lab service implementation.
 */

public class JavaStreamExperimentRequest {

    private String category;

    private JavaStreamSortDirection sortDirection;

    private JavaStreamResultType resultType;

    private Integer limit;

    public JavaStreamExperimentRequest() {
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public JavaStreamSortDirection getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(JavaStreamSortDirection sortDirection) {
        this.sortDirection = sortDirection;
    }

    public JavaStreamResultType getResultType() {
        return resultType;
    }

    public void setResultType(JavaStreamResultType resultType) {
        this.resultType = resultType;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}