package com.cybersoft.cozastore.repository;

import com.cybersoft.cozastore.entity.SizeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SizeRepository extends JpaRepository<SizeEntity,Integer> {

}
