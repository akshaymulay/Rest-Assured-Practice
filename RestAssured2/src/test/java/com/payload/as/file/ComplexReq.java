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

public class ComplexReq {
	
	String key=PropertiesUtil.getApiKey();
	
	

	@BeforeClass
	public void beforClassSetp() {
		
	System.out.println(key);
		
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
		
		
		//[] use arraylist and it has key rgba so create array list and assign that to key
		
		List<Integer>rgbaArraylistFirst=new ArrayList<Integer>();
		rgbaArraylistFirst.add(255);
		rgbaArraylistFirst.add(255);
		rgbaArraylistFirst.add(255);
		rgbaArraylistFirst.add(1);
		
		//now array is associated to a key: and key has value which is array
		
		HashMap<String,Object>codeHashmapOne=new HashMap<String,Object>();
		
		codeHashmapOne.put("code", rgbaArraylistFirst);
		codeHashmapOne.put("hex","#000");
		
		//Normal Key values
		
		HashMap<String,Object>normalHashmapOne=new HashMap<String,Object>();
		
		normalHashmapOne.put("color","black");
		normalHashmapOne.put("category","hue");
		normalHashmapOne.put("type","primary");

		
		//[] use arraylist and it has key rgba so create array list and assign that to key
		
		List<Integer>rgbaArraylistSecond=new ArrayList<Integer>();
		rgbaArraylistSecond.add(0);
		rgbaArraylistSecond.add(0);
		rgbaArraylistSecond.add(0);
		rgbaArraylistSecond.add(1);
		
		//now array is associated to a key: and key has value which is array
		HashMap<String,Object>codeHashmapTwo=new HashMap<String,Object>();
		
		codeHashmapTwo.put("code", rgbaArraylistSecond);
		codeHashmapTwo.put("hex","#FFF");

		//Normal Key values

		HashMap<String,Object>normalHashmapTwo=new HashMap<String,Object>();

		normalHashmapTwo.put("color","white");
		normalHashmapTwo.put("category","value");


		//Now object start with colors which has [{}],[{}] i.e list key value type

		List<HashMap<String,Object>>MainList=new ArrayList <HashMap<String,Object>>();
		MainList.add(normalHashmapOne);
		MainList.add(codeHashmapOne);
		MainList.add(normalHashmapTwo);
		MainList.add(codeHashmapTwo);



		
		
		
		
		
		
		
		
		
		
		given().
		body(MainList).
		when().
		post("/ComplexReq").
		then().
		log().all().
		assertThat().
		body("msg",equalTo("Data Added succesfully"));
	
		
	}

}
