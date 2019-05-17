package com.blockbone.companyprofile.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.bazaarvoice.jolt.Chainr;
import com.bazaarvoice.jolt.JsonUtils;
import com.blockbone.companyprofile.io.ProfileRequest;
import com.blockbone.companyprofile.io.ProfileResponse;
import com.blockbone.companyprofile.io.StatusMessage;
import com.blockbone.companyprofile.io.TCMResponse;
import com.blockbone.companyprofile.io.TCRMServiceResponse;
import com.blockbone.companyprofile.io.TxResponse;
import com.blockbone.companyprofile.io.TxResponse.KeyAddressBObj;
import com.blockbone.companyprofile.io.TxResponse.KeyContactEquivalentBObj;
import com.blockbone.companyprofile.io.TxResponse.KeyContactMethodBObj;
import com.blockbone.companyprofile.io.TxResponse.KeyIdentifierBObj;
import com.blockbone.companyprofile.io.TxResponse.KeyOrganizationNameBObj;
import com.blockbone.companyprofile.io.TxResponse.KeyPartyBObj;
import com.blockbone.companyprofile.io.TxResponse.KeyPartySearchParamBObj;
import com.blockbone.companyprofile.io.TxResponse.KeyPartySearchResultBObj;
import com.blockbone.companyprofile.io.TxResponse.ResponseObjects;
import com.blockbone.companyprofile.utils.MessageCodes;
import com.blockbone.companyprofile.utils.ProfileValidator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class ProfileService {

	@SuppressWarnings("static-access")
	public ProfileResponse processProfileDetails(ProfileRequest request) throws IOException {
		log.info("ProfileService :: processProfileDetails() :: Init");
		ProfileValidator.profileSearchValidator(request);
		//Transformation code
		Object transformedData = null;
		TCRMServiceResponse transformed = null;
		List<Object> chainrSpecJSON = JsonUtils.classpathToList("/reference/spec.json");
		log.info("Spec json follows :: " + chainrSpecJSON);
		Chainr chainr = Chainr.fromSpec(chainrSpecJSON);
		/*
		 * Here we will write logic to hit the 3rd api to get the response.
		 * I am reading the json input and replacing 2 fields and printing fyi.
		 */
		//Input parse starts here
		String inputData = readInput();
		log.info("Before replace :: " + inputData);
		inputData.replace("${orgName}$", request.getOrgName());
		inputData.replace("${orgType}$", request.getOrgId());
		log.info("After replace :: " + inputData);
		/*We will use this for hitting api client. But now no use since no 3rd api call and we get from 
		dummy method (testResponse()) in below fyi
		*/
		Object inputJSON = JsonUtils.classpathToObject("/reference/output.json");
		transformedData =  chainr.transform(inputJSON);
		log.info("Transferred json follows :: " + transformed);
		//Type cast code
		ObjectMapper mapper = new ObjectMapper();
		TCRMServiceResponse transformedMap = mapper.convertValue(transformedData,new TypeReference<TCRMServiceResponse>(){});
		log.info("Transferred bean follows :: " + transformedMap);
		//output preparation code
		TCMResponse TCMResponse = null;
		List<TCMResponse> TCMResponses = new ArrayList<>();
		int length = transformedMap.getCompanyCity().size();
		for (int i = 0; i < length ; i++) {
			TCMResponse =  TCMResponse.builder()
					.companyCity(transformedMap.getCompanyCity().get(i))
					.companyId(transformedMap.getCompanyId().get(i))
					.companyName(transformedMap.getCompanyName().get(i))
					.companyState(transformedMap.getCompanyState().get(i))
					.companyStatus(transformedMap.getCompanyStatus().get(i))
					.build();
			TCMResponses.add(TCMResponse);
		}
		log.info("ProfileService :: processProfileDetails() :: Ends");
		return ProfileResponse.builder()
				.status(MessageCodes.SUCCESS_MSG)
				.statusMessage(new StatusMessage(MessageCodes.SUCCESS_MSG,MessageCodes.PROFILE_DETAIL_SUCCESS))
				.data(TCMResponses)
				.build();
	}
	//Dummy response
	public TxResponse testResponse() {
		KeyPartySearchParamBObj KeyPartySearchParamBObj = TxResponse.KeyPartySearchParamBObj.builder()
				.ComponentID("5100390")
				.IdentificationType("2")
				.IdentificationNum("341915235")
				.PartyInquiryLevel("5")
				.MaxReturn("100").build();
		
		
		KeyOrganizationNameBObj KeyOrganizationNameBObj = TxResponse.KeyOrganizationNameBObj.builder()
				.ComponentID("3006133")
				.NameUsageType("1")
				.NameUsageValue("Legal")
				.OrganizationName("SASIIIIIIII LLC").build();
		
		KeyIdentifierBObj  KeyIdentifierBObj = TxResponse.KeyIdentifierBObj.builder()
				.ComponentID("3006135")
				.IdentifierType("2")
				.IdentifierValue("TIN")
				.IdentifierNumber("******")
				.build();

		KeyAddressBObj KeyAddressBObj = TxResponse.KeyAddressBObj.builder()
				.ComponentID("3006138")
				.AddressUsageType("3")
				.AddressUsageValue("Business")
				.AddressLineOne("ATTN JOHN FORBES")
				.AddressUsageValue("2421 S NAPPANEE ST SUITE B")
				.City("ELKHART")
				.StateType("1000018")
				.StateValue("TX")
				.CountryType("185")
				.CountryValue("UNITED STATES")
				.ZipPostalCode("465171302")
				.CountyCode("039").build();
		
		
		KeyContactMethodBObj KeyContactMethodBObj = TxResponse.KeyContactMethodBObj.builder()
				.ComponentID("ComponentID")
				.ContactMethodCatType("1")
				.ContactMethodCatValue("Telephone Number")
				.ContactMethodUsageType("2")
				.ContactMethodUsageValue("Business Telephone")
				.ReferenceNumber("5742955223").build();
		
		
		KeyContactEquivalentBObj KeyContactEquivalentBObj = TxResponse.KeyContactEquivalentBObj.builder()
				.ComponentID("3006145")
				.AdminPartyId("990011790")
				.AdminSystemType("1000009")
				.AdminSystemValue("ggg")
				.StartDate("")
				.EndDate("").build();
		
		
		KeyPartyBObj KeyPartyBObj = TxResponse.KeyPartyBObj.builder()
				.ComponentID("3006155")
				.PartyType("0")
				.StartDate("2000-02-23 00:00:00")
				.ClientStatusType("1000003")
				.ClientStatusValue("Active")
				.MDMID("0828R3C3169TJ")
				.KeyOrganizationNameBObj(Collections.singletonList(KeyOrganizationNameBObj))
				.KeyIdentifierBObj(Collections.singletonList(KeyIdentifierBObj))
				.KeyAddressBObj(Collections.singletonList(KeyAddressBObj))
				.KeyContactMethodBObj(Collections.singletonList(KeyContactMethodBObj))
				.KeyContactEquivalentBObj(Collections.singletonList(KeyContactEquivalentBObj))
				.build();
		
		
		KeyPartySearchResultBObj KeyPartySearchResultBObj = TxResponse.KeyPartySearchResultBObj.builder()
				.ComponentID("5100350")
				.DWLStatus(Collections.singletonList(TxResponse.DWLStatus.builder().Status("0").build()))
				.KeyPartySearchParamBObj(Collections.singletonList(KeyPartySearchParamBObj))
				.KeyPartyBObj(Collections.singletonList(KeyPartyBObj))
				.build();
				
		
		
		ResponseObjects ResponseObject = TxResponse.ResponseObjects.builder()
				.KeyPartySearchResultBObj(Collections.singletonList(KeyPartySearchResultBObj))
				.build();
		
		
		TxResponse txResponse = TxResponse.builder()
				.RequestType("SearchCustomerByIdentifierDetails")
				.TxResult(Collections.singletonList(TxResponse.TxResult.builder().ResultCode("SUCCESS").build()))
				.ResponseObject(Collections.singletonList(ResponseObject))
				.build();
		
		return txResponse;
	}
	
	private String readInput() throws IOException  {
		File file = ResourceUtils.getFile("classpath:reference/input.json");
		log.info("File Found : " + file.exists());
		String inpContent = new String(Files.readAllBytes(file.toPath()));
		log.info(inpContent);
		return inpContent;
	}
	
	/*
	private String readOutput() throws IOException  {
		File file = ResourceUtils.getFile("classpath:reference/output.json");
		System.out.println("File Found : " + file.exists());
		String inpContent = new String(Files.readAllBytes(file.toPath()));
		System.out.println(inpContent);
		return inpContent;
	}
	private String readSpec() throws IOException  {
			File file = ResourceUtils.getFile("classpath:reference/spec.json");
			System.out.println("File Found : " + file.exists());
			String specContent = new String(Files.readAllBytes(file.toPath()));
			System.out.println(specContent);
			return specContent;
	}*/
}
