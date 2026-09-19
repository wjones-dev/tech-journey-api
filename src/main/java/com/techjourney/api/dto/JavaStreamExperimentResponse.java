package com.techjourney.api.dto;

import java.util.List;

/*
 * Represents the result returned by the Spring Boot backend after
 * executing the Java Data Processing experiment.
 *
 * The response contains both the processed data and information Angular
 * can use to visualize what happened during the Java Stream pipeline.
 *
 * originalCount:
 * The number of timeline records loaded before any Stream operations
 * such as filtering or limiting are applied.
 *
 * filteredCount:
 * The number of records remaining after the optional category filter
 * has been applied.
 *
 * finalCount:
 * The number of records contained in the final result after all Stream
 * operations, including sorting, mapping, and limiting.
 *
 * executionTimeMs:
 * The amount of time the backend spent executing the experiment,
 * measured in milliseconds.
 *
 * stages:
 * Describes the Java Stream operations that participated in the
 * experiment. Angular will use these values to animate the visual
 * pipeline such as COLLECTION, STREAM, FILTER, SORT, MAP, TO_LIST,
 * and RESULT.
 *
 * results:
 * Contains the final data produced by the Stream pipeline. The exact
 * object type depends on JavaStreamResultType. Results may contain
 * complete TimelineEventDto objects or simple String values such as
 * titles or technologies.
 *
 * This response allows Angular to visualize real Java processing rather
 * than simulating the Stream operations entirely in the frontend.
 */
public class JavaStreamExperimentResponse {

    private int originalCount;

    private int filteredCount;

    private int finalCount;

    private long executionTimeMs;

    private List<String> stages;

    private List<?> results;

    public JavaStreamExperimentResponse() {
    }

    public JavaStreamExperimentResponse(
            int originalCount,
            int filteredCount,
            int finalCount,
            long executionTimeMs,
            List<String> stages,
            List<?> results) {

        this.originalCount = originalCount;
        this.filteredCount = filteredCount;
        this.finalCount = finalCount;
        this.executionTimeMs = executionTimeMs;
        this.stages = stages;
        this.results = results;
    }

    public int getOriginalCount() {
        return originalCount;
    }

    public void setOriginalCount(int originalCount) {
        this.originalCount = originalCount;
    }

    public int getFilteredCount() {
        return filteredCount;
    }

    public void setFilteredCount(int filteredCount) {
        this.filteredCount = filteredCount;
    }

    public int getFinalCount() {
        return finalCount;
    }

    public void setFinalCount(int finalCount) {
        this.finalCount = finalCount;
    }

    public long getExecutionTimeMs() {
        return executionTimeMs;
    }

    public void setExecutionTimeMs(long executionTimeMs) {
        this.executionTimeMs = executionTimeMs;
    }

    public List<String> getStages() {
        return stages;
    }

    public void setStages(List<String> stages) {
        this.stages = stages;
    }

    public List<?> getResults() {
        return results;
    }

    public void setResults(List<?> results) {
        this.results = results;
    }
}