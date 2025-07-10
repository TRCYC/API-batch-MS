package com.rcyc.batchsystem.model.resco;

import javax.xml.bind.annotation.XmlElement;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Agency {

	private String Agency;
	private String AgencyId;
	private String AgentId;
	private String Code;
	private String Name;
	private String Clia;
	private String Iata;
	private String Tids;
	private String OtherCode1;
	// newly added
	private String Agent;
	private String Enabled;
	private String Disabled;

	// Getter Methods
	@XmlElement(name = "AgencyId")
	public String getAgencyId() {
		return AgencyId;
	}

	@XmlElement(name = "AgentId")
	public String getAgentId() {
		return AgentId;
	}

	@XmlElement(name = "Code")
	public String getCode() {
		return Code;
	}

	@XmlElement(name = "Name")
	public String getName() {
		return Name;
	}

	// Setter Methods

	public void setAgencyId(String AgencyId) {
		this.AgencyId = AgencyId;
	}

	public void setAgentId(String AgentId) {
		this.AgentId = AgentId;
	}

	public void setCode(String Code) {
		this.Code = Code;
	}

	public void setName(String Name) {
		this.Name = Name;
	}

	/**
	 * @return the clia
	 */
	@XmlElement(name = "Clia")
	public String getClia() {
		return Clia;
	}

	/**
	 * @param clia the clia to set
	 */
	public void setClia(String clia) {
		Clia = clia;
	}

	/**
	 * @return the iata
	 */
	@XmlElement(name = "Iata")
	public String getIata() {
		return Iata;
	}

	/**
	 * @param iata the iata to set
	 */
	public void setIata(String iata) {
		Iata = iata;
	}

	/**
	 * @return the tids
	 */
	@XmlElement(name = "Tids")
	public String getTids() {
		return Tids;
	}

	/**
	 * @param tids the tids to set
	 */
	public void setTids(String tids) {
		Tids = tids;
	}

	@XmlElement(name = "OtherCode1")
	public String getOtherCode1() {
		return OtherCode1;
	}

	public void setOtherCode1(String otherCode1) {
		OtherCode1 = otherCode1;
	}

	/**
	 * @return the agent
	 */
	@XmlElement(name = "Agent")
	public String getAgent() {
		return Agent;
	}

	/**
	 * @param agent the agent to set
	 */
	public void setAgent(String agent) {
		Agent = agent;
	}

	/**
	 * @return the agency
	 */
	@XmlElement(name = "Agency")
	public String getAgency() {
		return Agency;
	}

	/**
	 * @param agency the agency to set
	 */
	public void setAgency(String agency) {
		Agency = agency;
	}

	/**
	 *
	 */
	public Agency() {
	}

	/**
	 * @param clia
	 * @param iata
	 * @param tids
	 */
	public Agency(String clia, String iata, String tids, String otherCode1) {
		super();
		Clia = clia;
		Iata = iata;
		Tids = tids;
		OtherCode1 = otherCode1;
	}

	/**
	 * @param agencyId
	 * @param agent
	 */
	public Agency(String agencyId, String agent) {
		super();
		AgencyId = agencyId;
		Agent = agent;
	}

	/**
	 * @param agencyId
	 * @param agent
	 */

	public Agency(String agentId) {
		super();
		AgentId = agentId;
	}

	@XmlElement(name = "Enabled")
	public String getEnabled() {
		return Enabled;
	}

	public void setEnabled(String enabled) {
		Enabled = enabled;
	}

	@XmlElement(name = "Disabled")
	public String getDisabled() {
		return Disabled;
	}

	public void setDisabled(String disabled) {
		Disabled = disabled;
	}
}
