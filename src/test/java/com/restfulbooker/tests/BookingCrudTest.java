package com.restfulbooker.tests;

import org.apache.http.entity.ContentType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import us.abstracta.jmeter.javadsl.core.TestPlanStats;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static us.abstracta.jmeter.javadsl.JmeterDsl.*;

/**
 * JUnit 5 tests for Restful Booker API - Combined CRUD operations
 */
@DisplayName("Booking CRUD Operations Tests")
public class BookingCrudTest {

        private static final String BASE_URL = "http://localhost:3001";
        private static final String AUTH_URL = "https://restful-booker.herokuapp.com";

        @Test
        @DisplayName("Should perform full CRUD cycle")
        public void testFullCrudCycle() throws Exception {
                var authBody = """
                                {
                                    "username" : "admin",
                                    "password" : "password123"
                                }
                                """;

                var body = """
                                {
                                    "firstname" : "Jim",
                                    "lastname" : "Brown",
                                    "totalprice" : 111,
                                    "depositpaid" : true,
                                    "bookingdates" : {
                                        "checkin" : "2018-01-01",
                                        "checkout" : "2019-01-01"
                                    },
                                    "additionalneeds" : "Breakfast"
                                }
                                """;

                TestPlanStats stats = testPlan(
                                threadGroup(1, 1,
                                                httpSampler("Authenticate", AUTH_URL + "/auth")
                                                                .post(authBody, ContentType.APPLICATION_JSON)
                                                                .header("Content-Type", "application/json")
                                                                .children(
                                                                                jsonExtractor("token", "token")),

                                                httpSampler("Get All Bookings", BASE_URL + "/booking"),

                                                httpSampler("Create Booking", BASE_URL + "/booking")
                                                                .post(body, ContentType.APPLICATION_JSON)
                                                                .header("Accept", "*/*")
                                                                .children(
                                                                                jsonExtractor("bookingId",
                                                                                                "bookingid")),

                                                httpSampler("Get Booking ${bookingId}",
                                                                BASE_URL + "/booking/${bookingId}")
                                                                .method("GET")
                                                                .header("Accept", "*/*"),

                                                httpSampler("Update Booking", BASE_URL + "/booking/${bookingId}")
                                                                .header("Accept", "*/*")
                                                                .header("Authorization",
                                                                                "Basic YWRtaW46cGFzc3dvcmQxMjM=")
                                                                .header("Content-Type", "application/json")
                                                                .method("PUT")
                                                                .body("{\n" + //
                                                                                "    \"firstname\" : \"James\",\n" + //
                                                                                "    \"lastname\" : \"Brown\",\n" + //
                                                                                "    \"totalprice\" : 111,\n" + //
                                                                                "    \"depositpaid\" : true,\n" + //
                                                                                "    \"bookingdates\" : {\n" + //
                                                                                "        \"checkin\" : \"2018-01-01\",\n"
                                                                                + //
                                                                                "        \"checkout\" : \"2019-01-01\"\n"
                                                                                + //
                                                                                "    },\n" + //
                                                                                "    \"additionalneeds\" : \"Breakfast\"\n"
                                                                                + //
                                                                                "}"),

                                                httpSampler("Delete Booking", BASE_URL + "/booking/${bookingId}")
                                                                .header("Accept", "*/*")
                                                                .header("Content-Type", "application/json")
                                                                .header("Authorization",
                                                                                "Basic YWRtaW46cGFzc3dvcmQxMjM=")
                                                                .method("DELETE"),

                                                resultsTreeVisualizer()))
                                .run();

                assertTrue(stats.overall().samplesCount() >= 6, "Test should execute at least 6 samples");
        }
}
