package com.autoui.fwk.rest;

import com.autoui.fwk.reporting.Logger;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Client {

    private static final Logger _logger = Logger.getLogger(Client.class);

    RestAssured restAssured = null;

    public static final int GET_RESPONSE_CODE = 200;
    public static final int PUT_RESPONSE_CODE = 201;
    public static final int POST_RESPONSE_CODE = 201;
    public static final int DELETE_RESPONSE_CODE = 202;

    public void Client() {
        restAssured = new RestAssured();
    }

    /*
     *** Sets ContentType*** We should set content type as JSON or XML before
     * starting the test
     */
    public void setContentType(ContentType Type) {
        _logger.info("Setting content type :" + Type);
        restAssured.given().contentType(Type);
    }

    public void setDefaultContentType() {
        _logger.info("Setting JSON as default content type");
        restAssured.given().contentType(ContentType.JSON);
    }

    public Map getDefaultHeader() {
        Map<String, String> header = new HashMap<String, String>();
        header.put("Content-Type", "application/json");
        return header;
    }

    public Response performAuthentication(String url, String userName, String password) {
        _logger.info("Performing authentication on url: " + url + ", userName: " + userName + " password: " + password);
        return restAssured.given().relaxedHTTPSValidation().auth().basic(userName, password).post();
    }

    // ********** Get Requests *************//
    public Response getRequest() {
        return RestAssured.given().relaxedHTTPSValidation().when().get();
    }

    public Response getRequestByUrl(String getUrl) {
        _logger.info("**************  Get Request Details Starts   *******************");
        _logger.info("URL:" + getUrl);
        _logger.info("**************  Get Request Details Ends     *******************");
        return RestAssured.given().relaxedHTTPSValidation().urlEncodingEnabled(false).when().get(getUrl);
    }

    public Response getRequestByHeaders(String getUrl, Map<String, String> headers) {
        _logger.info("**************  Get Request Details Starts   *******************");
        _logger.info("URL:" + getUrl);
        _logger.info("Header:" + headers.toString());
        _logger.info("**************  Get Request Details Ends     *******************");
        return RestAssured.given().relaxedHTTPSValidation().urlEncodingEnabled(false).headers(headers)
                .when().get(getUrl);
    }

    public Response getRequestByHeadersAndParams(String getUrl, Map<String, String> headers,
                                                 Map<String, String> params) {
        _logger.info("**************  Get Request Details Starts   *******************");
        _logger.info("URL:" + getUrl);
        _logger.info("Header:" + headers.toString());
        _logger.info("Params:" + params.toString());
        _logger.info("**************  Get Request Details Ends     *******************");
        return RestAssured.given().headers(headers).relaxedHTTPSValidation().urlEncodingEnabled(false)
                .params(params).when().get(getUrl);
    }

    public Response getRequestByHeadersAndQueryParams(String getUrl, Map<String, String> headers,
                                                      Map<String, String> queryParams) {
        _logger.info("**************  Get Request Details Starts   *******************");
        _logger.info("URL:" + getUrl);
        _logger.info("Header:" + headers.toString());
        _logger.info("Query Params:" + queryParams.toString());
        _logger.info("**************  Get Request Details Ends     *******************");
        return RestAssured.given().queryParams(queryParams).headers(headers).relaxedHTTPSValidation()
                .urlEncodingEnabled(false).when().get(getUrl);
    }

    public Response getRequestBySpecification(String getUrl, RequestSpecification requestSpecification) {
        _logger.info("**************  Get Request Details Starts   *******************");
        _logger.info("URL:" + getUrl);
        _logger.info("Request Spec:" + requestSpecification.toString());
        _logger.info("**************  Get Request Details Ends     *******************");
        return RestAssured.given().relaxedHTTPSValidation().urlEncodingEnabled(false)
                .spec(requestSpecification).when().get(getUrl);
    }

    // ********** Post Requests *************//
    public Response postRequest() {
        Response response = RestAssured.given().relaxedHTTPSValidation().post();
        return response;
    }

    public Response postRequestByUrl(String postUrl) {
        _logger.info("**************   Post Request Details Starts   *******************");
        _logger.info("URL:" + postUrl);
        _logger.info("**************   Request Details Ends     *******************");
        return RestAssured.given().relaxedHTTPSValidation().urlEncodingEnabled(false).post(postUrl);
    }

    public Response postRequestByUrlAndHeaders(String postUrl, Map<String, String> headers) {
        _logger.info("**************  Post Request Details Starts   *******************");
        _logger.info("URL:" + postUrl);
        _logger.info("Header:" + headers.toString());
        _logger.info("**************   Post Request Details Ends     *******************");
        return RestAssured.given().headers(headers).relaxedHTTPSValidation().urlEncodingEnabled(false)
                .post(postUrl);
    }

    public Response postRequestByUrlAndHeadersAndBodyContent(String postUrl, Map<String, String> headers,
                                                             String bodyContent) {
        _logger.info("**************  Post Request Details Starts   *******************");
        _logger.info("URL:" + postUrl);
        _logger.info("Header:" + headers.toString());
        _logger.info("Body:" + bodyContent);
        _logger.info("**************  Post Request Details Ends     *******************");
        return RestAssured.given().headers(headers).relaxedHTTPSValidation().urlEncodingEnabled(false)
                .body(bodyContent).post(postUrl);
    }

    public Response postRequestByUrlAndHeadersAndBodyObject(String postUrl, Map<String, String> headers,
                                                            Object object) {
        _logger.info("**************  Post Request Details Starts   *******************");
        _logger.info("URL:" + postUrl);
        _logger.info("Header:" + headers.toString());
        _logger.info("Body:" + object.toString());
        _logger.info("**************  Post Request Details Ends     *******************");
        return RestAssured.given().headers(headers).relaxedHTTPSValidation().urlEncodingEnabled(false)
                .body(object).post(postUrl);
    }

    // ********** Put Requests *************//

    public Response putRequest() {
        Response response = RestAssured.given().relaxedHTTPSValidation().urlEncodingEnabled(false).put();
        return response;
    }

    public Response putRequestByUrl(String putUrl) {
        _logger.info("**************  Put Request Details Starts   *******************");
        _logger.info("URL:" + putUrl);
        _logger.info("**************  Put Request Details Ends     *******************");
        return RestAssured.given().relaxedHTTPSValidation().urlEncodingEnabled(false).put(putUrl);
    }

    public Response putRequestByUrlAndHeaders(String putUrl, Map<String, String> headers) {
        _logger.info("**************  Put Request Details Starts   *******************");
        _logger.info("URL:" + putUrl);
        _logger.info("Header:" + headers.toString());
        _logger.info("************** Put Request Details Ends     *******************");
        return RestAssured.given().headers(headers).relaxedHTTPSValidation().urlEncodingEnabled(false)
                .put(putUrl);
    }

    public Response putRequestByUrlAndHeadersAndBodyContent(String putUrl, Map<String, String> headers,
                                                            String bodyContent) {
        _logger.info("**************  Put Request Details Starts   *******************");
        _logger.info("URL:" + putUrl);
        _logger.info("Header:" + headers.toString());
        _logger.info("Body:" + bodyContent);
        _logger.info("**************  Put Request Details Ends     *******************");
        return RestAssured.given().headers(headers).relaxedHTTPSValidation().urlEncodingEnabled(false)
                .body(bodyContent).put(putUrl);
    }

    // ********** Delete Requests *************//

    public Response deleteRequest() {
        return RestAssured.given().relaxedHTTPSValidation().delete();
    }

    public Response deleteRequestByUrl(String deleteUrl) {
        _logger.info("**************  Delete Request Details Starts   *******************");
        _logger.info("URL:" + deleteUrl);
        _logger.info("**************  Delete Request Details Ends     *******************");
        return RestAssured.given().relaxedHTTPSValidation().urlEncodingEnabled(false).delete(deleteUrl);
    }

    public Response deleteRequestByUrlAndHeaders(String deleteUrl, Map<String, String> headers) {
        _logger.info("**************  Delete Request Details Starts   *******************");
        _logger.info("URL:" + deleteUrl);
        _logger.info("Header:" + headers.toString());
        _logger.info("**************  Delete Request Details Ends     *******************");
        return RestAssured.given().headers(headers).relaxedHTTPSValidation().urlEncodingEnabled(false)
                .delete(deleteUrl);
    }

    public Response deleteRequestByUrlHeadersAndContent(String deleteUrl, Map<String, String> headers,
                                                        String bodyContent) {
        _logger.info("**************  Delete Request Details Starts   *******************");
        _logger.info("URL:" + deleteUrl);
        _logger.info("Header:" + headers.toString());
        _logger.info("**************  Delete Request Details Ends     *******************");
        return RestAssured.given().headers(headers).relaxedHTTPSValidation().urlEncodingEnabled(false)
                .body(bodyContent).delete(deleteUrl);
    }

    /**** Status code validation checkers **/

    public void validateGetResponseCode(Response response) {
        response.then().assertThat().statusCode(GET_RESPONSE_CODE);
    }

    public void validatePostResponseCode(Response response) {
        printResponse(response);
        response.then().assertThat().statusCode(POST_RESPONSE_CODE);
    }

    public void validatePutResponseCode(Response response) {
        response.then().assertThat().statusCode(PUT_RESPONSE_CODE);
    }

    public void validateDeleteResponseCode(Response response) {
        response.then().assertThat().statusCode(DELETE_RESPONSE_CODE);
    }

    /************ Response Extraction methods************/

    public String getResponseBody(Response response) {
        return response.getBody().asString();
    }

    public int getResponseStatusCode(Response response) {
        return response.getStatusCode();
    }

    public String getResponseContentType(Response response) {
        return response.getContentType();
    }

    public String getResponseSessionId(Response response) {
        return response.getSessionId();
    }

    public List<Header> getResponseHeaders(Response response) {
        return response.getHeaders().asList();
    }

    public String getResponseHeader(Response response, String headerKey) {
        return response.getHeader(headerKey);
    }

    public Map<String, String> getResponseCookie(Response response) {
        return response.cookies();
    }

    public String getResponseCookie(Response response, String cookieName) {
        return response.getCookie(cookieName);
    }

    /**************** Print response ******************/

    public void printResponse(Response response){
        _logger.info("******Response details starts*******");
        _logger.info("Status code :" + response.getStatusCode());
        _logger.info("Body :" + response.getBody());
        _logger.info("Pretty string :" + response.asPrettyString());
        _logger.info("String :" + response.toString());
        _logger.info("******Response details ends*******");
    }


}
