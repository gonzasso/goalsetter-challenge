package com.goalsetter.api.stepdefs;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.goalsetter.api.dtos.AuthRequest;
import com.goalsetter.api.dtos.AuthResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import utils.api.SortingNodeFactory;


import java.io.IOException;
import java.util.Base64;

import static java.util.Base64.getUrlDecoder;
import static properties.TestProperties.TEST_PROPERTIES;

public class WalletInfoSteps {

    private final String BASE_URL = "https://qa-api.goalsetter.co";

    private Scenario scenario;
    private String accessToken;
    private Response parentInfoResponse;

    @Before
    public void before(Scenario scenarioVal) {
        this.scenario = scenarioVal;
    }

    @Given("I am an authorized user")
    public void get_access_token() throws Exception {
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();
        AuthRequest authRequest = AuthRequest.builder()
                .email(TEST_PROPERTIES.getAuthEmail())
                .password(TEST_PROPERTIES.getAuthPassword())
                .build();

        request.body(authRequest).header("Content-Type", "application/json");

        Response response = request.post("/auth/credentials");
        int responseCode = response.then().extract().statusCode();

        Assert.assertEquals(200, responseCode);
        accessToken = response.getBody().as(AuthResponse.class).getToken();
    }

    @When("I get the parent information")
    public void get_parent_info() throws ParseException {
        //Decode token
        String[] chunks = accessToken.split("\\.");
        Base64.Decoder decoder = getUrlDecoder();

        String payload = new String(decoder.decode(chunks[1]));

        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(payload);

        String rid = json.get("rid").toString();

        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + accessToken)
                .pathParam("rid", rid);
        this.parentInfoResponse = request.get("/Parents/{rid}");
        int responseCode = this.parentInfoResponse.then().extract().statusCode();
        Assert.assertEquals(200, responseCode);
    }

    @Then("Parent {string} must be {string}")
    public void validate_parent_info(String key, String value) throws IOException {

        String actualValue = this.parentInfoResponse.jsonPath().get(key).toString();

        Assert.assertEquals(value, actualValue);

        // Sort fields from response
        SortingNodeFactory factory = new SortingNodeFactory();
        ObjectMapper mapper = JsonMapper.builder()
                .nodeFactory(factory)
                .build();
        JsonNode root = mapper.readTree(this.parentInfoResponse.getBody().asString());
        String orderedResponse = mapper.writeValueAsString(root);

        // Pretty printing
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonElement je = JsonParser.parseString(orderedResponse);
        String prettyJsonString = gson.toJson(je);

        scenario.log("Parent info == " + prettyJsonString);
    }
}
