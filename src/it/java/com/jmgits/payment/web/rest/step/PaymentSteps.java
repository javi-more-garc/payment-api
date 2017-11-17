package com.jmgits.payment.web.rest.step;

import com.google.gson.reflect.TypeToken;
import com.jmgits.payment.base.util.JsonUtils;
import com.jmgits.payment.core.model.Payment;
import com.jmgits.payment.core.view.PaymentCreateOrUpdate;
import com.jmgits.payment.core.view.PaymentSearch;
import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;
import org.json.JSONException;
import org.skyscreamer.jsonassert.JSONAssert;

import java.lang.reflect.Type;
import java.util.Map;

import static io.restassured.mapper.ObjectMapperType.GSON;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.springframework.http.HttpStatus.*;

/**
 * Created by javi.more.garc on 12-11-17.
 */
public class PaymentSteps {

    private static final String PATH = "/v1/payments/";

    private String serverUrl;
    private Response response;

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    @Step("We try to search for payments")
    public Response search(PaymentSearch criteriaSearch) {

        String json = JsonUtils.write(criteriaSearch);

        Type type = new TypeToken<Map<String, String>>() {
        }.getType();

        Map<String, ?> params = JsonUtils.read(json, type);

        response = SerenityRest.rest()
                .contentType("application/json")
                .queryParams(params)
                .log().all()
                .when().get(serverUrl + PATH + "search");

        return response;
    }

    @Step("We check the payments were searched successfully")
    public void checkSearchedOk(Object expected) throws JSONException {

        int statusCode = response.getStatusCode();

        assertThat(statusCode, is(OK.value()));

        JSONAssert.assertEquals(JsonUtils.write(expected), response.getBody().prettyPrint(), false);
    }

    @Step("We try to retrieve the latest payments")
    public Response getLatest() {

        response = SerenityRest.rest()
                .contentType("application/json")
                .log().all()
                .when().get(serverUrl + PATH);

        return response;
    }

    @Step("We check the latest payments were retrieved successfully")
    public void checkGotLatestOk(Object expected) throws JSONException {

        int statusCode = response.getStatusCode();

        assertThat(statusCode, is(OK.value()));

        JSONAssert.assertEquals(JsonUtils.write(expected), response.getBody().prettyPrint(), false);
    }

    @Step("We try to retrieve the payment with id ({0})")
    public Response getById(String id) {

        response = SerenityRest.rest()
                .contentType("application/json")
                .log().all()
                .when().get(serverUrl + PATH + id);

        return response;
    }

    @Step("We check the payment was retrieved successfully")
    public void checkGotByIdOk(Object expected) throws JSONException {

        int statusCode = response.getStatusCode();

        assertThat(statusCode, is(OK.value()));

        JSONAssert.assertEquals(JsonUtils.write(expected), response.getBody().prettyPrint(), false);
    }

    @Step("We try to create a new payment")
    public Response create(PaymentCreateOrUpdate payment) {

        response = SerenityRest.rest()
                .contentType("application/json")
                .body(payment, GSON)
                .log().all()
                .when().post(serverUrl + PATH);

        return response;
    }

    @Step("We check the payment was created successfully")
    public String checkCreatedOk() {

        int statusCode = response.getStatusCode();

        assertThat(statusCode, is(CREATED.value()));

        String id = response.body().as(Payment.class, GSON).getId();

        assertThat(id, is(notNullValue()));

        return id;
    }

    @Step("We try to update the payment with id ({0})")
    public Response update(String id, PaymentCreateOrUpdate payment) {

        response = SerenityRest.rest()
                .contentType("application/json")
                .body(payment, GSON)
                .log().all()
                .when().put(serverUrl + PATH + id);

        return response;
    }

    @Step("We check the payment was updated successfully")
    public void checkUpdatedOk(String id) {

        int statusCode = response.getStatusCode();

        assertThat(statusCode, is(OK.value()));
    }

    @Step("We try to delete the payment with id ({0})")
    public Response delete(String id) {

        response = SerenityRest.rest()
                .contentType("application/json")
                .log().all()
                .when().delete(serverUrl + PATH + id);

        return response;
    }

    @Step("We check the payment was deleted successfully")
    public void checkDeletedOk() {

        int statusCode = response.getStatusCode();

        assertThat(statusCode, is(NO_CONTENT.value()));
    }

    @Step("We check the payment was not found")
    public void checkNotFound() {

        int statusCode = response.getStatusCode();

        assertThat(statusCode, is(NOT_FOUND.value()));
    }
}
