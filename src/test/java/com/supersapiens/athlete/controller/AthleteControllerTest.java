package com.supersapiens.athlete.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.supersapiens.athlete.enums.SportTypeEnum;
import com.supersapiens.athlete.model.Athlete;
import com.supersapiens.athlete.service.AthleteService;

import lombok.extern.slf4j.Slf4j;
import static com.supersapiens.athlete.constant.AthleteAppConstants.ValidationMsg.FIRST_NAME_IS_REQUIRED;
import static com.supersapiens.athlete.constant.AthleteAppConstants.ValidationMsg.LAST_NAME_IS_REQUIRED;


@ExtendWith(SpringExtension.class) // initialing mocks in JUnit 5
@WebMvcTest
@Slf4j
public class AthleteControllerTest {
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private AthleteService athleteService;
	
	@InjectMocks
	private AthleteController athleteController;
	
	public static final ObjectMapper objMapper = new ObjectMapper();
	
	
	
	@Test
	public void contextLoads() {
		assertThat(athleteController).isNotNull();
	}
	
	@Test
	public void shouldReturnErrorWhenCreatingAthleteWithEmptyRequiredFields() throws Exception {
		Athlete athleteWithEmptyFields = Athlete.builder().build();
		this.mockMvc
		.perform(
				post("/athlete/add")
				.content(getJsonString(athleteWithEmptyFields))
				.contentType(MediaType.APPLICATION_JSON)
			    .accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.firstName", is(FIRST_NAME_IS_REQUIRED)))
		.andExpect(jsonPath("$.lastName", is(LAST_NAME_IS_REQUIRED)));
	}
	
	@Test
	public void shouldReturnSucessWhenCreatingAthleteWithValidInfo() throws Exception{
		Athlete validAthlete = Athlete.builder()
				.firstName("Valid")
				.lastName("Athlete")
				.primarySport(SportTypeEnum.CYCLING)
				.secondarySport(SportTypeEnum.SWIMMING)
				.build();
		this.mockMvc
		.perform(
				post("/athlete/add")
				.content(getJsonString(validAthlete))
				.contentType(MediaType.APPLICATION_JSON)
			    .accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().is2xxSuccessful())
		.andExpect(jsonPath("$.message", is(FIRST_NAME_IS_REQUIRED)))
		.andExpect(jsonPath("$.lastName", is(LAST_NAME_IS_REQUIRED)));
	}
	
	@Test
	public void shouldReturnNotFoundErrorWhenFetchingAthleteThatDoesNotExist() {
		
	}
	
	@Test
	public void shouldReturnAthleteWhenFetchingAthleteThatExists() {
		
	}
	
	public static String getJsonString(Object obj) {
		try {
			return objMapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			log.error(e.getMessage());
			throw new RuntimeException(e);
		}
	}
	
	
}
