package com.SriGanesh.steps;


import java.io.File;

import java.io.FileInputStream;

import java.io.FileOutputStream;

import java.io.FileReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import java.sql.Connection;

import java.sql.DriverManager;

import java.sql.ResultSet;

import java.sql.ResultSetMetaData;

import java.sql.SQLException;

import java.sql.Statement;

import java.util.List;

import java.util.Map;

 

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import org.apache.poi.ss.usermodel.Sheet;

import org.apache.poi.ss.usermodel.Workbook;

import org.apache.poi.ss.usermodel.WorkbookFactory;

import org.jsoup.helper.HttpConnection.Response;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonGenerator;

import com.fasterxml.jackson.core.JsonPointer;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.fasterxml.jackson.core.JsonToken;

import com.fasterxml.jackson.core.ObjectCodec;

import com.fasterxml.jackson.core.JsonParser.NumberType;

import com.fasterxml.jackson.databind.*;

import com.fasterxml.jackson.databind.jsontype.TypeSerializer;

import com.fasterxml.jackson.databind.node.ArrayNode;

import com.fasterxml.jackson.databind.node.JsonNodeType;

import com.fasterxml.jackson.databind.node.ObjectNode;

import com.google.common.base.Optional;

import com.google.gson.JsonElement;

import com.google.gson.JsonObject;

import com.google.gson.JsonParser;

import com.google.gson.JsonStreamParser;

import com.google.gson.stream.JsonReader;

 

import io.cucumber.core.gherkin.messages.internal.gherkin.internal.com.eclipsesource.json.Json;

import io.cucumber.core.gherkin.messages.internal.gherkin.internal.com.eclipsesource.json.JsonValue;

import io.restassured.RestAssured;

import io.restassured.http.Method;

import io.restassured.path.json.JsonPath;

import io.restassured.specification.RequestSpecification;

 

public class rest {
	
	       public static JsonNode rootNode;

	       static ObjectMapper mapper = new ObjectMapper();

	       static ObjectReader reader = mapper.reader();
	    @Test

	       public void resttest() throws  IOException, SQLException, ClassNotFoundException, InvalidFormatException {

	    	   
	            
	    	   	 Workbook wb = WorkbookFactory.create(new File("rest.xlsx"));

	             Sheet sh = wb.getSheetAt(0);

	             int rowcount = sh.getLastRowNum();

	             int colcount = sh.getRow(0).getLastCellNum();

	             for(int i =1;i<=rowcount && sh.getRow(i).getCell(0) != null;i++)

	             {  
	            	 

	                    JsonElement jsonElement = new JsonParser().parse(new FileReader("testing.json"));

	                    JsonObject json = jsonElement.getAsJsonObject();

	                    rootNode = reader.readTree(json.toString());

	                    for(int j=1;j<colcount;j++)

	                    {

	                          String value = sh.getRow(i).getCell(j).getStringCellValue();

	                          String[] avalue = value.split(";");

	                          if(avalue.length==3)

	                          {

	 

	                                 modifynode(avalue[0],avalue[1], avalue[2]);

	                          }      else

	                          {

	                                 modifynode(avalue[0],avalue[1]);                   

	                          }

	                    }

	                    System.out.println(rootNode.toPrettyString());

	                    RestAssured.baseURI = "https://reqres.in/";

	                    RequestSpecification request = RestAssured.given();

	                    request.contentType("application/json");

	                    request.body(rootNode.toString());

	                    io.restassured.response.Response response = request.post( "/api/users");

	                    System.out.println("The status received: " + response.statusLine());

	                   

	             }

	 

	       }

	       public static void modifynode(String path, String element, String value) throws JsonMappingException, JsonProcessingException {

	             int index =0;

	             String anode ="";

	             JsonNode temp = rootNode;

	             for(String node:path.split(",")) {

	                    if(node.contains("[")) {

	                          anode = node.split("\\[")[0];

	                          index = Integer.valueOf(node.split("\\[")[1].replace("]", ""));

	                          // System.out.println(temp.findPath(anode).get(index));

	                          if(temp.findPath(anode).get(index) == null){

	                                 if(temp.findPath(anode).get(0) == null) {                                   

	                                        ((ObjectNode)temp).putArray(anode).addObject();

	                                       temp = temp.get(anode).get(0);}

	                                 else {

	                                        //System.out.println("path is" +temp.findPath(anode).get(1));

	                                       ((ArrayNode) temp.findPath(anode)).addObject();

	                                        //System.out.println("path is" +temp.findPath(anode).get(1));

	                                       temp = temp.get(anode).get(index);

	                                        //System.out.println(rootNode);

	                                 }

	                                 //System.out.println(temp.get(anode).toString()+temp+ rootNode.toString());

	                          }

	                          else {

	                                 temp = temp.get(anode).get(index);}

	                    }else {

	                          if(temp.path(node).isMissingNode()){

	                                 ((ObjectNode)temp).putObject(node);}

	                          temp = temp.get(node);

	                    }

	             }

	             //     System.out.println(rootNode.toString());       

	             if(value.equals("removetag")) {

	                    ((ObjectNode) temp).remove(element);

	             }else {

	                    //System.out.println(temp.getNodeType().toString());

	                    if(!temp.getNodeType().toString().equals("OBJECT")) {

	                          if(temp.get(index).isObject()) {

	                                 ((ObjectNode) temp.get(index)).put(element, value);

	                          }else {

	                                 //System.out.println("here");

	                                 ((ObjectNode) temp.get(index)).put(element, value);}

	                    }else {

	                          ((ObjectNode) temp).put(element, value);}

	             }

	       }

	       public static void modifynode(String element, String value) {

	 

	             JsonNode temp = rootNode;

	 

	             if(value.equals("removetag")) {

	                    ((ObjectNode) temp).remove(element);

	             }else {

	                    ((ObjectNode) temp).put(element, value);}

	 

	 

	       }

	 

	}

	 

	 


