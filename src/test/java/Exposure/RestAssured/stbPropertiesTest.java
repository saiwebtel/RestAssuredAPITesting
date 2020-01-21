package Exposure.RestAssured;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import RestAPIHelper.RestUtil;
import XMLUtils.XMLUtil;
import base.Testbase;
import dbConnection.DataBaseConnection;

public class stbPropertiesTest extends Testbase {

	@BeforeMethod
	public void beforeMethod()
	{
		RestAssured.basePath = "/broker/bta/setSTBProperties";

	}
	@Test(invocationCount=1,priority=1)
	public void setSTBProperties_BEP1423001() throws JAXBException, ClassNotFoundException, SQLException, ParserConfigurationException, TransformerException, IOException 
	{
		System.out.println("CASE:1===================STB PROPERTIES TESTS==========================");
		XMLUtil testData = new XMLUtil();
		Map<String,String> ListData = DataBaseConnection.executeSelectQuery(SqlQueries.Queries.macEquipmentQuery, tmdbUserName, tmdbPassword);
		System.out.println("REMINDER_LIST_DATA"+ListData);
		String postBody=testData.xmlConverter();
		System.out.println("=======>>>>>>>>>"+postBody);
		//QueryParams
		Map<String, String> queryParams = new HashMap<String, String>();
		queryParams.put("MAC", ListData.get("MACADDRESS"));
		queryParams.put("InterfaceVersion", "5.2.0");
		String equipmentID=ListData.get("EQUIPMENTID");
		System.out.println("EQU"+equipmentID);
		
		//Sending Request
		Response response =  RestUtil.sendPostAPI(postBody, "application/xml", queryParams);
		
		Map<String,String> ListData1 = DataBaseConnection.executeSelectQuery("select * from SETTOPBOXPROPERTIES where EQUIPMENTID ='"+ equipmentID +"'", tmdbUserName, tmdbPassword);
		String equipmentIDDB=ListData1.get("EQUIPMENTID");
		//Validating Response
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(equipmentID, equipmentIDDB);
		System.out.println(response.asString());
		DataBaseConnection.executeSelectQuery("delete from SETTOPBOXPROPERTIES where EQUIPMENTID ='"+ equipmentID +"'", tmdbUserName, tmdbPassword);
		}
		
	}
