package testData;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import model.AddReminder;
import model.BTA;
import model.Consent;
import XMLUtils.XMLUtil;
import base.Testbase;
import dbConnection.DataBaseConnection;

public class TestDataCreation {


	public String createAddReminderPOSTBody(Map<String, String> reminderData) {
		BTA obj = new BTA();
		AddReminder addrem =  new AddReminder();
		obj.setXsinoNamespaceSchemaLocation("BTADocAddReminder.xsd");
		addrem.setReminderType("Reminder");
		addrem.setMinsBeforeStart("8");
		addrem.setChannelExtID(reminderData.get("EXTERNALID"));
		addrem.setSchTrailID(reminderData.get("SCHEDULETRAILID"));
		obj.setAddReminder(addrem);
		XMLUtil xmlUtil= new  XMLUtil();
		String requestBody1 = xmlUtil.convertToXml(obj, obj.getClass());
		return requestBody1;

	}
	public String createSetConsentPOSTBody(Map<String, String> reminderData,String ConsentType,String consentValue) {
		BTA obj = new BTA();
		Consent setConsent =  new Consent();
		obj.setXsinoNamespaceSchemaLocation("BTADocSetConsent.xsd");
		setConsent.setConsentType(ConsentType);
		setConsent.setConsentValue(consentValue);
		setConsent.setConsentMessage("Dynamic");
		setConsent.setLastUpdatedBy("Automatic_Updation");
		obj.setConsents(setConsent);
		XMLUtil xmlUtil= new  XMLUtil();
		String requestBody2 = xmlUtil.convertToXml(obj, obj.getClass());
		return requestBody2;
	}
	public Map<String, String> getDataFromDBUniversal(String Query,String dbIP,String dbPort,String dbUsername,String dbPassword,String serviceName) throws SQLException, ClassNotFoundException
	{
		Map<String, String> ListData = null;
		try{
			ListData = DataBaseConnection.executeSelectQueryUniversal(Query,dbIP,dbPort,dbUsername,dbPassword,serviceName);
		 }catch(Exception ex){
			System.out.println(ex.getMessage());
		}
		finally{
			//stmt.close();
			//resultset2.close();
			DataBaseConnection.closeConnection();
		}
		
		return ListData;
	}
	public Map<String, String> getDataFromDB(String Query) throws SQLException, ClassNotFoundException
	{
		Map<String, String> ListData = null;
		try{
			ListData = DataBaseConnection.executeSelectQuery(Query,Testbase.tmdbUserName, Testbase.tmdbPassword);
		 }catch(Exception ex){
			System.out.println(ex.getMessage());
		}
		finally{
			//stmt.close();
			//resultset2.close();
			DataBaseConnection.closeConnection();
		}
		
		return ListData;
	}
	public void deleteTestData(String Query,String tmUserName,String tmPassword ) throws SQLException
	{
		/*DataBaseConnection.executeDeleteQuery(SqlQueries.Queries.deleteReminderQuery,Testbase.tmdbUserName, Testbase.tmdbPassword);
		System.out.println("Delete reminder test data executed");*/
		Statement stmt = null;
		ResultSet resultset2 = null;
		try{
			stmt = DataBaseConnection.getDBConnection(tmUserName,tmPassword).createStatement();
			resultset2=stmt.executeQuery(Query);
		    }
	     
		catch(Exception ex){
			System.out.println(ex.getMessage());
		}
		finally{
			stmt.close();
			resultset2.close();
			DataBaseConnection.closeConnection();
		}
	}
	public void deleteTestData(String Query,String dbIP,String dbPort,String dbUserName,String dbPassword,String serviceName ) throws SQLException
	{
		/*DataBaseConnection.executeDeleteQuery(SqlQueries.Queries.deleteReminderQuery,Testbase.tmdbUserName, Testbase.tmdbPassword);
		System.out.println("Delete reminder test data executed");*/
		Statement stmt = null;
		ResultSet resultset2 = null;
		try{
			stmt = DataBaseConnection.getDBConnectionUniversal(dbIP,dbPort,dbUserName,dbPassword,serviceName).createStatement();
			resultset2=stmt.executeQuery(Query);
		    }
	     
		catch(Exception ex){
			System.out.println(ex.getMessage());
		}
		finally{
			stmt.close();
			resultset2.close();
			DataBaseConnection.closeConnection();
		}
	}
	public static void main(String args[]) throws ClassNotFoundException, SQLException
	{
		String Query1=null;
		TestDataCreation adt=new TestDataCreation();
		adt.getDataFromDB(Query1);
	}

}
