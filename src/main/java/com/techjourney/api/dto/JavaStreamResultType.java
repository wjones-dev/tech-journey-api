package com.techjourney.api.dto;

/*
 * Defines the possible output formats for the Java Stream experiment.
 *
 * FULL_OBJECT:
 * Returns the complete timeline event objects after Stream processing.
 *
 * TITLE:
 * Maps each processed timeline event to only its title.
 *
 * TECHNOLOGY:
 * Maps each processed timeline event to only its technology value.
 *
 * This enum keeps the experiment strongly typed and allows the Angular
 * frontend to control how the Java Stream transforms the final results.
 */


public enum JavaStreamResultType {

    FULL_OBJECT,
    TITLE,
    TECHNOLOGY
}