package com.keybank.profile.io;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
@JsonInclude(value=Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
public class MDMProfileResponse {

	private String RequestType;
	private List<TxResult> TxResult;
	private List<ResponseObjects> ResponseObject;
	private KeyPartySearchResultBObj KeyPartySearchResultBObj;
	
	@Data
	@Builder
	@JsonInclude(value=Include.NON_NULL)
	@JsonIgnoreProperties(ignoreUnknown=true)
	public static class TxResult {
		private String ResultCode;
	}
	
	@Data
	@Builder
	@JsonInclude(value=Include.NON_NULL)
	@JsonIgnoreProperties(ignoreUnknown=true)
	public static class ResponseObjects {
		private List<KeyPartySearchResultBObj> KeyPartySearchResultBObj;
	}
	
	@Data
	@Builder
	@JsonInclude(value=Include.NON_NULL)
	@JsonIgnoreProperties(ignoreUnknown=true)
	public static class KeyPartySearchResultBObj{
		private String ComponentID;
		private List<DWLStatus> DWLStatus;
		private List<KeyPartySearchParamBObj> KeyPartySearchParamBObj;
		private List<KeyPartyBObj> KeyPartyBObj;
		
	}
	
	@Data
	@Builder
	@JsonInclude(value=Include.NON_NULL)
	@JsonIgnoreProperties(ignoreUnknown=true)
	public static class DWLStatus {
		private String Status;
	}
	
	@Data
	@Builder
	@JsonInclude(value=Include.NON_NULL)
	@JsonIgnoreProperties(ignoreUnknown=true)
	public static class KeyPartySearchParamBObj {
		private String ComponentID;
		private String IdentificationType;
		private String IdentificationNum;
		private String PartyInquiryLevel;
		private String MaxReturn;
	}
	
	@Data
	@Builder
	@JsonInclude(value=Include.NON_NULL)
	@JsonIgnoreProperties(ignoreUnknown=true)
	public static class KeyPartyBObj {
		private String ComponentID;
		private String PartyType;
		private String StartDate;
		private String ClientStatusType;
		private String ClientStatusValue;
		private String MDMID;
		private List<KeyOrganizationNameBObj> KeyOrganizationNameBObj;
		private List<KeyIdentifierBObj> KeyIdentifierBObj;
		private List<KeyAddressBObj> KeyAddressBObj;
		private List<KeyContactMethodBObj> KeyContactMethodBObj;
		private List<KeyContactEquivalentBObj> KeyContactEquivalentBObj;
		
	}
	
	@Data
	@Builder
	@JsonInclude(value=Include.NON_NULL)
	@JsonIgnoreProperties(ignoreUnknown=true)
	public static class KeyOrganizationNameBObj {
		private String ComponentID;
		private String NameUsageType;
		private String NameUsageValue;
		private String OrganizationName;
	}
	
	@Data
	@Builder
	@JsonInclude(value=Include.NON_NULL)
	@JsonIgnoreProperties(ignoreUnknown=true)
	public static class KeyIdentifierBObj {
		private String ComponentID;
		private String IdentifierType;
		private String IdentifierValue;
		private String IdentifierNumber;
		private String IdentifierStatusType;
		private String IdentifierStatusValue;
	}
	
	@Data
	@Builder
	@JsonInclude(value=Include.NON_NULL)
	@JsonIgnoreProperties(ignoreUnknown=true)
	public static class KeyAddressBObj {
		private String ComponentID;
		private String AddressUsageType;
		private String AddressUsageValue;
		private String AddressLineOne;
		private String AddressLineTwo;
		private String City;
		private String StateType;
		private String StateValue;
		private String CountryType;
		private String CountryValue;
		private String ZipPostalCode;
		private String CountyCode;
	}
	
	@Data
	@Builder
	@JsonInclude(value=Include.NON_NULL)
	@JsonIgnoreProperties(ignoreUnknown=true)
	public static class KeyContactMethodBObj {
		private String ComponentID;
		private String ContactMethodCatType;
		private String ContactMethodCatValue;
		private String ContactMethodUsageType;
		private String ContactMethodUsageValue;
		private String ReferenceNumber;
	}
	
	@Data
	@Builder
	@JsonInclude(value=Include.NON_NULL)
	@JsonIgnoreProperties(ignoreUnknown=true)
	public static class KeyContactEquivalentBObj {
		private String ComponentID;
		private String AdminSystemType;
		private String AdminSystemValue;
		private String AdminPartyId;
		private String StartDate ;
		private String EndDate;
	}
	
}
