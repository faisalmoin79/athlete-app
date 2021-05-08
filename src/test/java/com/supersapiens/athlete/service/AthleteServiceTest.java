package com.supersapiens.athlete.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.supersapiens.athlete.exception.AthleteNotFoundException;
import com.supersapiens.athlete.model.Athlete;
import com.supersapiens.athlete.repository.AthleteInMemoryRepository;
import static com.supersapiens.athlete.constant.AthleteAppConstants.ServiceErrors.ATHLETE_CAN_NOT_BE_NULL;
import static com.supersapiens.athlete.constant.AthleteAppConstants.ServiceErrors.ATHLETE_NOT_FOUND;
import static com.supersapiens.athlete.constant.AthleteAppConstants.ServiceErrors.ID_MUST_BE_GREATER_THAN_1;

@ExtendWith(MockitoExtension.class)


public class AthleteServiceTest {

	@InjectMocks
	private AthleteService athleteService;
	
	@Mock 
	private AthleteInMemoryRepository repository;
	
	@Test
	public void shouldReturnExceptionWhenAthleteIsNull() throws Exception {
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			athleteService.saveOrUpdateAthlete(null);
		});
		assertTrue(ATHLETE_CAN_NOT_BE_NULL.contains(exception.getMessage()));
	}
	
	@Test
	public void shouldSaveAthleteWhenAthleteIsNotNull() throws Exception {
		// Given 
		Athlete mockedAthlete = mock(Athlete.class);
		
		// When
		when(repository.save(ArgumentMatchers.any(Athlete.class))).thenReturn(mockedAthlete);
		
		// Then
		Athlete savedAthlete = athleteService.saveOrUpdateAthlete(mockedAthlete);
		assertEquals(savedAthlete, mockedAthlete);
	}
	
	@Test
	public void shouldReturnExceptionWhenDeletingWithIdLessThanZero() throws Exception {
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			athleteService.deleteAthlete(-1);
		});
		assertTrue(ID_MUST_BE_GREATER_THAN_1.contains(exception.getMessage()));
	}
	
	@Test
	public void shouldReturnExceptionWhenFetchingAthleteWithIdLessThanZero() throws Exception {
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			athleteService.getAthlete(0);
		});
		assertTrue(ID_MUST_BE_GREATER_THAN_1.contains(exception.getMessage()));
	}
	
	@Test
	public void shouldThrowNotFoundExceptionWhenAthleteDoesNotExist() throws Exception {
		Exception exception = assertThrows(AthleteNotFoundException.class, () -> {
			athleteService.getAthlete(1);
		});
		assertTrue(ATHLETE_NOT_FOUND.contains(exception.getMessage()));
	}
	
	@Test
	public void showReturnAthleteWithValidId() throws Exception {
		// Given 
		Athlete mockedAthlete = mock(Athlete.class);
		
		// When
		when(repository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(mockedAthlete));
		
		// Then
		Athlete resultAthlete = athleteService.getAthlete(1);
		assertEquals(resultAthlete, mockedAthlete);
	}

}
