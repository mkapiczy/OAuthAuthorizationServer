package com.github.britter.springbootherokudemo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacebookRepository extends JpaRepository<FaceBookPojo, Long> {
}
