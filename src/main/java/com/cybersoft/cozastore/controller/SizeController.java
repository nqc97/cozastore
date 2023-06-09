package com.cybersoft.cozastore.controller;

import com.cybersoft.cozastore.payload.response.BaseResponse;
import com.cybersoft.cozastore.service.imp.ISizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/size")
public class SizeController {

    @Autowired
    ISizeService iSizeService;

    @GetMapping("")
    public ResponseEntity<?> getAllSize(){
        BaseResponse response = new BaseResponse();
        response.setData(iSizeService.getAllSize());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
