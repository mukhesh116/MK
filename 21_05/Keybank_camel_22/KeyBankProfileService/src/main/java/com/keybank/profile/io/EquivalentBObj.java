package com.keybank.profile.io;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonInclude(value=Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@NoArgsConstructor
@AllArgsConstructor
public class EquivalentBObj implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String ComponentID;
	private String AdminSystemType;
	private String AdminSystemValue;
	private String AdminPartyId;
	private String StartDate;
	private String EndDate;
}
