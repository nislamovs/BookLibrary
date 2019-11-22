package com.booklibrary.app.services.stuff;

import com.booklibrary.app.models.sql.RandomData;
import com.booklibrary.app.repository.sql.stuff.TestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TestService {

    private final TestRepository testRepository;

    @Transactional
    public String findUser(String name) {
        return testRepository.findByFirstname("dan").toString();
    }

    @Transactional
    public RandomData saveUser(RandomData randomData) {
        return testRepository.save(randomData);
    }

}
