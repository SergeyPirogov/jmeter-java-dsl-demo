package com.restfulbooker.actions;

import us.abstracta.jmeter.javadsl.core.postprocessors.DslJsonExtractor;

import static us.abstracta.jmeter.javadsl.JmeterDsl.jsonExtractor;

/**
 * DataExtractionActions provides data extraction operations for test flow.
 */
public class DataExtractionActions {

    /**
     * Extract booking ID from response
     */
    public static DslJsonExtractor extractBookingId() {
        return jsonExtractor("bookingId", "bookingid");
    }
}
