package com.cybersoft.cozastore.repository;

import com.cybersoft.cozastore.entity.ColorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColorRepository extends JpaRepository<ColorEntity , Integer> {
}
