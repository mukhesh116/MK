package com.keybank.profile.io;

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
	private List<ProfileDetails> response;
	private String data;
	
	
	public void setResponse(List<ProfileDetails> response) {
		if(response != null && response.size() > 0) {
			this.response = response;
		}else {
			this.response = null;
		}
	}
	
	
}
