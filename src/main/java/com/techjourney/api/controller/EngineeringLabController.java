package com.techjourney.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techjourney.api.dto.JavaStreamExperimentRequest;
import com.techjourney.api.dto.JavaStreamExperimentResponse;
import com.techjourney.api.service.EngineeringLabService;

/*
 * REST controller for Tech Journey Engineering Lab experiments.
 *
 * The controller receives experiment requests from the Angular frontend
 * and delegates the actual Java processing to EngineeringLabService.
 *
 * The controller intentionally contains very little business logic.
 * Its responsibility is to:
 *
 * - expose Engineering Lab HTTP endpoints
 * - receive request data from Angular
 * - call the appropriate service method
 * - return the experiment response to the frontend
 *
 * Keeping the controller thin preserves the same Controller -> Service
 * separation used throughout the rest of the Tech Journey backend.
 *
 * Current experiment:
 *
 * POST /api/lab/java/streams/run
 *
 * Runs Experiment 01 - Java Data Processing using real Java Streams.
 */
@RestController
@RequestMapping("/api/lab")
public class EngineeringLabController {

    private final EngineeringLabService engineeringLabService;

    /*
     * Constructor injection provides the controller with access to the
     * Engineering Lab service implementation.
     */
    public EngineeringLabController(
            EngineeringLabService engineeringLabService) {

        this.engineeringLabService = engineeringLabService;
    }

    /*
     * Runs Experiment 01 - Java Data Processing.
     *
     * Angular sends configuration such as:
     *
     * - category
     * - sort direction
     * - result type
     * - optional result limit
     *
     * Spring Boot then executes the real Java Stream pipeline and returns
     * the processed results, record counts, execution time, and pipeline
     * stages that Angular can visualize.
     */
    @PostMapping("/java/streams/run")
    public ResponseEntity<JavaStreamExperimentResponse>
            runJavaStreamExperiment(
                    @RequestBody JavaStreamExperimentRequest request) {

        JavaStreamExperimentResponse response =
                engineeringLabService.runJavaStreamExperiment(request);

        return ResponseEntity.ok(response);
    }
}