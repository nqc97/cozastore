package com.cybersoft.cozastore.controller;


import com.cybersoft.cozastore.payload.response.BaseResponse;
import com.cybersoft.cozastore.service.imp.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    IProductService iProductService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductCategory(@PathVariable int id){
        BaseResponse response = new BaseResponse();
        response.setData(iProductService.getProductByCategoryId(id));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }



}
