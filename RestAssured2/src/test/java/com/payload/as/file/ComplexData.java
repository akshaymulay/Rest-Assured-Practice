package com.payload.as.file;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import Utility.PropertiesUtil;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;

public class ComplexData {
	
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
		
		
		//if payload has array [] use array list
		
		List<Integer>idArraylist=new ArrayList<Integer>();
		idArraylist.add(5);
		idArraylist.add(9);
		
		//now array is associated to a key: and key has value which is array
		
		HashMap<String,Object>batterHashmapSecond=new HashMap<String,Object>();
		
		batterHashmapSecond.put("id", idArraylist);
		batterHashmapSecond.put("type", "Chocolate");
		
		HashMap<String,Object>batterHashmapFirst=new HashMap<String,Object>();
		
		batterHashmapFirst.put("id","1001");
		batterHashmapFirst.put("type","Regular");
		
		//now if structure is like [ {} {} ] i.e json array and then object inside and object has type of Map<String,Object>
		//then its arraylist with type of hashmap
		
		List<HashMap<String,Object>>batterArraylist=new  ArrayList<HashMap<String,Object>>();
		batterArraylist.add(batterHashmapFirst);
		batterArraylist.add(batterHashmapSecond);
		
		// now batters is json object with json array {[{}}} Map List of map
		
		HashMap<String,List<HashMap<String,Object>>>Batters=new HashMap<String,List<HashMap<String,Object>>>();
		Batters.put("batter",batterArraylist);
		
		
		
		List<String>typeArraylist=new ArrayList<String>();
		typeArraylist.add("typeOne");
		typeArraylist.add("typeTwo");
		
		HashMap<String,Object>toppingHashmapTwo=new HashMap<String,Object>();
		toppingHashmapTwo.put("id","5002");
		toppingHashmapTwo.put("type", typeArraylist);
		
		HashMap<String,Object>toppingHashmapone=new HashMap<String,Object>();
		toppingHashmapTwo.put("id","5001");
		toppingHashmapTwo.put("type", "None");
		
		List<Map<String,Object>>toppingArraylist=new  ArrayList<Map<String,Object>>();
		toppingArraylist.add(toppingHashmapone);
		toppingArraylist.add(toppingHashmapTwo);
		
		
		
		
		HashMap<String,Object>MainHashmap=new HashMap<String,Object>();
		
		MainHashmap.put("id","0001");
		MainHashmap.put("type","donut");
		MainHashmap.put("name","Cake");
		MainHashmap.put("ppu",0.55);
		MainHashmap.put("batters",Batters);
		MainHashmap.put("topping",toppingArraylist);
		
		given().
		body(MainHashmap).
		when().
		post("/ComplexData").
		then().
		log().all().
		assertThat().
		body("msg",equalTo("Success"));
	
		
	}
	
	

}
