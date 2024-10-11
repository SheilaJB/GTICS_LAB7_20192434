package org.example.lab07_20192434.Repository;

import org.example.lab07_20192434.Entity.Obras;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ObraRepository extends JpaRepository<Obras, Integer> {
}
