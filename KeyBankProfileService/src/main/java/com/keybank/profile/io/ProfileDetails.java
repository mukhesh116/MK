package com.keybank.profile.io;

import java.io.Serializable;

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
public class ProfileDetails implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String companyId;
	private String companyStatus;
	private String companyName;
	private String companyCity;
	private String companyState;
	private EquivalentBObj equivalentBObj;
	
	
}
