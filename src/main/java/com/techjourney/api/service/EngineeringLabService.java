package com.techjourney.api.service;

import com.techjourney.api.dto.JavaStreamExperimentRequest;
import com.techjourney.api.dto.JavaStreamExperimentResponse;

/*
 * Defines the backend contract for Engineering Lab experiments.
 *
 * The Engineering Lab is intended to demonstrate how Java and Spring
 * process data internally, rather than only exposing traditional CRUD
 * operations.
 *
 * This service currently supports the Java Stream experiment used by
 * Experiment 01 - Java Data Processing.
 *
 * runJavaStreamExperiment:
 * Accepts experiment configuration from the Angular frontend and returns
 * the processed results, execution metrics, and pipeline stages that
 * Angular can use to visualize the Stream execution.
 *
 * Keeping the experiment logic behind a service interface helps maintain
 * the same Controller -> Service separation used throughout the rest of
 * the Tech Journey Spring Boot application.
 *
 * Additional Engineering Lab experiments can be added to this service
 * later, such as request lifecycle demonstrations or Spring Security/JWT.
 */

public interface EngineeringLabService {

    JavaStreamExperimentResponse runJavaStreamExperiment(
            JavaStreamExperimentRequest request);
}