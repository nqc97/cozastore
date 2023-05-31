package com.cybersoft.cozastore.service.imp;

import com.cybersoft.cozastore.payload.response.ProductResponse;
import org.springframework.stereotype.Service;

import java.util.List;

public interface IProductService {

     List<ProductResponse> getProductByCategoryId(int id);

}
