package com.cybersoft.cozastore.controller;

import com.cybersoft.cozastore.payload.request.ProductResquest;
import com.cybersoft.cozastore.payload.response.BaseResponse;
import com.cybersoft.cozastore.service.imp.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

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

    /**
     * Có 2 cách làm để upload file chính
     * Cách 1 : Chuyển file về dang base64
     *        - Từ file chuyển thành chuỗi xong rồi đẩy chuỗi lên server
     *        - Từ chuỗi của file đã được chuyển, chuyển chuỗi đó lại thành file
     *   !! Ưu điểm : Vì file đã được chuyển thành chuỗi nên lưu trữ được dưới dạng chuỗi
     *   !! Nhược điểm : Kích thước file sẽ tăng khoảng x1.5
     *
     * Cách 2 : Sử dụng multipartfile
     *       - Mở 1 luồng đọc vào file ( stream )
     *       -
     */

    @PostMapping("")
    public ResponseEntity<?> addProduct(@Valid ProductResquest productResquest) {


        // CHuyển file về chuỗi base64
//        byte[] filename = file.getBytes();
//        String base64 = base64.getEncoder().encodeToString(filename);

//        // lấy tên file và đuôi file
//        String filename = file.getOriginalFilename();
//        // Đường dẫn lưu trữ file
//        String roodFolder = "C:\\CyberSoft_LapTrinhNenTang\\git01\\image";
//        Path pathRoot = Paths.get(roodFolder);
//
//        // Nếu đường dẫn lưu trữ k tồn tại
//        if (!Files.exists(pathRoot)){
//            // Tạo folder
//            Files.createDirectory(pathRoot);
//        }
//
//        // resolve <=> /
//        // pathRoot.resolve(filename) <=> "C:\\CyberSoft_LapTrinhNenTang\\git01\\image\\filename.png"
//        Files.copy(file.getInputStream(),pathRoot.resolve(filename), StandardCopyOption.REPLACE_EXISTING);

        String fileName = productResquest.getFile().getOriginalFilename();
        try {
            String rootFolder = "C:\\CyberSoft_LapTrinhNenTang\\git01\\image";
            Path pathRoot = Paths.get(rootFolder);
            if (!Files.exists(pathRoot)){
                Files.createDirectory(pathRoot);
            }
            Files.copy(productResquest.getFile().getInputStream(),pathRoot.resolve(fileName),StandardCopyOption.REPLACE_EXISTING);
            iProductService.addProduct(productResquest);



        }catch (Exception e){

        }

        return new ResponseEntity<>(fileName,HttpStatus.OK);
    }



}
