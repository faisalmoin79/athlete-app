package com.supersapiens.athlete;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.supersapiens.athlete.enums.SportTypeEnum;
import com.supersapiens.athlete.model.Athlete;
import com.supersapiens.athlete.service.AthleteService;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class AthleteApplication {

    public static void main(String[] args) {

        SpringApplication.run(AthleteApplication.class, args);
    }
    

    @Bean
    public CommandLineRunner demo(AthleteService service) {
      return (args) -> {
    	 log.debug("Saving Atheletes......");
        // save a few Athletes
        Athlete faisalKhan = Athlete.builder()
		.firstName("Faisal")
		.lastName("Khan")
		.primarySport(SportTypeEnum.RUNNING)
		.secondarySport(SportTypeEnum.CYCLING).build();
		service.saveOrUpdateAthlete(
        		faisalKhan);
        log.debug("saved Athlete Faisal Khan...");
        
        Athlete ellenAbarr = Athlete.builder()
		.firstName("Ellen")
		.lastName("Abarr")
		.primarySport(SportTypeEnum.SWIMMING)
		.secondarySport(SportTypeEnum.RUNNING).build();
		service.saveOrUpdateAthlete(
        		ellenAbarr);
        log.debug("saved Athlete Ellen Abarr...");
        
        Athlete johnSmith = Athlete.builder()
		.firstName("John")
		.lastName("Smith")
		.primarySport(SportTypeEnum.SWIMMING).build();
		service.saveOrUpdateAthlete(
        		johnSmith);
        log.debug("saved Athlete John Smith...");

        // fetch Athletes by Id
        log.debug("Athelete with id 1 : {}",service.getAthlete(1));
        log.debug("Athelete with id 2 : {}",service.getAthlete(2));
        log.debug("Athelete with id 3 : {}",service.getAthlete(3));
        
        // update Athletes
        log.debug("Updating Faisal Khan's secondary sport...");
        Athlete faisaKhanUpdated = faisalKhan.toBuilder().secondarySport(SportTypeEnum.SWIMMING).build();        
        service.saveOrUpdateAthlete(faisaKhanUpdated);
        log.debug("Getting updated record for Faisal Khan : {}",service.getAthlete(1));
        
        
        // delete Athletes
        log.debug("Deleting Ellen Abbar's record...");
        service.deleteAthlete(2);
        
        log.debug("Getting updated record for Faisal Khan : {}",service.getAthlete(1));

      };
    }

}
