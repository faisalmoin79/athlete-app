package com.supersapiens.athlete.constant;


// these messages could go to properties files
public class AthleteAppConstants {

	public static class ValidationMsg {
		public static final String FIRST_NAME_IS_REQUIRED="First Name is Required";
		public static final String LAST_NAME_IS_REQUIRED = "lastName is required.";
	}

	public static class ApiMessages {
		public static final String ATHLETE_CREATED_SUCCESSFULLY = "Athlete created successfully";
		public static final String ATHLETE_WITH_ID_D_DOES_NOT_EXIST = "Athlete with id %d does not exist";
		public static final String ATHLETE_WITH_ID_D_DELETED_SUCCESSFULLY = "Athlete with id %d deleted successfully";
		public static final String ATHLETE_UPDATED_SUCCESSFULLY = "Athlete updated successfully";
	}

	public static class ServiceErrors {
		public static final String ATHLETE_NOT_FOUND = "Athlete Not Found";
		public static final String ID_MUST_BE_GREATER_THAN_1 = "Id must be greater than 1";
		public static final String ATHLETE_CAN_NOT_BE_NULL = "athlete can not be null";
	}
	
}