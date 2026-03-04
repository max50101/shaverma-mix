package com.example.shaverma_cloud.repository;

import com.example.shaverma_cloud.model.Shaverma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface ShavermaRepository extends JpaRepository<Shaverma,Long> {

}
