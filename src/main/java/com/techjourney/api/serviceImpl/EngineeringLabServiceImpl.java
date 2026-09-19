package com.techjourney.api.serviceImpl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.techjourney.api.dto.JavaStreamExperimentRequest;
import com.techjourney.api.dto.JavaStreamExperimentResponse;
import com.techjourney.api.dto.JavaStreamResultType;
import com.techjourney.api.dto.JavaStreamSortDirection;
import com.techjourney.api.dto.TimelineEventDto;
import com.techjourney.api.entity.TimelineCategory;
import com.techjourney.api.entity.TimelineEvent;
import com.techjourney.api.exception.EngineeringLabInvalidRequestException;
import com.techjourney.api.repository.TimelineEventRepository;
import com.techjourney.api.service.EngineeringLabService;

/*
 * Implements the backend logic for the Tech Journey Engineering Lab.
 *
 * Experiment 01 - Java Data Processing
 *
 * This service loads the real Tech Journey timeline records from the
 * database and processes them using Java Collections, Streams, Lambdas,
 * method references, filtering, sorting, mapping, limiting, and toList().
 *
 * The goal is for the Engineering Lab to visualize real Java execution
 * rather than simulate the processing entirely in Angular.
 *
 * Angular sends configuration describing how the experiment should run.
 * This service executes the requested Java Stream operations and returns:
 *
 * - the processed results
 * - record counts before and after processing
 * - actual backend execution time
 * - the Stream stages Angular should visualize
 *
 * No timeline data is changed by this experiment. It is read-only.
 */
@Service
public class EngineeringLabServiceImpl implements EngineeringLabService {

    private final TimelineEventRepository timelineEventRepository;

    /*
     * Constructor injection provides access to the existing timeline
     * repository without creating a separate repository just for the lab.
     */
    public EngineeringLabServiceImpl(
            TimelineEventRepository timelineEventRepository) {

        this.timelineEventRepository = timelineEventRepository;
    }

    /*
     * Runs Experiment 01 - Java Data Processing.
     *
     * Processing flow:
     *
     * Database
     *   -> List<TimelineEvent>
     *   -> stream()
     *   -> optional filter()
     *   -> sorted()
     *   -> map()
     *   -> optional limit()
     *   -> toList()
     *   -> experiment response
     *
     * The requested result type determines what the map operation produces:
     *
     * FULL_OBJECT -> TimelineEventDto
     * TITLE       -> String containing the event title
     * TECHNOLOGY  -> String containing the technology
     */
    @Override
    public JavaStreamExperimentResponse runJavaStreamExperiment(
            JavaStreamExperimentRequest request) {

        /*
         * Validate user-controlled experiment configuration before any database
         * access or Stream processing occurs.
         */
        validateRequest(request);

        /*
         * nanoTime() is used for elapsed-time measurement because it is
         * designed for measuring durations rather than clock time.
         */
        long startedAt = System.nanoTime();

        /*
         * Spring Data loads the real timeline records into a Java List.
         *
         * This List becomes the Collection at the beginning of the visual
         * Stream pipeline shown in Angular.
         */
        List<TimelineEvent> events =
                timelineEventRepository.findAll();

        int originalCount = events.size();

        /*
         * Angular will use this list to determine which stages should
         * illuminate in the Engineering Lab pipeline visualization.
         */
        List<String> stages = new ArrayList<>();

        stages.add("COLLECTION");
        stages.add("STREAM");

        /*
         * Start the actual Java Stream pipeline.
         */
        Stream<TimelineEvent> stream = events.stream();

        /*
         * filteredCounter records how many elements survive the optional
         * category filter before mapping or limiting occurs.
         */
        AtomicInteger filteredCounter = new AtomicInteger();

        /*
         * Category is optional.
         *
         * null, blank, or "ALL" means that every timeline event should
         * continue through the Stream.
         */
        if (hasCategoryFilter(request.getCategory())) {

            TimelineCategory category =
                    parseCategory(request.getCategory());

            stream = stream.filter(
                    event -> event.getCategory() == category);

            stages.add("FILTER");
        }

        /*
         * peek() observes the records that survived filtering without
         * changing them.
         *
         * This gives the experiment a real count for the "After Filter"
         * metric displayed by Angular.
         */
        stream = stream.peek(
                event -> filteredCounter.incrementAndGet());

        /*
         * Sorting is always part of this experiment.
         *
         * ASC  -> oldest year to newest year
         * DESC -> newest year to oldest year
         *
         * ASC is used as the default if no direction is supplied.
         */
        JavaStreamSortDirection sortDirection =
                request.getSortDirection() != null
                        ? request.getSortDirection()
                        : JavaStreamSortDirection.ASC;

        Comparator<TimelineEvent> comparator =
                Comparator.comparing(TimelineEvent::getYear);

        if (sortDirection == JavaStreamSortDirection.DESC) {
            comparator = comparator.reversed();
        }

        stream = stream.sorted(comparator);

        stages.add("SORT");

        /*
         * Every result type performs a real map operation.
         *
         * Even FULL_OBJECT uses map() because the JPA entity is converted
         * into a TimelineEventDto before it leaves the backend.
         */
        JavaStreamResultType resultType =
                request.getResultType() != null
                        ? request.getResultType()
                        : JavaStreamResultType.FULL_OBJECT;

        stages.add("MAP");

        /*
         * LIMIT is optional. A null value means that all matching results
         * should be returned.
         */
        boolean hasLimit =
                request.getLimit() != null
                        && request.getLimit() > 0;

        if (hasLimit) {
            stages.add("LIMIT");
        }

        /*
         * Execute the mapping selected by the visitor.
         *
         * The terminal toList() operation causes the Stream pipeline to
         * actually execute.
         */
        List<?> results;

        switch (resultType) {

            case TITLE -> {

                Stream<String> mappedStream =
                        stream.map(TimelineEvent::getTitle);

                mappedStream =
                        applyLimit(
                                mappedStream,
                                request.getLimit());

                results = mappedStream.toList();
            }

            case TECHNOLOGY -> {

                Stream<String> mappedStream =
                        stream.map(TimelineEvent::getTechnology);

                mappedStream =
                        applyLimit(
                                mappedStream,
                                request.getLimit());

                results = mappedStream.toList();
            }

            case FULL_OBJECT -> {

                Stream<TimelineEventDto> mappedStream =
                        stream.map(this::toDto);

                mappedStream =
                        applyLimit(
                                mappedStream,
                                request.getLimit());

                results = mappedStream.toList();
            }

            default ->
                throw new IllegalStateException(
                        "Unsupported Java Stream result type: "
                                + resultType);
        }

        stages.add("TO_LIST");
        stages.add("RESULT");

        /*
         * If no category filter was requested, every original record
         * reached this point before the optional limit.
         */
        int filteredCount =
                hasCategoryFilter(request.getCategory())
                        ? filteredCounter.get()
                        : originalCount;

        int finalCount = results.size();

        /*
         * Calculate the real execution duration.
         *
         * Very fast operations may legitimately report 0 ms.
         * We intentionally do not fake or inflate this value.
         */
        long executionTimeMs =
                (System.nanoTime() - startedAt)
                        / 1_000_000;

        return new JavaStreamExperimentResponse(
                originalCount,
                filteredCount,
                finalCount,
                executionTimeMs,
                stages,
                results);
    }

    /*
     * Validates configuration supplied by the Engineering Lab frontend.
     *
     * Optional fields are allowed because the experiment provides sensible
     * defaults when values are omitted.
     *
     * A supplied result limit must always be greater than zero.
     *
     * Invalid configuration is converted into an
     * EngineeringLabInvalidRequestException so the REST API can return
     * HTTP 400 BAD REQUEST instead of treating the problem as an internal
     * server error.
     */
    private void validateRequest(
            JavaStreamExperimentRequest request) {

        if (request == null) {
            throw new EngineeringLabInvalidRequestException(
                    "Java Stream experiment request cannot be null.");
        }

        if (request.getLimit() != null
                && request.getLimit() <= 0) {

            throw new EngineeringLabInvalidRequestException(
                    "Limit must be greater than zero.");
        }
    }

    /*
     * Determines whether a real category filter should be added to the
     * Stream pipeline.
     *
     * "ALL" represents the frontend option for processing every category.
     */
    private boolean hasCategoryFilter(String category) {

        return category != null
                && !category.isBlank()
                && !category.equalsIgnoreCase("ALL");
    }

    /*
     * Converts the category supplied by Angular into the TimelineCategory
     * enum used by the backend.
     *
     * TimelineCategory.valueOf() normally throws IllegalArgumentException
     * when an unknown category is supplied.
     *
     * That implementation exception is caught here and converted into an
     * EngineeringLabInvalidRequestException so the frontend receives a
     * meaningful HTTP 400 BAD REQUEST response.
     */
    private TimelineCategory parseCategory(
            String category) {

        try {

            return TimelineCategory.valueOf(
                    category.trim().toUpperCase());

        } catch (IllegalArgumentException exception) {

            throw new EngineeringLabInvalidRequestException(
                    "Unknown timeline category: " + category);
        }
    }

    /*
     * Applies Stream.limit() only when the visitor selected a positive
     * result limit.
     *
     * The generic method works for both TimelineEventDto and String Streams.
     */
    private <T> Stream<T> applyLimit(
            Stream<T> stream,
            Integer limit) {

        if (limit != null && limit > 0) {
            return stream.limit(limit);
        }

        return stream;
    }

    /*
     * Converts the persistence entity into the DTO returned through the API.
     *
     * This prevents the Engineering Lab from exposing JPA entities directly
     * and also allows FULL_OBJECT to demonstrate a real Java map operation.
     */
    private TimelineEventDto toDto(
            TimelineEvent event) {

        return new TimelineEventDto(
                event.getId(),
                event.getYear(),
                event.getCategory(),
                event.getTitle(),
                event.getDescription(),
                event.getTechnology());
    }
}