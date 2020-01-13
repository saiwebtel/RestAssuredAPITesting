package Exposure.RestAssured;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBException;

import model.Consent;

import org.testng.Assert;
import org.testng.annotations.Test;

import testData.TestDataCreation;
import RestAPIHelper.RestUtil;
import SqlQueries.Queries;
import base.Testbase;
import dbConnection.DataBaseConnection;

public class setConsentTest extends Testbase{
	
	public static String siteName=Testbase.getUpdatedProptiesFile().getProperty("SITENAME1");
	public static String query="SELECT DISTINCT(STB.MACADDRESS),cna.SUBSCRIBER_NA  FROM subscribers s,customer_na@TO_UUSD cna,SETTOPBOXES STB  WHERE S.ID = CNA.SUBSCRIBER_ID  AND S.STATUSCODE = 'A' AND STB.STATUS='Assigned'  AND STB.RECORDSTATUSCODE='A' AND rownum < 100";
	public static String deleteConsentQuery="delete FROM consents WHERE id > ( ( SELECT MAX( id ) FROM consents ) - 1 )";
	public static String getConsentQuery="select TVID,CONSENTTYPE,CONSENTVALUE,LASTUPDATEBY FROM consents WHERE id > ( ( SELECT MAX( id ) FROM consents ) - 1)";
	public static String CLIENTSETINTEGRATIONVALUE="Select CONSENTTYPE,CLIENTSETINTEGRATION from CONSENTTYPES where CONSENTTYPE='TVTA'";
	
	@Test(priority=1)
	public void setConsentTVTA_OPTIN_BEP1423001() throws JAXBException, FileNotFoundException,ClassNotFoundException, SQLException 
	{
		System.out.println("CASE:1===================CONSENT TESTS==========================");
		RestAssured.basePath = "/broker/bta/setConsent";
		TestDataCreation testData = new TestDataCreation();
		Map<String,String> ListData = DataBaseConnection.executeSelectQuery(query,tmdbUserName, tmdbPassword);
		System.out.println("REMINDER_LIST_DATA"+ListData);
		String postBody = testData.createSetConsentPOSTBody(ListData,"TVTA","OPTIN");
		System.out.println(postBody);
		//QueryParams
		Map<String, String> queryParams = new HashMap<String, String>();
		queryParams.put("MAC", ListData.get("MACADDRESS"));
		queryParams.put("InterfaceVersion", "5.2.0");
		Map<String,String> status = DataBaseConnection.executeSelectQueryUniversal(CLIENTSETINTEGRATIONVALUE, comicdbServerIp, comicdbServerPort, comicdbUserName, comicdbPassword, comicdbServiceName);
		System.out.println(status.get("CLIENTSETINTEGRATION"));
		//Sending Request
		if(status.get("CLIENTSETINTEGRATION")=="LOCAL")
		{
		Response response =  RestUtil.sendPostAPI(postBody, "application/xml", queryParams);
		//Validating Response
		Assert.assertEquals(response.getStatusCode(), 200);
		System.out.println(response.asString());
		Map<String, String> queryParams1=testData.getDataFromDBUniversal(getConsentQuery,Testbase.comicdbServerIp,Testbase.comicdbServerPort,Testbase.comicdbUserName,Testbase.comicdbPassword,Testbase.comicdbServiceName);
		String consentType=queryParams1.get("CONSENTTYPE");
		
		System.out.println("CONSENT Is--------------->"+consentType);
		Assert.assertEquals(response.getBody().xmlPath().get("BTAResponse.Status"),"Success");
		Assert.assertEquals(consentType,"TVTA");
		testData.deleteTestData(deleteConsentQuery,Testbase.comicdbServerIp,Testbase.comicdbServerPort,Testbase.comicdbUserName,Testbase.comicdbPassword,Testbase.comicdbServiceName);
		}
		else
		{
			System.out.println("********** Property is set as IIAMS *************");
		}
	}
	@Test(priority=2)
	public void setConsentTVTA_OPOUT_BEP1423002() throws JAXBException, FileNotFoundException,ClassNotFoundException, SQLException 
	{
		System.out.println("CASE:2==================CONSENT TEST=======================================");
		RestAssured.basePath = "/broker/bta/setConsent";
		TestDataCreation testData = new TestDataCreation();
		Map<String,String> ListData = DataBaseConnection.executeSelectQuery(query,tmdbUserName, tmdbPassword);
		System.out.println("REMINDER_LIST_DATA"+ListData);
		String postBody = testData.createSetConsentPOSTBody(ListData,"TVTA","OPTOUT");
		System.out.println(postBody);
		//QueryParams
		Map<String, String> queryParams = new HashMap<String, String>();
		queryParams.put("MAC", ListData.get("MACADDRESS"));
		queryParams.put("InterfaceVersion", "5.2.0");
		
		//Sending Request
		Response response =  RestUtil.sendPostAPI(postBody, "application/xml", queryParams);
		//Validating Response
		Assert.assertEquals(response.getStatusCode(), 200);
		System.out.println(response.asString());
		Map<String, String> queryParams1=testData.getDataFromDBUniversal(getConsentQuery,Testbase.comicdbServerIp,Testbase.comicdbServerPort,Testbase.comicdbUserName,Testbase.comicdbPassword,Testbase.comicdbServiceName);
		String consentValue=queryParams1.get("CONSENTVALUE");
		String lastUpdatedBy=queryParams1.get("LASTUPDATEBY");
		String lastUpdatedBy1=lastUpdatedBy.substring(4);
		System.out.println("CONSENT Is--------------->"+consentValue);
		System.out.println("LASTUPDTED BY Is--------------->"+lastUpdatedBy1);
		Assert.assertEquals(response.getBody().xmlPath().get("BTAResponse.Status"),"Success");
		Assert.assertEquals(consentValue,"OPTOUT");
		testData.deleteTestData(deleteConsentQuery,Testbase.comicdbServerIp,Testbase.comicdbServerPort,Testbase.comicdbUserName,Testbase.comicdbPassword,Testbase.comicdbServiceName);
	}
	@Test(priority=3)
	public void setConsentTVReco_OPTIN_BEP1423003() throws JAXBException, FileNotFoundException,ClassNotFoundException, SQLException 
	{
		System.out.println("CASE:3===================CONSENT TESTS==========================");
		RestAssured.basePath = "/broker/bta/setConsent";
		TestDataCreation testData = new TestDataCreation();
		Map<String,String> ListData = DataBaseConnection.executeSelectQuery(query,tmdbUserName, tmdbPassword);
		System.out.println("REMINDER_LIST_DATA"+ListData);
		String postBody = testData.createSetConsentPOSTBody(ListData,"TVReco","OPTIN");
		System.out.println(postBody);
		//QueryParams
		Map<String, String> queryParams = new HashMap<String, String>();
		queryParams.put("MAC", ListData.get("MACADDRESS"));
		queryParams.put("InterfaceVersion", "5.2.0");
		
		//Sending Request
		
		Response response =  RestUtil.sendPostAPI(postBody, "application/xml", queryParams);
		//Validating Response
		Assert.assertEquals(response.getStatusCode(), 200);
		System.out.println(response.asString());
		Map<String, String> queryParams1=testData.getDataFromDBUniversal(getConsentQuery,Testbase.comicdbServerIp,Testbase.comicdbServerPort,Testbase.comicdbUserName,Testbase.comicdbPassword,Testbase.comicdbServiceName);
		String consentType=queryParams1.get("CONSENTTYPE");
		
		System.out.println("CONSENT Is--------------->"+consentType);
		Assert.assertEquals(response.getBody().xmlPath().get("BTAResponse.Status"),"Success");
		Assert.assertEquals(consentType,"TVReco");
		testData.deleteTestData(deleteConsentQuery,Testbase.comicdbServerIp,Testbase.comicdbServerPort,Testbase.comicdbUserName,Testbase.comicdbPassword,Testbase.comicdbServiceName);
		
		
	}
	@Test(priority=4)
	public void setConsentTVReco_OPTOUT_BEP1423004() throws JAXBException, FileNotFoundException,ClassNotFoundException, SQLException 
	{
		System.out.println("CASE:4===================CONSENT TESTS==========================");
		RestAssured.basePath = "/broker/bta/setConsent";
		TestDataCreation testData = new TestDataCreation();
		Map<String,String> ListData = DataBaseConnection.executeSelectQuery(query,tmdbUserName, tmdbPassword);
		System.out.println("REMINDER_LIST_DATA"+ListData);
		String postBody = testData.createSetConsentPOSTBody(ListData,"TVReco","OPTOUT");
		System.out.println(postBody);
		//QueryParams
		Map<String, String> queryParams = new HashMap<String, String>();
		queryParams.put("MAC", ListData.get("MACADDRESS"));
		queryParams.put("InterfaceVersion", "5.2.0");
		
		//Sending Request
		Response response =  RestUtil.sendPostAPI(postBody, "application/xml", queryParams);
		//Validating Response
		Assert.assertEquals(response.getStatusCode(), 200);
		System.out.println(response.asString());
		Map<String, String> queryParams1=testData.getDataFromDBUniversal(getConsentQuery,Testbase.comicdbServerIp,Testbase.comicdbServerPort,Testbase.comicdbUserName,Testbase.comicdbPassword,Testbase.comicdbServiceName);
		String consentValue=queryParams1.get("CONSENTVALUE");
		
		System.out.println("CONSENT Is--------------->"+consentValue);
		Assert.assertEquals(response.getBody().xmlPath().get("BTAResponse.Status"),"Success");
		Assert.assertEquals(consentValue,"OPTOUT");
		testData.deleteTestData(deleteConsentQuery,Testbase.comicdbServerIp,Testbase.comicdbServerPort,Testbase.comicdbUserName,Testbase.comicdbPassword,Testbase.comicdbServiceName);
	}
	@Test(priority=5)
	public void setConsentwithInvalidConsentType_BEP1423005() throws JAXBException, FileNotFoundException,ClassNotFoundException, SQLException 
	{
		System.out.println("CASE:5===================CONSENT TESTS==========================");
		RestAssured.basePath = "/broker/bta/setConsent";
		TestDataCreation testData = new TestDataCreation();
		Map<String,String> ListData = DataBaseConnection.executeSelectQuery(query,tmdbUserName, tmdbPassword);
		System.out.println("REMINDER_LIST_DATA"+ListData);
		String postBody = testData.createSetConsentPOSTBody(ListData,"TVReco1","OPTOUT");
		System.out.println(postBody);
		//QueryParams
		Map<String, String> queryParams = new HashMap<String, String>();
		queryParams.put("MAC", ListData.get("MACADDRESS"));
		queryParams.put("InterfaceVersion", "5.2.0");
		
		//Sending Request
		Response response =  RestUtil.sendPostAPI(postBody, "application/xml", queryParams);
		//Validating Response
		Assert.assertEquals(response.getStatusCode(), 200);
		System.out.println(response.asString());
		Assert.assertEquals(response.getBody().xmlPath().get("BTAResponse.ERROR.ErrorDescription"),"ConsentType Invalid");
	}
	@Test(priority=5)
	public void setConsentwithInvalidConsentValue_BEP1423006() throws JAXBException, FileNotFoundException,ClassNotFoundException, SQLException 
	{
		System.out.println("CASE:6===================CONSENT TESTS==========================");
		RestAssured.basePath = "/broker/bta/setConsent";
		TestDataCreation testData = new TestDataCreation();
		Map<String,String> ListData = DataBaseConnection.executeSelectQuery(query,tmdbUserName, tmdbPassword);
		System.out.println("REMINDER_LIST_DATA"+ListData);
		String postBody = testData.createSetConsentPOSTBody(ListData,"TVReco","OPTOUT1");
		System.out.println(postBody);
		//QueryParams
		Map<String, String> queryParams = new HashMap<String, String>();
		queryParams.put("MAC", ListData.get("MACADDRESS"));
		queryParams.put("InterfaceVersion", "5.2.0");
		
		//Sending Request
		Response response =  RestUtil.sendPostAPI(postBody, "application/xml", queryParams);
		//Validating Response
		Assert.assertEquals(response.getStatusCode(), 200);
		System.out.println(response.asString());
		Assert.assertEquals(response.getBody().xmlPath().get("BTAResponse.ERROR.ErrorDescription"),"Mandatory Parameter ConsentValue should be either OPTIN or OPTOUT");
	}
}
