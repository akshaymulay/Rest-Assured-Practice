package com.params;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;

public class SingleParams {
	
	
	@Test
	public void test() {
		
		given().
		baseUri("https://postman-echo.com").
		queryParam("Name","Akshay").
		queryParam("Surname","Mulay").
		//param("Name","Akshay").
		when().
		get("/get").
		then().
		log().all().
		assertThat().
		statusCode(200);
		

}
	
	
	@Test
	public void test2() {
		
		HashMap<String,String>QueryParams=new HashMap<String,String>();
		QueryParams.put("Name","Vishal");
		QueryParams.put("Surname","Mulay");
		
		given().
		baseUri("https://postman-echo.com").
		queryParams(QueryParams).
		when().
		get("/get").
		then().
		log().all().
		assertThat().
		statusCode(200);
		

}
	
	
	
	@Test
	public void test3() {
		
		given().
		baseUri("https://postman-echo.com").
		queryParam("Name","Akshay,Vishal,Aks,AK").
		//param("Name","Akshay").
		when().
		get("/get").
		then().
		log().all().
		assertThat().
		statusCode(200);
		

}
	
	
	@Test
public void pathParams() {
		
		given().
		baseUri("https://reqres.in/api").
		pathParam("id","2").
		
		when().
		get("/users/{id}").
		then().
		log().all().
		assertThat().
		statusCode(200);
		

}
	
	
	@Test
public void MultiPartFormData() {
		
		given().
		baseUri("https://postman-echo.com").
		multiPart("Foo", "val1").
		multiPart("Foo", "val2").
		multiPart("Foo2", "val3").
		when().
		post("/post").
		then().
		log().all().
		assertThat().
		statusCode(200);
		

}
	
	
	@Test
	public void MultiPartFormDataWithFile() {
		
		String data="{\r\n"
				+ "\r\n"
				+ "\"name\":\"File.txt\"\r\n"
				+ "\"id:\"1243\",\r\n"
				+ "\"parent\":{\r\n"
				+ "\r\n"
				+ "\r\n"
				+ "\"type\":\".txt\",\r\n"
				+ "\"size\":\"3mb\"\r\n"
				+ "}\r\n"
				+ "\r\n"
				+ "}";
			
			given().
			baseUri("https://postman-echo.com").
			multiPart("file", new File("src/main/resources/temp.txt")).
			multiPart("data", data,"application/json").log().all().
			when().
			post("/post").
			then().
			log().all().
			assertThat().
			statusCode(200);
			

	}
	
	
	@Test
	public void testFileDownload() throws IOException {
		
		byte[] bytes=given().
		baseUri("https://raw.githubusercontent.com").
		when().
		get("/appium-boneyard/sample-code/refs/heads/master/sample-code/apps/ApiDemos/bin/ApiDemos-debug.apk").
		then().
		log().all().
		assertThat().
		statusCode(200).
		extract().
		asByteArray();
		
		OutputStream os=new FileOutputStream(new File("src/main/resources/ApiDemos-debug.apk"));
		os.write(bytes);
		os.close();

}
		
	
	@Test
	public void UrlEncoding() {
		
		given()
		.config(RestAssured.config().encoderConfig(EncoderConfig.encoderConfig()
                        .appendDefaultContentCharsetToContentTypeIfUndefined(false))).
		baseUri("https://postman-echo.com").
		formParam("file", "a.txt").
		when().
		post("/post").
		then().
		log().all().
		assertThat().
		statusCode(200);
		

}
	
	
	
	
	
}