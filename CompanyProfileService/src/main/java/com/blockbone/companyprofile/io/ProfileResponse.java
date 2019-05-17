package com.blockbone.companyprofile.io;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonInclude(value=Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileResponse {

	private String status;
	private StatusMessage statusMessage;
	private List<TCRMServiceResponse> TCRMServiceResponse;
	private List<TCMResponse> data;
	
	
	

	
}
