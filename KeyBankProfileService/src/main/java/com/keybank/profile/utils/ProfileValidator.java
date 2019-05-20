package com.keybank.profile.utils;

import com.keybank.profile.io.ProfileRequest;

public class ProfileValidator {

	
	public static void profileSearchValidator(ProfileRequest request) {
		if( (request.getOrgId()  == null || request.getOrgId().isEmpty()) && (request.getOrgName()  == null || request.getOrgName().isEmpty())) {
			throw new IllegalArgumentException(MessageCodes.BAD_REQUEST_DESC);
		}
	}
	
}
