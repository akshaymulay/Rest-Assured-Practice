package com.payload.as.file;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import Utility.PropertiesUtil;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.matchesPattern;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;

public class TestJsonArrayList {

	String key=PropertiesUtil.getApiKey();
	
	@BeforeClass
	public void beforClassSetp() {
		
		RequestSpecBuilder req=new RequestSpecBuilder().
				setBaseUri("https://349db900-a5d9-40c5-b6ae-d309b7b84b4a.mock.pstmn.io/")
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
	public void JsonTestCase() {
		
		HashMap<String,Object> idDetailFirst=new HashMap<String,Object>();
		idDetailFirst.put("id","1001");
		idDetailFirst.put("Type","None");
		
		HashMap<String,Object> idDetailSecond=new HashMap<String,Object>();
		idDetailSecond.put("id","1022");
		idDetailSecond.put("Type","Main");
		
		  List<Map<String, Object>> workspaces = new ArrayList<Map<String, Object>>();
		  workspaces.add(idDetailFirst);
		  workspaces.add(idDetailSecond);
		  
		
		given().
		body(workspaces).
		when().
		post("/post").
		then().
		log().all().
		assertThat().
		body("msg",equalTo("Success"));
	
		
	}
	
	
	
}
