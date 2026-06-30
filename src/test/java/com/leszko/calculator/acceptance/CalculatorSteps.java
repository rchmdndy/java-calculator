package com.leszko.calculator.acceptance;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class CalculatorSteps {

    private static final String BASE_URL = System.getProperty("calculator.url", "http://localhost:8080");
    private final HttpClient client = HttpClient.newHttpClient();
    private int a;
    private int b;
    private String response;

    @Given("the calculator endpoint is available")
    public void the_calculator_endpoint_is_available() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/sum?a=0&b=0"))
                .GET()
                .build();
        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, httpResponse.statusCode(), "Calculator endpoint is not available at " + BASE_URL);
    }

    @When("I add {int} and {int}")
    public void i_add_and(Integer a, Integer b) throws Exception {
        this.a = a;
        this.b = b;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/sum?a=" + a + "&b=" + b))
                .GET()
                .build();
        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
        this.response = httpResponse.body();
    }

    @Then("the result should be {int}")
    public void the_result_should_be(Integer expected) {
        assertEquals(String.valueOf(expected), response,
                "Expected " + a + " + " + b + " = " + expected + " but got " + response);
    }
}
