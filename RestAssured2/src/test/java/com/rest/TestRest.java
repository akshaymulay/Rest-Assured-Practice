package com.rest;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import Utility.PropertiesUtil;

import static org.hamcrest.MatcherAssert.assertThat;
import io.restassured.config.LogConfig;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class TestRest {
	String key=PropertiesUtil.getApiKey();
	
	RequestSpecification rs;
	
	@BeforeClass
	public void beforeClass() {
		rs=with().baseUri("https://api.getpostman.com").
				header("X-API-Key",key);
				
	}
	
	@Test
	public void withoutBdd() {
		Response res=rs.get("/workspaces");
		assertThat(res.statusCode(),is(equalTo(200)));
		
	
	}
	

	@Test
	public void test() {
		
		given(rs).
		when().
		get("/workspaces").
		then().
		log().all().
		assertThat().statusCode(200).
		statusCode(200).
		body("workspaces[0].name",is(equalTo("Test Automation Workspace")),
				
				
				"workspaces.name",hasItems("Test Automation Workspace","Workspace Using Post Method"),
				"workspaces.type",hasItems("personal","personal"),
				"workspaces.size()",equalTo(2)
				
				);
		

		
		
	}
	
	@Test
	public void extracResponse() {
		
		Response rs=given().
		baseUri("https://api.getpostman.com").
		header("X-API-Key",key).
		log().headers().
		//log().all().
		when().
		get("/workspaces").
		then().
		log().all().
		statusCode(200)
		.extract().
		response();	
		System.out.println(rs.asString());
	}
	
	
	@Test
	public void extracSingleResponse() {
		
		Response rs=given().
		baseUri("https://api.getpostman.com").
		header("X-API-Key",key).
		when().
		get("/workspaces").
		then().
		statusCode(200)
		.extract().
		response();	
		System.out.println(rs.path("workspaces[1].name"));
		
		JsonPath js=new JsonPath(rs.asString());
		System.out.println(js.getString("workspaces[1].type"));
	}
	
	@Test
	public void errorLog() {
		
		given().
		baseUri("https://api.getpostman.com").
		header("X-API-Key",key).
		when().
		get("/workspaces").
		then().
		log().ifError().
		statusCode(200);
	}
	
	@Test
	public void failedCase() {
		
		Set<String> list=new HashSet<String>();
		list.add("X-API-Key");
		list.add("Accept");
		
		
		given().
		baseUri("https://api.getpostman.com").
		header("X-API-Key",key).
		config(config.logConfig(LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails())).
		config(config.logConfig(LogConfig.logConfig().blacklistHeader("X-API-Key"))).
		config(config.logConfig(LogConfig.logConfig().blacklistHeaders(list))).
		log().all().
		when().
		get("/workspaces").
		then().
		//log().ifValidationFails().
		statusCode(200);
	}
	
	

	
	
	
	
	@Test
	public void MockHead() {
		
		HashMap<String,String>headers=new HashMap<String,String>();
		headers.put("HeaderName","Value2");
		
		//Header header=new Header("HeaderName","Value2");
		//Header matchHeader=new Header("x-mock-match-request-header","HeaderName");
		
		//Headers listHeader=new Headers(header,matchHeader);
		given().
		baseUri("https://349db900-a5d9-40c5-b6ae-d309b7b84b4a.mock.pstmn.io").
		headers(headers).
		when().
		get("/get").
		then().
		log().all().
		assertThat().
		statusCode(200). 
		header("ResponseHeader","Value2");
		//use headers to assert multiple headers
		
		//use extract().header() to extract the headers
		//Create object Headers header=given() upto extract().header()
		//header.get(key)
		//extract multiple then use for(Header head:header){head.getName()} 
	
		//header("HeaderName","Value2").
				//header("x-mock-match-request-header","HeaderName");
		
		//if the header has multivalues then we can use multiValueHeader
		//we can create Header for such values and pass it to Headers
	
}
	
	
}
