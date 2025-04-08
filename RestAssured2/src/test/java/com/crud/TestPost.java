package com.crud;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import Utility.PropertiesUtil;

import static org.hamcrest.MatcherAssert.assertThat;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.config.LogConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import org.hamcrest.Matcher;


public class TestPost {
	
	String key=PropertiesUtil.getApiKey();
	
	@BeforeClass
	public void beforClassSetp() {
		
		RequestSpecBuilder req=new RequestSpecBuilder().
				setBaseUri("https://api.getpostman.com")
				.setContentType(ContentType.JSON).
				addHeader("X-API-Key",key)
				.log(LogDetail.ALL);
		
		RestAssured.requestSpecification=req.build();
		
		ResponseSpecBuilder res=new ResponseSpecBuilder()
				.expectStatusCode(200).expectContentType(ContentType.JSON)
				.log(LogDetail.ALL);
		
		RestAssured.responseSpecification=res.build();
		
		
		
	}
	
	@Test
	public void postMethod() {
		String payload="{\r\n"
				+ "\"workspace\": {\r\n"
				+ "            \"name\": \"Workspace Using Post Method via RestAssured\",\r\n"
				+ "            \"type\": \"personal\",\r\n"
				+ "            \"visibility\": \"personal\"\r\n"
				+ "            }\r\n"
				+ "        \r\n"
				+ "}";
		given().
		body(payload).
		when().post("/workspaces").
		then().
		assertThat().
		body("workspace.name",equalTo("Workspace Using Post Method via RestAssured"),
				"workspace.id",matchesPattern("^[a-z0-9]{8}-[a-z0-9]{4}-[a-z0-9]{4}-[a-z0-9]{4}-[a-z0-9]{12}$")
				
				);
		
	}
	
	@Test
	public void postMethodNonBDD() {
		String payload="{\r\n"
				+ "\"workspace\": {\r\n"
				+ "            \"name\": \"Workspace 3\",\r\n"
				+ "            \"type\": \"personal\",\r\n"
				+ "            \"visibility\": \"personal\"\r\n"
				+ "            }\r\n"
				+ "        \r\n"
				+ "}";
	
	Response resNonbdd=with().
			body(payload).
			post("/workspaces");
	
	JsonPath jsonPath = resNonbdd.jsonPath();   // resNonbdd is the response body

	String workspaceName = jsonPath.getString("workspace.name");

	assertThat(workspaceName,equalTo("Workspace 3"));
	
	}
	
}
