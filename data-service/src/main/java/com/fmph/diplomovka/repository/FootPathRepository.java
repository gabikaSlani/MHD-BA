package com.fmph.diplomovka.repository;

import com.fmph.diplomovka.model.FootPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FootPathRepository extends JpaRepository<FootPath, Long> {

}
