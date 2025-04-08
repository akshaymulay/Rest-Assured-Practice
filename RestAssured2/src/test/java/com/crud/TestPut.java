package com.crud;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.matchesPattern;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import Utility.PropertiesUtil;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;

public class TestPut {

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
	public void putMethod() {
		String payload="{\r\n"
				+ "\"workspace\": {\r\n"
				+ "            \"name\": \"Test Automation Workspace via put\",\r\n"
				+ "            \"type\": \"personal\",\r\n"
				+ "            \"visibility\": \"personal\"\r\n"
				+ "            }\r\n"
				+ "        \r\n"
				+ "}";
		given().
		body(payload).
		when().put("/workspaces/0bce47e3-240f-4a2e-b3d9-583c7ffb8ded").
		then().
		assertThat().
		body("workspace.name",equalTo("Test Automation Workspace via put"),
				"workspace.id",matchesPattern("^[a-z0-9]{8}-[a-z0-9]{4}-[a-z0-9]{4}-[a-z0-9]{4}-[a-z0-9]{12}$")
				
				);
		
	}
	
	
	
}
