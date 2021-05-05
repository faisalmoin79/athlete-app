package com.supersapiens.athlete.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SportTypeEnum {
	
	CYCLING("C"),
	RUNNING("R"),
	SWIMMING("S");
	
	private String sport;
	

}
