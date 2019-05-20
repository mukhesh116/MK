package com.keybank.profile.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.keybank.profile.io.MDMBaseProfileDetails;
import com.keybank.profile.io.MDMProfileResponse;
import com.keybank.profile.io.MDMProfileResponse.KeyContactEquivalentBObj;
import com.keybank.profile.io.MDMRequest;
import com.keybank.profile.io.ProfileDetails;
import com.keybank.profile.io.ProfileRequest;
import com.keybank.profile.io.ProfileResponse;
import com.keybank.profile.io.QueueResponse;
import com.keybank.profile.io.StatusMessage;
import com.keybank.profile.queue.Listener;
import com.keybank.profile.queue.Sender;
import com.keybank.profile.utils.MessageCodes;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class ProfileService {

	@Autowired
	private Sender sender;
	
	
	public ProfileResponse processProfileDetails(ProfileRequest request) throws RestClientException, Exception {
		log.info("ProfileService :: processProfileDetails() :: Init");
		//Input code
		String inputData = readInput();
		log.info("Before replace :: " + inputData);
		inputData = inputData.replace("${orgName}$", request.getOrgName());
		inputData = inputData.replace("${orgType}$", request.getOrgId());
		log.info("After replace :: " + inputData);
		//MDM rest api call invocation
		MDMRequest mdmRequest = MDMRequest.builder().data(inputData).build();
		ResponseEntity<?> responseEntity =  this.invokeAPI("http://localhost:8083/mdm/api/v1/profile-details",
				HttpMethod.POST,mdmRequest, ProfileResponse.class);
		ProfileResponse mdmMesponse = (ProfileResponse) responseEntity.getBody();
		String processData = mdmMesponse.getData();
		log.info("Response from MDM service :: " + mdmMesponse.toString());
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
		//MDMBaseProfileDetails mdmBaseProfileDetail =  mapper.readValue(processData,MDMBaseProfileDetails.class);
		//processData = enhanceStatus(mdmBaseProfileDetail);
		sender.processSendingQueue(processData);
		try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            log.error("LOG exception ",e);
        }
		log.info("Listener response :: " +Listener.queueResponse);
		QueueResponse transformedMap =  mapper.readValue(Listener.queueResponse,QueueResponse.class);
		log.info("Transferred bean follows :: " + transformedMap);
		//output preparation code
		ProfileDetails tcmResponse = null;
		List<ProfileDetails> TCMResponses = new ArrayList<>();
		int length = transformedMap.getCompanyCity().size();
		for (int i = 0; i < length ; i++) {
			tcmResponse =  ProfileDetails.builder()
					.companyCity(transformedMap.getCompanyCity().get(i))
					.companyId(transformedMap.getCompanyId().get(i))
					.companyName(transformedMap.getCompanyName().get(i))
					.companyState(transformedMap.getCompanyState().get(i))
					.companyStatus(transformedMap.getCompanyStatus().get(i))
					.build();
			TCMResponses.add(tcmResponse);
		}
		log.info("ProfileService :: processProfileDetails() :: Ends");
		return ProfileResponse.builder()
				.status(MessageCodes.SUCCESS)
				.statusMessage(new StatusMessage(MessageCodes.SUCCESS_MSG,MessageCodes.PROFILE_DETAIL_SUCCESS))
				.response(TCMResponses)
				.build();
	}

	public String enhanceStatus(MDMBaseProfileDetails mdmBaseProfileDetails) throws JsonProcessingException {
		KeyContactEquivalentBObj KeyContactEquivalentBObj = null;
		for (MDMProfileResponse mDMProfileResponse : mdmBaseProfileDetails.getTCRMService().getTxResponse()) {
			KeyContactEquivalentBObj = mDMProfileResponse.getKeyPartySearchResultBObj().getKeyPartyBObj().get(0).
					getKeyContactEquivalentBObj().get(0);
			if(KeyContactEquivalentBObj.getAdminSystemValue().equalsIgnoreCase("ENT") && 
					!StringUtils.isEmpty(KeyContactEquivalentBObj.getEndDate()) ) {
				mDMProfileResponse.getKeyPartySearchResultBObj().getKeyPartyBObj().get(0).setClientStatusValue("In-Active");
			}
		}
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
		return mapper.writeValueAsString(mdmBaseProfileDetails);
	}
	
	
	private String readInput() throws IOException  {
		String data = "";
		ClassPathResource cpr = new ClassPathResource("/reference/input.json");
		byte[] bdata = FileCopyUtils.copyToByteArray(cpr.getInputStream());
	    data = new String(bdata, StandardCharsets.UTF_8);
		return data;
	}
	
	//Generic method to call lifafa apis
	private ResponseEntity<?> invokeAPI(String url,HttpMethod method,Object payload,Class<?> responseType) throws RestClientException, Exception{
		ResponseEntity<?> response  = null;
		HttpEntity<?> requestEntity = new HttpEntity<>(payload);
		RestTemplate restTemplate = new RestTemplate();
		response = restTemplate.exchange(url,method,requestEntity,responseType);
        log.debug(" Response from lifafa call :: "+response);
        return response;
	}
}
