package com.booklibrary.app.repository.sql.stuff;

import com.booklibrary.app.models.sql.RandomData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRepository extends JpaRepository<RandomData, Long> {
    RandomData findByFirstname(String firstname);
}
