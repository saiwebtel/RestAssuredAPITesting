package model;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="BTA")
public class BTA 
{
	@XmlAttribute(name = "xmlns:xsi")
	private String xmlnsxsi = "http://www.w3.org/2001/XMLSchema-instance";
	private String xsinoNamespaceSchemaLocation;
	private AddReminder addReminder;
	private Consent consent ;   
	
	@XmlElement(name="Consent")
	public Consent getConsents() {
		return consent;
	}
	public void setConsents(Consent consent) {
		this.consent = consent;
	}
	
	@XmlElement(name="AddReminderDoc")
	public AddReminder getAddReminder() {
		return addReminder;
	}

	public void setAddReminder(AddReminder addReminder) {
		this.addReminder = addReminder;
	}



	@XmlAttribute(name = "xsi:noNamespaceSchemaLocation")
	public String getXsinoNamespaceSchemaLocation() {
		return xsinoNamespaceSchemaLocation;
	}

	public void setXsinoNamespaceSchemaLocation(String xsinoNamespaceSchemaLocation) {
		this.xsinoNamespaceSchemaLocation = xsinoNamespaceSchemaLocation;
	}




}
