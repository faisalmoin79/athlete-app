package com.supersapiens.athlete.service;

import com.supersapiens.athlete.repository.AthleteInMemoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AthleteService {
    @Autowired
    private AthleteInMemoryRepository repository;

    // TODO: Use AthleteInMemoryRepository
}
