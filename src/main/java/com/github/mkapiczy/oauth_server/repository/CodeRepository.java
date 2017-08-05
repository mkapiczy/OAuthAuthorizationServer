package com.github.mkapiczy.oauth_server.repository;

import com.github.mkapiczy.oauth_server.entity.Code;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CodeRepository extends JpaRepository<Code, Long> {


}
