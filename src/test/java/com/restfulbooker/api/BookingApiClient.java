package com.restfulbooker.api;

import org.apache.http.entity.ContentType;
import us.abstracta.jmeter.javadsl.http.DslHttpSampler;

import static us.abstracta.jmeter.javadsl.JmeterDsl.httpSampler;

/**
 * BookingApiClient provides methods to build HTTP samplers for Booking API operations.
 * This layer abstracts HTTP configuration and allows reusable API calls.
 */
public class BookingApiClient {

    private static final String BASIC_AUTH = "Basic YWRtaW46cGFzc3dvcmQxMjM=";
    private static final String CONTENT_TYPE_JSON = "application/json";

    private final String baseUrl;

    public BookingApiClient(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    /**
     * Get all bookings
     */
    public DslHttpSampler getAllBookings() {
        return httpSampler("Get All Bookings", baseUrl + "/booking")
                .method("GET")
                .header("Accept", "*/*");
    }

    /**
     * Create a new booking
     */
    public DslHttpSampler createBooking(String body) {
        return httpSampler("Create Booking", baseUrl + "/booking")
                .post(body, ContentType.APPLICATION_JSON)
                .header("Accept", "*/*");
    }

    /**
     * Get a specific booking by ID
     */
    public DslHttpSampler getBooking(String bookingId) {
        return httpSampler("Get Booking by ID", baseUrl + "/booking/${bookingId}")
                .method("GET")
                .header("Accept", "*/*");
    }

    /**
     * Update a booking with Basic Authentication
     */
    public DslHttpSampler updateBooking(String body) {
        return httpSampler("Update Booking", baseUrl + "/booking/${bookingId}")
                .header("Accept", "*/*")
                .header("Authorization", BASIC_AUTH)
                .header("Content-Type", CONTENT_TYPE_JSON)
                .method("PUT")
                .body(body);
    }

    /**
     * Delete a booking with Basic Authentication
     */
    public DslHttpSampler deleteBooking() {
        return httpSampler("Delete Booking", baseUrl + "/booking/${bookingId}")
                .header("Accept", "*/*")
                .header("Content-Type", CONTENT_TYPE_JSON)
                .header("Authorization", BASIC_AUTH)
                .method("DELETE");
    }
}
