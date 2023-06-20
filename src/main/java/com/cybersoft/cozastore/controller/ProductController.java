package com.cybersoft.cozastore.controller;

import com.cybersoft.cozastore.exception.FileNotFoundException;
import com.cybersoft.cozastore.payload.request.ProductResquest;
import com.cybersoft.cozastore.payload.response.BaseResponse;
import com.cybersoft.cozastore.service.imp.IProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Value("${root.file.path}")
    private String rootPath;

    @Autowired
    IProductService iProductService;

    Logger logger = LoggerFactory.getLogger(ProductController.class);

    @GetMapping("/{id}")
    public ResponseEntity<?> getDetailProduct(@PathVariable int id){

        BaseResponse response = new BaseResponse();
        response.setData(iProductService.getDetailProduct(id));

        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/clear-cache")

    public ResponseEntity<?> clearcache(){
        iProductService.clearCache();
        return new ResponseEntity<>("",HttpStatus.OK);
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<?> getProductByCategory(
            HttpServletRequest request,
            @PathVariable int id){

        logger.trace("Hello Trace Logger");
        logger.debug("Hello Debug");
        logger.info("Hello Info");
        logger.warn("Hello Warning");
        logger.error("Hello Error");

        String hostName = request.getHeader("host");

        BaseResponse response = new BaseResponse();
        response.setData(iProductService.getProductByCategoryId(hostName , id));

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

    @GetMapping("/file/{filename}")
    public ResponseEntity<?> downloadFileProduct(@PathVariable String filename) {

        try{

            // Định nghĩa đường dẫn folder lưu file
            Path path =Paths.get(rootPath);
            // Định nghĩa đường dẫn tới file được lưu
//          <=>  "C:\\CyberSoft_LapTrinhNenTang\\git01\\image\\hinh1.jpg"
            Path pathFile =path.resolve(filename);

            Resource resource = new UrlResource(pathFile.toUri());
            if (resource.exists()|| resource.isReadable()){

                // Cho phép download file
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                        .body(resource);
            }else{
                throw new FileNotFoundException("Không tìm thấy file");
            }
        }catch (Exception e){

            // Lỗi
           throw new FileNotFoundException("Không tìm thấy file");

        }

    }

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
            String rootFolder = rootPath;
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
