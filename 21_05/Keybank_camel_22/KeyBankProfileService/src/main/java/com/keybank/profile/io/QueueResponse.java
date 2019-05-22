package com.keybank.profile.io;

import java.io.Serializable;
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
public class QueueResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<String> companyId;
	private List<String> companyStatus;
	private List<String> companyName;
	private List<String> companyCity;
	private List<String> companyState;
	private List<EquivalentBObj> EquivalentBObj;
	
	
}
