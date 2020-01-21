package base;

import io.restassured.RestAssured;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import dbConnection.DataBaseConnection;
public class Testbase 
{
	public static String tmdbServerIp=Testbase.getUpdatedProptiesFile().getProperty("DB_IP_TM");
 	public static String tmdbServerPort=Testbase.getUpdatedProptiesFile().getProperty("DB_PORT_TM");
 	public static String tmdbUserName=Testbase.getUpdatedProptiesFile().getProperty("DB_USERNAME_TM");
 	public static String tmdbPassword=Testbase.getUpdatedProptiesFile().getProperty("DB_PASSWORD_TM");
 	public static String tmdbServiceName=Testbase.getUpdatedProptiesFile().getProperty("DB_SCHEMA_TM");
 	public static String tmServerIp=Testbase.getUpdatedProptiesFile().getProperty("TM_SERVER_IP");
	public static String tmPort=Testbase.getUpdatedProptiesFile().getProperty("TM_SERVER_PORT");
	
	public static String comicdbServerIp=Testbase.getUpdatedProptiesFile().getProperty("DB_IP_COMIC");
 	public static String comicdbServerPort=Testbase.getUpdatedProptiesFile().getProperty("DB_PORT_COMIC");
 	public static String comicdbUserName=Testbase.getUpdatedProptiesFile().getProperty("DB_USERNAME_COMIC");
 	public static String comicdbPassword=Testbase.getUpdatedProptiesFile().getProperty("DB_PASSWORD_COMIC");
 	public static String comicdbServiceName=Testbase.getUpdatedProptiesFile().getProperty("DB_SCHEMA_COMIC");
 	public static String comicServerIp=Testbase.getUpdatedProptiesFile().getProperty("COMIC_SERVER_IP");
	public static String comicPort=Testbase.getUpdatedProptiesFile().getProperty("COMIC_SERVER_PORT");
	
	public static String uusddbServerIp=Testbase.getUpdatedProptiesFile().getProperty("DB_IP_UUSD");
 	public static String uusddbServerPort=Testbase.getUpdatedProptiesFile().getProperty("DB_PORT_UUSD");
 	public static String uusddbUserName=Testbase.getUpdatedProptiesFile().getProperty("DB_USERNAME_UUSD");
 	public static String uusddbPassword=Testbase.getUpdatedProptiesFile().getProperty("DB_PASSWORD_UUSD");
 	public static String uusddbServiceName=Testbase.getUpdatedProptiesFile().getProperty("DB_SCHEMA_UUSD");
	
	@BeforeSuite(enabled=true)
	public void beforeSuite() throws ClassNotFoundException, SQLException
	{
		RestAssured.baseURI = "http://"+tmServerIp+":"+tmPort+"";
	}
	public static Properties getUpdatedProptiesFile() {
		Properties property = new Properties();
		FileInputStream FIS;
		try {
			FIS = new FileInputStream(System.getProperty("user.dir") + File.separator + "src" + File.separator+ "test" + File.separator + "java" + File.separator+ "config" + File.separator + "configuration.properties");
			property.load(FIS);
		} catch (IOException e) {

			e.printStackTrace();
		}

		return property;
	}
	@AfterSuite(enabled=true)
	public void afterSuite() throws SQLException{
		DataBaseConnection.closeConnection();
	
	}
	
	public static String getStatusOfClientSetIntegration() throws SQLException
	{
		Map<String,String> status = DataBaseConnection.executeSelectQueryUniversal("Select CONSENTTYPE,CLIENTSETINTEGRATION from CONSENTTYPES where CONSENTTYPE='TVTA'", comicdbServerIp, comicdbServerPort, comicdbUserName, comicdbPassword, comicdbServiceName);
		String Status=(status.get("CLIENTSETINTEGRATION"));
		System.out.println(Status);
		return Status;
		
	}
	
	public static void main(String[] args) throws SQLException, ClassNotFoundException 
	{
			Testbase ts=new Testbase();
			//database=new DataBaseConnection();
			//ts.getDataForReminderPostBTACall();
			ts.getStatusOfClientSetIntegration();

	}
	
}
