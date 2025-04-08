package com.payload.as.file;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.matchesPattern;

import java.io.File;
import java.util.HashMap;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import Utility.PropertiesUtil;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;

public class TestFile {
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
		File payload=new File("Resources/CreateWorkspacePayload.json");
		given().
		body(payload).
		when().post("/workspaces").
		then().
		assertThat().
		body("workspace.name",equalTo("Workspace Using json file Method"),
				"workspace.id",matchesPattern("^[a-z0-9]{8}-[a-z0-9]{4}-[a-z0-9]{4}-[a-z0-9]{4}-[a-z0-9]{12}$")
				
				);
		
	}
	
	@Test
	public void postMethodAsMap() {
		
		HashMap<String,Object> mainObject=new HashMap<String,Object>();
		
		HashMap<String,Object> nestedObject=new HashMap<String,Object>();
		nestedObject.put("name", "Workspace Using map");
		nestedObject.put("type", "personal");
		nestedObject.put("visibility", "personal");
		
		mainObject.put("workspace", nestedObject);
		
		given().
		body(mainObject).
		when().post("/workspaces").
		then().
		assertThat().
		body("workspace.name",equalTo("Workspace Using json file Method"),
				"workspace.id",matchesPattern("^[a-z0-9]{8}-[a-z0-9]{4}-[a-z0-9]{4}-[a-z0-9]{4}-[a-z0-9]{12}$")
				
				);
		
	}
	

}
