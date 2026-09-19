package com.techjourney.api.dto;

/*
 * Defines the sort direction used by the Java Stream experiment.
 *
 * ASC:
 * Sorts timeline events from lowest year to highest year.
 *
 * DESC:
 * Sorts timeline events from highest year to lowest year.
 *
 * This enum is used by the experiment request so the frontend can
 * choose how Java's Stream pipeline sorts the timeline dataset.
 */


public enum JavaStreamSortDirection {

    ASC,
    DESC
}