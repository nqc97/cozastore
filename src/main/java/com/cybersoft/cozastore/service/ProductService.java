package com.cybersoft.cozastore.service;

import com.cybersoft.cozastore.entity.CategoryEntity;
import com.cybersoft.cozastore.entity.ColorEntity;
import com.cybersoft.cozastore.entity.ProductEntity;
import com.cybersoft.cozastore.entity.SizeEntity;
import com.cybersoft.cozastore.payload.request.ProductResquest;
import com.cybersoft.cozastore.payload.response.ProductResponse;
import com.cybersoft.cozastore.repository.ProductRepository;
import com.cybersoft.cozastore.service.imp.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements IProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public ProductResponse getDetailProduct(int id){

        /**
         * Optional : Kiểu dữ liệu giúp tránh trường hợp đối tượng bị null hoặc rỗng
         * isPresent : Kiểm tra đối tượng có giá trị hay không
         * get() : giúp hủy optional đi
         */
        Optional<ProductEntity> product = productRepository.findById(id);
        ProductResponse productResponse = new ProductResponse();
        if(product.isPresent()){

            productResponse.setId(product.get().getId());
            productResponse.setImage(product.get().getImage());
            productResponse.setPrice(product.get().getPrice());
            productResponse.setName(product.get().getName());
            productResponse.setDesc(product.get().getDescription());

        }

        return  productResponse;
    }

//    @Value("${host.name}")
//    private String hostName;

    @Override
    public List<ProductResponse> getProductByCategoryId( String hostName, int id){

        List<ProductEntity> list = productRepository.findByCategoryId(id);
        List<ProductResponse> productResponseList = new ArrayList<>();

        for (ProductEntity data : list){
            ProductResponse productResponse = new ProductResponse();
            productResponse.setId(data.getId());
            productResponse.setName(data.getName());
            productResponse.setImage("http://" + hostName + "/product/file/" + data.getImage());
            productResponse.setPrice(data.getPrice());
            productResponseList.add(productResponse);
        }
        return productResponseList ;
    }

    @Override
    public boolean addProduct(ProductResquest productResquest) {
        try {

            ProductEntity productEntity = new ProductEntity();
            productEntity.setName(productResquest.getName());
            productEntity.setImage(productResquest.getFile().getOriginalFilename());
            productEntity.setPrice(productResquest.getPrice());
            productEntity.setQuantity(productResquest.getQuantity());

            ColorEntity colorEntity = new ColorEntity();
            colorEntity.setId(productResquest.getColorId());

            SizeEntity sizeEntity = new SizeEntity();
            sizeEntity.setId(productResquest.getSizeId());

            CategoryEntity categoryEntity = new CategoryEntity();
            categoryEntity.setId(productResquest.getCategoryId());

            productEntity.setColor(colorEntity);
            productEntity.setSize(sizeEntity);
            productEntity.setCategory(categoryEntity);

            productRepository.save(productEntity);

            return true;

        }catch (Exception e){

            return false;
        }

    }

}
