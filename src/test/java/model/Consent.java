package model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Consent")
public class Consent{
	private String ConsentType;
    private String ConsentValue;
    private String LastUpdatedBy;
    private String ConsentMessage;
    @XmlElement(name = "ConsentType" )
    public String getConsentType() {
    	return ConsentType;
    }

    public void setConsentType(String ConsentType) {
    	this.ConsentType = ConsentType;
    }

    @XmlElement(name = "ConsentValue" )
    public String getConsentValue() {
    	return ConsentValue;
    }

    public void setConsentValue(String ConsentValue) {
    	this.ConsentValue = ConsentValue;
    }
    @XmlElement(name = "LastUpdatedBy" )
    public String getLastUpdatedBy() {	
    	return LastUpdatedBy;
    }
    public void setLastUpdatedBy(String LastUpdatedBy) {
    	this.LastUpdatedBy = LastUpdatedBy;
    }

    @XmlElement(name = "ConsentMessage" )
    public String getConsentMessage() {
    	return ConsentMessage;
    }
    public void setConsentMessage(String ConsentMessage) {
    	this.ConsentMessage = ConsentMessage;
    }

}