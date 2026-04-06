# JMeter Java DSL - Restful Booker Tests

A performance and functional testing suite for the [Restful Booker API](https://restfulbooker.herokuapp.com) using JMeter Java DSL with **JUnit 5**.

## Project Structure

```
jmeter-java-dsl/
├── pom.xml                          # Maven configuration with JUnit 5
├── src/
│   └── test/java/com/restfulbooker/tests/
│       └── BookingCrudTest.java     # JUnit 5 CRUD operation tests
└── README.md                        # This file
```

## Prerequisites

- Java 11 or higher
- Maven 3.6+
- Internet connection (to download dependencies)

## Setup

1. **Navigate to project directory:**
   ```bash
   cd /Users/spirohov/work/jmeter-java-dsl
   ```

2. **Install dependencies:**
   ```bash
   mvn clean install
   ```

## Running Tests

### Run All Tests
```bash
mvn test
```

### Run Specific Test Method
```bash
mvn test -Dtest=BookingCrudTest#testFullCrudCycle
mvn test -Dtest=BookingCrudTest#testGetBookingsByVariousIds
```

## Test Class: BookingCrudTest

### Test Methods

- **testFullCrudCycle()** - Complete CRUD lifecycle test
  - Get all bookings
  - Create a new booking
  - Retrieve specific booking
  - Update booking details
  - Delete booking

- **testGetBookingsByVariousIds()** - Parameterized test with multiple booking IDs (1, 2, 3)
  - Runs the same test with different booking IDs
  - Validates test execution and response handling

## JUnit 5 Features Used

- **@Test** - Mark methods as test methods
- **@DisplayName** - Provide readable test names
- **@ParameterizedTest** - Run tests with multiple parameters
- **@ValueSource** - Provide parameter values for parameterized tests
- **Assertions** - assertEquals(), assertTrue()

## API Endpoints Tested

- `GET /booking` - Get all bookings
- `GET /booking/{id}` - Get specific booking
- `POST /booking` - Create new booking
- `PUT /booking/{id}` - Update booking
- `DELETE /booking/{id}` - Delete booking

## Customization

### Change Base URL
Update `BASE_URL` constant in BookingCrudTest:
```java
private static final String BASE_URL = "http://localhost:3001";
```

### Modify Request Payloads
Update booking JSON in POST/PUT requests to match your test data.

## Dependencies

- **jmeter-java-dsl** - DSL for defining JMeter tests in Java
- **junit-jupiter** (JUnit 5) - Testing framework
- **junit-jupiter-params** - Parameterized test support
- **slf4j** - Logging
- **gson** - JSON processing

## Assertions & Validation

```java
// Check for no errors
assertEquals(0, stats.errorCount());

// Verify samples executed
assertTrue(stats.samplesCount() > 0);
```

## Resources

- [JMeter Java DSL Documentation](https://abstracta.github.io/jmeter-java-dsl/)
- [JUnit 5 User Guide](https://junit.org/junit5/docs/current/user-guide/)
- [Restful Booker API Docs](https://restfulbooker.herokuapp.com/)
- [JMeter Official Documentation](https://jmeter.apache.org/)
