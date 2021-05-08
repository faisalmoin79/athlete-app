package com.supersapiens.athlete.service;

import static com.supersapiens.athlete.constant.AthleteAppConstants.ServiceErrors.ATHLETE_CAN_NOT_BE_NULL;
import static com.supersapiens.athlete.constant.AthleteAppConstants.ServiceErrors.ATHLETE_NOT_FOUND;
import static com.supersapiens.athlete.constant.AthleteAppConstants.ServiceErrors.ID_MUST_BE_GREATER_THAN_1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supersapiens.athlete.exception.AthleteNotFoundException;
import com.supersapiens.athlete.model.Athlete;
import com.supersapiens.athlete.repository.AthleteInMemoryRepository;

@Service
public class AthleteService {
    @Autowired
    private AthleteInMemoryRepository repository;

	public Athlete saveOrUpdateAthlete(Athlete athlete) {
		if(athlete==null) {
			throw new IllegalArgumentException(ATHLETE_CAN_NOT_BE_NULL);
		}
		return repository.save(athlete);
	}


	public void deleteAthlete(int id) {
		if(id<=0) {
			throw new IllegalArgumentException(ID_MUST_BE_GREATER_THAN_1);
		}
		repository.deleteById(Long.valueOf(id));
	}

	public Athlete getAthlete(int id) {
		if(id<=0) {
			throw new IllegalArgumentException(ID_MUST_BE_GREATER_THAN_1);
		}
		return repository.findById(Long.valueOf(id)).orElseThrow(() -> new AthleteNotFoundException(ATHLETE_NOT_FOUND));
	}

}
