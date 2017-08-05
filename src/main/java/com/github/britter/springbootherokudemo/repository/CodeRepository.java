package com.github.britter.springbootherokudemo.repository;

import com.github.britter.springbootherokudemo.entity.Code;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CodeRepository extends JpaRepository<Code, Long> {


}
