package com.supersapiens.athlete.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.any;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
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
import static com.supersapiens.athlete.constant.AthleteAppConstants.ApiMessages.ATHLETE_CREATED_SUCCESSFULLY;
import static com.supersapiens.athlete.constant.AthleteAppConstants.ApiMessages.ATHLETE_UPDATED_SUCCESSFULLY;
import static com.supersapiens.athlete.constant.AthleteAppConstants.ApiMessages.ATHLETE_WITH_ID_D_DELETED_SUCCESSFULLY;
import static com.supersapiens.athlete.constant.AthleteAppConstants.ApiMessages.ATHLETE_WITH_ID_D_DOES_NOT_EXIST;

@ExtendWith(SpringExtension.class) // initialing mocks in JUnit 5
@WebMvcTest
@Slf4j
public class AthleteControllerTest {
	private static final String UPDATED_FIRST_NAME = "Updated First Name";

	private static final String UPDATED_LAST_NAME = "Updated Last Name";

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
				.perform(post("/athlete/add").content(getJsonString(athleteWithEmptyFields))
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.firstName", is(FIRST_NAME_IS_REQUIRED)))
				.andExpect(jsonPath("$.lastName", is(LAST_NAME_IS_REQUIRED)));
	}

	@Test
	public void shouldReturnSucessWhenCreatingAthleteWithValidInfo() throws Exception {
		Athlete validAthlete = Athlete.builder().firstName("Valid").lastName("Athlete")
				.primarySport(SportTypeEnum.CYCLING).secondarySport(SportTypeEnum.SWIMMING).build();

		this.mockMvc
				.perform(post("/athlete/add").content(getJsonString(validAthlete))
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().is2xxSuccessful())
				.andExpect(jsonPath("$.message", is(ATHLETE_CREATED_SUCCESSFULLY)));
	}

	@Test
	public void shouldReturnSucessWhenUpdatingExistingAthlete() throws Exception {
		Athlete existingAthlete = Athlete.builder().firstName("Valid").lastName("Athlete")
				.primarySport(SportTypeEnum.CYCLING).secondarySport(SportTypeEnum.SWIMMING).build();

		when(athleteService.getAthlete(ArgumentMatchers.anyInt())).thenReturn(existingAthlete);

		Athlete updatedAthlete = existingAthlete.toBuilder().firstName(UPDATED_FIRST_NAME).lastName(UPDATED_LAST_NAME)
				.primarySport(SportTypeEnum.SWIMMING).secondarySport(null).build();
		
		when(athleteService.saveOrUpdateAthlete(ArgumentMatchers.any((Athlete.class)))).thenReturn(updatedAthlete);
		
		this.mockMvc
				.perform(put("/athlete/update/1").content(getJsonString(updatedAthlete))
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().is2xxSuccessful())
				.andExpect(jsonPath("$.message", is(ATHLETE_UPDATED_SUCCESSFULLY)))
				.andExpect(jsonPath("$.athlete.firstName", is(UPDATED_FIRST_NAME)))
				.andExpect(jsonPath("$.athlete.lastName", is(UPDATED_LAST_NAME)))
				.andExpect(jsonPath("$.athlete.primarySport", is(SportTypeEnum.SWIMMING.toString())))
				.andExpect(jsonPath("$.athlete.secondarySport").doesNotExist());
	}

	@Test
	public void shouldReturnNotFoundErrorWhenFetchingAthleteThatDoesNotExist() throws Exception {
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
