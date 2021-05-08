package com.supersapiens.athlete.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.supersapiens.athlete.model.Athlete;

@Repository
public interface AthleteInMemoryRepository  extends CrudRepository<Athlete, Long>{
    // TODO - Use any in-memory data structure (Map, Set, in-memory database, etc.)
    //  to persist the athlete information. You are welcome to use other choices/frameworks as well!
}
