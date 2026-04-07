package com.restfulbooker.tests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import us.abstracta.jmeter.javadsl.core.TestPlanStats;

import com.restfulbooker.api.BookingApiClient;
import com.restfulbooker.actions.AuthenticationActions;
import com.restfulbooker.actions.DataExtractionActions;
import com.restfulbooker.data.BookingPayloads;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static us.abstracta.jmeter.javadsl.JmeterDsl.*;

/**
 * JUnit 5 tests for Restful Booker API - Combined CRUD operations
 */
@DisplayName("Booking CRUD Operations Tests")
public class BookingCrudTest {

    private static final String BASE_URL = "http://localhost:3001";
    private static final String AUTH_URL = "https://restful-booker.herokuapp.com";
    private static final String ADMIN_USER = "admin";
    private static final String ADMIN_PASSWORD = "password123";

    // InfluxDB configuration
    private static final String INFLUX_URL = "http://localhost:8086/write?db=jmeter";

    @Test
    @DisplayName("Should perform full CRUD cycle")
    public void testFullCrudCycle() throws Exception {
        BookingApiClient apiClient = new BookingApiClient(BASE_URL);
        AuthenticationActions authActions = new AuthenticationActions(AUTH_URL);

        TestPlanStats stats = testPlan(
                threadGroup(1, 100,
                        authActions.authenticate(ADMIN_USER, ADMIN_PASSWORD),
                        apiClient.getAllBookings(),
                        apiClient.createBooking(BookingPayloads.createBookingPayload())
                                .children(
                                        DataExtractionActions.extractBookingId()
                                ),
                        apiClient.getBooking("${bookingId}"),
                        apiClient.updateBooking(BookingPayloads.updateBookingPayload()),
                        apiClient.deleteBooking(),
                        resultsTreeVisualizer()
                ),
                influxDbListener(INFLUX_URL)
        ).run();

        assertTrue(stats.overall().samplesCount() >= 6, "Test should execute at least 6 samples");
    }
}
