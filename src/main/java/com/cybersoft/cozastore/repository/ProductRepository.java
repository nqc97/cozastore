package com.cybersoft.cozastore.repository;

import com.cybersoft.cozastore.entity.ProductEntity;
import com.cybersoft.cozastore.payload.request.ProductResquest;
import com.cybersoft.cozastore.payload.response.ProductResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {

    List<ProductEntity> findByCategoryId(int id);

}
