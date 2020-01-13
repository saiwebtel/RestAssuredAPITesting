package Exposure.RestAssured;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.matcher.RestAssuredMatchers.matchesXsdInClasspath;
import static org.hamcrest.Matchers.equalTo;
import groovy.ui.SystemOutputInterceptor;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.matcher.RestAssuredMatchers;
import io.restassured.response.Response;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.*;

import javax.xml.bind.JAXBException;

import org.testng.annotations.Test;

import testData.TestDataCreation;
import dbConnection.DataBaseConnection;
import RestAPIHelper.RestUtil;
import SqlQueries.Queries;
import XMLUtils.XMLUtil;
import base.Testbase;
import model.AddReminder;
import model.BTA;

public class addReminderTests extends Testbase {
	static String requestBody1 = null;
	TestDataCreation addReminderData;
	Map<String,String> reminderListData;
	
	@BeforeTest
	public void printEX()
	{
		System.out.println("Only Once it will execute");
	}
	@BeforeMethod
	public void beforeTest() throws SQLException
	{
		RestAssured.basePath = "/broker/bta/addReminder";
		addReminderData = new TestDataCreation();
		reminderListData = DataBaseConnection.executeSelectQuery(SqlQueries.Queries.AddReminderQuery,tmdbUserName, tmdbPassword);
	}
	@Test(priority=1)
	public void addReminderInsertionWithInterfaceVersion520_BEP14470001() throws JAXBException, FileNotFoundException,ClassNotFoundException, SQLException 
	{
		System.out.println("CASE:1=========================================================");
		String postBody = addReminderData.createAddReminderPOSTBody(reminderListData);
		System.out.println(postBody);
		
		//QueryParams
		Map<String, String> queryParams = new HashMap<String, String>();
		queryParams.put("MAC", reminderListData.get("MACADDRESS"));
		queryParams.put("InterfaceVersion", "5.2.0");
		
		//Sending Request
		Response response =  RestUtil.sendPostAPI(postBody, "application/xml", queryParams);
		//Validating Response
		Assert.assertEquals(response.getStatusCode(), 200);
		System.out.println(response.asString());
		Map<String, String> queryParams1=addReminderData.getDataFromDB(Queries.getReminderQuery);
		String ID=queryParams1.get("REMINDERID");
		String ReminderType=queryParams1.get("REMINDERTYPE");
		System.out.println("ID id--------------->"+ID);
		Assert.assertEquals(response.getBody().xmlPath().get("BTAResponse.ID"),ID);
		Assert.assertEquals("Reminder",ReminderType);
		//response.then().body(RestAssuredMatchers.matchesXsd("C:\\Automation\\XSD_5.2\\BEP5.2Docs\\BTA\\BTAResponseAddUserWishList.xsd"));
		//String wishListID = response.xmlPath("")
	}
	 @Test(priority=2)
	public void addReminderInsertionWithInterfaceVersion420_BEP14470004() throws JAXBException, FileNotFoundException,ClassNotFoundException, SQLException 
	{
		 System.out.println("CASE:2=========================================================");
		System.out.println("REMINDER_LIST_DATA"+reminderListData);
		String postBody = addReminderData.createAddReminderPOSTBody(reminderListData);
		System.out.println(postBody);
		//QueryParams
		Map<String, String> queryParams = new HashMap<String, String>();
		queryParams.put("MAC", reminderListData.get("MACADDRESS"));
		queryParams.put("InterfaceVersion", "4.2.0");
		
		//Sending Request
		Response response =  RestUtil.sendPostAPI(postBody, "application/xml", queryParams);
		//Validating Response
		Assert.assertEquals(response.getStatusCode(), 200);
		System.out.println(response.asString());
		Map<String, String> queryParams1=addReminderData.getDataFromDB(Queries.getReminderQuery);
		String ID=queryParams1.get("REMINDERID");
		System.out.println("ID id--------------->"+ID);
		Assert.assertEquals(response.getBody().xmlPath().get("BTAResponse.ID"),ID);
		//response.then().body(RestAssuredMatchers.matchesXsd("C:\\Users\\vinay.singh.IND-DEL\\RestAssuredFrameWork\\src\\resources\\BTAResponseAddReminder.xsd"));
		//String wishListID = response.xmlPath("")
		
	}
	 @Test(priority=3)
		public void addReminderInsertionWithoutInterfaceVersion_BEP14470002() throws JAXBException, FileNotFoundException,ClassNotFoundException, SQLException 
		{
		 System.out.println("CASE:3=========================================================");	
			System.out.println("REMINDER_LIST_DATA"+reminderListData);
			String postBody = addReminderData.createAddReminderPOSTBody(reminderListData);
			System.out.println(postBody);
			//QueryParams
			Map<String, String> queryParams = new HashMap<String, String>();
			queryParams.put("MAC", reminderListData.get("MACADDRESS"));
			//queryParams.put("InterfaceVersion", "4.2.0");			
			//Sending Request
			Response response =  RestUtil.sendPostAPI(postBody, "application/xml", queryParams);
			//Validating Response
			Assert.assertEquals(response.getStatusCode(), 200);
			Assert.assertEquals(response.getBody().xmlPath().get("BTAResponse.ERROR.ErrorDescription"),"Mandatory Parameter Interface Version missing");
		}
	 @Test(priority=4)
		public void addReminderInsertionWithInterfaceVersionLessThan420_BEP14470003() throws JAXBException, FileNotFoundException,ClassNotFoundException, SQLException 
		{
		 System.out.println("CASE:4=========================================================");
			System.out.println("REMINDER_LIST_DATA"+reminderListData);
			String postBody = addReminderData.createAddReminderPOSTBody(reminderListData);
			System.out.println(postBody);
			//QueryParams
			Map<String, String> queryParams = new HashMap<String, String>();
			queryParams.put("MAC", reminderListData.get("MACADDRESS"));
			queryParams.put("InterfaceVersion", "4.1.0");			
			//Sending Request
			Response response =  RestUtil.sendPostAPI(postBody, "application/xml", queryParams);
			//Validating Response
			Assert.assertEquals(response.getStatusCode(), 200);
			Assert.assertEquals(response.getBody().xmlPath().get("BTAResponse.ERROR.ErrorDescription"),"Invalid Interface version");
		}
	 @Test(priority=5)
		public void addReminderInsertionWithoutMac_BEP14470005() throws JAXBException, FileNotFoundException,ClassNotFoundException, SQLException 
		{
		 System.out.println("CASE:5=========================================================");
			System.out.println("REMINDER_LIST_DATA"+reminderListData);
			String postBody = addReminderData.createAddReminderPOSTBody(reminderListData);
			System.out.println(postBody);
			//QueryParams
			Map<String, String> queryParams = new HashMap<String, String>();
			//queryParams.put("MAC", reminderListData.get("MACADDRESS"));
			queryParams.put("InterfaceVersion", "4.1.0");			
			//Sending Request
			Response response =  RestUtil.sendPostAPI(postBody, "application/xml", queryParams);
			//Validating Response
			Assert.assertEquals(response.getStatusCode(), 200);
			Assert.assertEquals(response.getBody().xmlPath().get("BTAResponse.ERROR.ErrorDescription"),"Request header for remote ipaddress does not exist or it may be empty");
		}
	 @Test(priority=6)
		public void addReminderInsertionWitInvalidMac_BEP14470006() throws JAXBException, FileNotFoundException,ClassNotFoundException, SQLException 
		{
		 	System.out.println("CASE:6=========================================================");	
			System.out.println("REMINDER_LIST_DATA"+reminderListData);
			String postBody = addReminderData.createAddReminderPOSTBody(reminderListData);
			System.out.println(postBody);
			//QueryParams
			Map<String, String> queryParams = new HashMap<String, String>();
			queryParams.put("MAC", reminderListData.get("MACADDRESS")+"PPP");
			System.out.println(reminderListData.get("MACADDRESS")+""+"PPP");
			queryParams.put("InterfaceVersion", "4.2.0");			
			//Sending Request
			Response response =  RestUtil.sendPostAPI(postBody, "application/xml", queryParams);
			//Validating Response
			Assert.assertEquals(response.getStatusCode(), 200);
			Assert.assertEquals(response.getBody().xmlPath().get("BTAResponse.ERROR.ErrorDescription"),"Invalid MAC.");
		}
	 @Test(priority=7)
		public void addReminderInsertionWithBlankValueForReminderType_BEP14470007() throws JAXBException, FileNotFoundException,ClassNotFoundException, SQLException 
		{
			System.out.println("CASE:7=========================================================");
			System.out.println("REMINDER_LIST_DATA"+reminderListData);
			String postBody = addReminderData.createAddReminderPOSTBody(reminderListData);
			String FinalPostBody=postBody.replaceAll("<ReminderType>Reminder</ReminderType>", "<ReminderType></ReminderType>");
			System.out.println("FINAL BODY IS ====="+FinalPostBody);
			//QueryParams
			Map<String, String> queryParams = new HashMap<String, String>();
			queryParams.put("MAC", reminderListData.get("MACADDRESS"));
			queryParams.put("InterfaceVersion", "5.2.0");
			
			//Sending Request
			Response response =  RestUtil.sendPostAPI(FinalPostBody, "application/xml", queryParams);
			//Validating Response
			//Validating Response
			Assert.assertEquals(response.getStatusCode(), 200);
			Assert.assertEquals(response.getBody().xmlPath().get("BTAResponse.ERROR.ErrorDescription"),"Reminder Type tag is either missing or empty");
		}
	 @Test(priority=8)
		public void addReminderInsertionWithoutReminderTag_BEP14470008() throws JAXBException, FileNotFoundException,ClassNotFoundException, SQLException 
		{
			System.out.println("CASE:8=========================================================");
			System.out.println("REMINDER_LIST_DATA"+reminderListData);
			String postBody = addReminderData.createAddReminderPOSTBody(reminderListData);
			String FinalPostBody=postBody.replaceAll("<ReminderType>Reminder</ReminderType>", "");
			System.out.println("FINAL BODY IS ====="+FinalPostBody);
			//QueryParams
			Map<String, String> queryParams = new HashMap<String, String>();
			queryParams.put("MAC", reminderListData.get("MACADDRESS"));
			queryParams.put("InterfaceVersion", "5.2.0");
			
			//Sending Request
			Response response =  RestUtil.sendPostAPI(FinalPostBody, "application/xml", queryParams);
			//Validating Response
			//Validating Response
			Assert.assertEquals(response.getStatusCode(), 200);
			Assert.assertEquals(response.getBody().xmlPath().get("BTAResponse.ERROR.ErrorDescription"),"Reminder Type tag is either missing or empty");
		}
	 @Test(priority=9)
		public void addReminderInsertionWithBlankValueforMinsBeforeStart_BEP14470009() throws JAXBException, FileNotFoundException,ClassNotFoundException, SQLException 
		{
			System.out.println("CASE:9=========================================================");
			System.out.println("REMINDER_LIST_DATA"+reminderListData);
			String postBody = addReminderData.createAddReminderPOSTBody(reminderListData);
			String FinalPostBody=postBody.replaceAll("<MinsBeforeStart>8</MinsBeforeStart>", "<MinsBeforeStart></MinsBeforeStart>");
			System.out.println("FINAL BODY IS ====="+FinalPostBody);
			//QueryParams
			Map<String, String> queryParams = new HashMap<String, String>();
			queryParams.put("MAC", reminderListData.get("MACADDRESS"));
			queryParams.put("InterfaceVersion", "5.2.0");
			
			//Sending Request
			Response response =  RestUtil.sendPostAPI(FinalPostBody, "application/xml", queryParams);
			//Validating Response
			//Validating Response
			Assert.assertEquals(response.getStatusCode(), 200);
			Assert.assertEquals(response.getBody().xmlPath().get("BTAResponse.ERROR.ErrorDescription"),"Mins Before Start tag is either missing/empty/incorrect");
		}
	 @Test(priority=10)
		public void addReminderInsertionWithoutMinsBeforeStartTag_BEP144700010() throws JAXBException, FileNotFoundException,ClassNotFoundException, SQLException 
		{
			System.out.println("CASE:10=========================================================");
			System.out.println("REMINDER_LIST_DATA"+reminderListData);
			String postBody = addReminderData.createAddReminderPOSTBody(reminderListData);
			String FinalPostBody=postBody.replaceAll("<MinsBeforeStart>8</MinsBeforeStart>", "");
			System.out.println("FINAL BODY IS ====="+FinalPostBody);
			//QueryParams
			Map<String, String> queryParams = new HashMap<String, String>();
			queryParams.put("MAC", reminderListData.get("MACADDRESS"));
			queryParams.put("InterfaceVersion", "5.2.0");
			
			//Sending Request
			Response response =  RestUtil.sendPostAPI(FinalPostBody, "application/xml", queryParams);
			//Validating Response
			//Validating Response
			Assert.assertEquals(response.getStatusCode(), 200);
			Assert.assertEquals(response.getBody().xmlPath().get("BTAResponse.ERROR.ErrorDescription"),"Mins Before Start tag is either missing/empty/incorrect");
		}
	 @Test(priority=11)
		public void addReminderInsertionWithBlankValueForSchTrailID_BEP144700011() throws JAXBException, FileNotFoundException,ClassNotFoundException, SQLException 
		{
			System.out.println("CASE:11=========================================================");
			System.out.println("REMINDER_LIST_DATA"+reminderListData);
			String postBody = addReminderData.createAddReminderPOSTBody(reminderListData);
			String FinalPostBody=postBody.replaceAll("<SchTrailID>"+reminderListData.get("SCHEDULETRAILID")+"</SchTrailID>", "<SchTrailID></SchTrailID>");
			System.out.println("FINAL BODY IS ====="+FinalPostBody);
			//QueryParams
			Map<String, String> queryParams = new HashMap<String, String>();
			queryParams.put("MAC", reminderListData.get("MACADDRESS"));
			queryParams.put("InterfaceVersion", "5.2.0");
			
			//Sending Request
			Response response =  RestUtil.sendPostAPI(FinalPostBody, "application/xml", queryParams);
			//Validating Response
			//Validating Response
			Assert.assertEquals(response.getStatusCode(), 200);
			Assert.assertEquals(response.getBody().xmlPath().get("BTAResponse.ERROR.ErrorDescription"),"Schedule Trail Id tag is either missing/empty/incorrect");
		}
	 @Test(priority=12)
		public void addReminderInsertionWithoutSchTrailIDTag_BEP144700012() throws JAXBException, FileNotFoundException,ClassNotFoundException, SQLException 
		{
			System.out.println("CASE:12=========================================================");
			System.out.println("REMINDER_LIST_DATA"+reminderListData);
			String postBody = addReminderData.createAddReminderPOSTBody(reminderListData);
			String FinalPostBody=postBody.replaceAll("<SchTrailID>"+reminderListData.get("SCHEDULETRAILID")+"</SchTrailID>", "");
			System.out.println("FINAL BODY IS ====="+FinalPostBody);
			//QueryParams
			Map<String, String> queryParams = new HashMap<String, String>();
			queryParams.put("MAC", reminderListData.get("MACADDRESS"));
			queryParams.put("InterfaceVersion", "5.2.0");
			
			//Sending Request
			Response response =  RestUtil.sendPostAPI(FinalPostBody, "application/xml", queryParams);
			//Validating Response
			//Validating Response
			Assert.assertEquals(response.getStatusCode(), 200);
			Assert.assertEquals(response.getBody().xmlPath().get("BTAResponse.ERROR.ErrorDescription"),"Schedule Trail Id tag is either missing/empty/incorrect");
		}
	 @Test(priority=13)
		public void addReminderInsertionWithBlankValueForChannelExtID_BEP144700013() throws JAXBException, FileNotFoundException,ClassNotFoundException, SQLException 
		{
			System.out.println("CASE:13=========================================================");
			System.out.println("REMINDER_LIST_DATA"+reminderListData);
			String postBody = addReminderData.createAddReminderPOSTBody(reminderListData);
			String FinalPostBody=postBody.replaceAll("<ChannelExtID>"+reminderListData.get("EXTERNALID")+"</ChannelExtID>", "<ChannelExtID></ChannelExtID>");
			System.out.println("FINAL BODY IS ====="+FinalPostBody);
			//QueryParams
			Map<String, String> queryParams = new HashMap<String, String>();
			queryParams.put("MAC", reminderListData.get("MACADDRESS"));
			queryParams.put("InterfaceVersion", "5.2.0");
			
			//Sending Request
			Response response =  RestUtil.sendPostAPI(FinalPostBody, "application/xml", queryParams);
			//Validating Response
			//Validating Response
			Assert.assertEquals(response.getStatusCode(), 200);
			Assert.assertEquals(response.getBody().xmlPath().get("BTAResponse.ERROR.ErrorDescription"),"Channel External Id tag is either missing/empty");
		}
	 @Test(priority=14)
		public void addReminderInsertionWithoutChannelExtID_BEP144700014() throws JAXBException, FileNotFoundException,ClassNotFoundException, SQLException 
		{
			System.out.println("CASE:14=========================================================");
			System.out.println("REMINDER_LIST_DATA"+reminderListData);
			String postBody = addReminderData.createAddReminderPOSTBody(reminderListData);
			String FinalPostBody=postBody.replaceAll("<ChannelExtID>"+reminderListData.get("EXTERNALID")+"</ChannelExtID>", "");
			System.out.println("FINAL BODY IS ====="+FinalPostBody);
			//QueryParams
			Map<String, String> queryParams = new HashMap<String, String>();
			queryParams.put("MAC", reminderListData.get("MACADDRESS"));
			queryParams.put("InterfaceVersion", "5.2.0");
			
			//Sending Request
			Response response =  RestUtil.sendPostAPI(FinalPostBody, "application/xml", queryParams);
			//Validating Response
			//Validating Response
			Assert.assertEquals(response.getStatusCode(), 200);
			Assert.assertEquals(response.getBody().xmlPath().get("BTAResponse.ERROR.ErrorDescription"),"Channel External Id tag is either missing/empty");
		}
	 @Test(priority=15)
		public void addReminderInsertionWhenIcorrectSchTrailID_BEP144700016() throws JAXBException, FileNotFoundException,ClassNotFoundException, SQLException 
		{
			System.out.println("CASE:15=========================================================");
			System.out.println("REMINDER_LIST_DATA"+reminderListData);
			String postBody = addReminderData.createAddReminderPOSTBody(reminderListData);
			String FinalPostBody=postBody.replaceAll("<SchTrailID>"+reminderListData.get("SCHEDULETRAILID")+"</SchTrailID>", "<SchTrailID>"+reminderListData.get("SCHEDULETRAILID")+"99999"+"</SchTrailID>");
			System.out.println("FINAL BODY IS ====="+FinalPostBody);
			//QueryParams
			Map<String, String> queryParams = new HashMap<String, String>();
			queryParams.put("MAC", reminderListData.get("MACADDRESS"));
			queryParams.put("InterfaceVersion", "5.2.0");
			
			//Sending Request
			Response response =  RestUtil.sendPostAPI(FinalPostBody, "application/xml", queryParams);
			//Validating Response
			//Validating Response
			Assert.assertEquals(response.getStatusCode(), 200);
			Assert.assertEquals(response.getBody().xmlPath().get("BTAResponse.ERROR.ErrorDescription"),"Schedule Trail Id does not exists");
		}
	 @Test(priority=16)
		public void addReminderInsertionWhenIcorrectChannelExtID_BEP144700017() throws JAXBException, FileNotFoundException,ClassNotFoundException, SQLException 
		{
			System.out.println("CASE:16=========================================================");
			System.out.println("REMINDER_LIST_DATA"+reminderListData);
			String postBody = addReminderData.createAddReminderPOSTBody(reminderListData);
			String FinalPostBody=postBody.replaceAll("<ChannelExtID>"+reminderListData.get("EXTERNALID")+"</ChannelExtID>", "<ChannelExtID>"+reminderListData.get("EXTERNALID")+"99999"+"</ChannelExtID>");
			System.out.println("FINAL BODY IS ====="+FinalPostBody);
			//QueryParams
			Map<String, String> queryParams = new HashMap<String, String>();
			queryParams.put("MAC", reminderListData.get("MACADDRESS"));
			queryParams.put("InterfaceVersion", "5.2.0");
			//Sending Request
			Response response =  RestUtil.sendPostAPI(FinalPostBody, "application/xml", queryParams);
			//Validating Response
			Assert.assertEquals(response.getStatusCode(), 200);
			Assert.assertEquals(response.getBody().xmlPath().get("BTAResponse.ERROR.ErrorDescription"),"Channel External Id does not exists");
		}
	 @Test(priority=17)
		public void addReminderInsertionWhenMinsBeforeStartIsnotCorrect_BEP144700018() throws JAXBException, FileNotFoundException,ClassNotFoundException, SQLException 
		{
			System.out.println("CASE:17=========================================================");
			System.out.println("REMINDER_LIST_DATA"+reminderListData);
			String postBody = addReminderData.createAddReminderPOSTBody(reminderListData);
			String FinalPostBody=postBody.replaceAll("<MinsBeforeStart>8</MinsBeforeStart>", "<MinsBeforeStart>101</MinsBeforeStart>");
			System.out.println("FINAL BODY IS ====="+FinalPostBody);
			//QueryParams
			Map<String, String> queryParams = new HashMap<String, String>();
			queryParams.put("MAC", reminderListData.get("MACADDRESS"));
			queryParams.put("InterfaceVersion", "5.2.0");
			
			//Sending Request
			Response response =  RestUtil.sendPostAPI(FinalPostBody, "application/xml", queryParams);
			//Validating Response
			//Validating Response
			Assert.assertEquals(response.getStatusCode(), 200);
			Assert.assertEquals(response.getBody().xmlPath().get("BTAResponse.ERROR.ErrorDescription"),"Mins Before Start value should be between 0 and 99");
		}
	 /* @Test
	 public void getReminderBTACall_BEP14477001() throws SQLException, ClassNotFoundException
	 {
		 	RestAssured.basePath = "/broker/bta/getReminder";
			//QueryParams
			TestDataCreation addReminderData = new TestDataCreation();

		 	Map<String, String> queryParams = new HashMap<String, String>();
			Map<String,String> reminderListData = DataBaseConnection.executeSelectQuery(SqlQueries.Queries.getReminderQuery,tmdbUserName, tmdbPassword);
			queryParams.put("MAC", reminderListData.get("MACADDRESS"));
			queryParams.put("InterfaceVersion", "4.2.0");
			
			//Sending Request
			Response response =  RestUtil.sendGetAPI("application/xml", queryParams);
			//Validating Response
			Assert.assertEquals(response.getStatusCode(), 200);
			System.out.println(response.asString());
			
			Map<String, String> queryParams1=addReminderData.getDataFromDB(Queries.getReminderQuery);
			String Id=queryParams1.get("REMINDERID");
			String macAdrress=queryParams1.get("MACADDRESS");
			String reminderType=queryParams1.get("REMINDERTYPE");
			
			Assert.assertEquals(response.getBody().xmlPath().get("BTAResponse.ID"),Id);
			
	 }*/
	 @AfterMethod
	 public void afterTest() throws SQLException
	 {
		 addReminderData.deleteTestData(Queries.deleteReminderQuery,Testbase.tmdbUserName,Testbase.tmdbPassword);

	 }
}
