package com.cybersoft.cozastore.service;

import com.cybersoft.cozastore.entity.CountryEntity;
import com.cybersoft.cozastore.entity.OrderDetailEntity;
import com.cybersoft.cozastore.entity.OrderEntity;
import com.cybersoft.cozastore.entity.ids.OrderDetailIds;
import com.cybersoft.cozastore.payload.request.OrderProductRequest;
import com.cybersoft.cozastore.payload.request.OrderRequest;
import com.cybersoft.cozastore.repository.OrderDetailRepository;
import com.cybersoft.cozastore.repository.OrderRepository;
import com.cybersoft.cozastore.service.imp.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class OrderService implements IOrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Override
    public boolean addOrder(OrderRequest orderRequest) {
//      Thêm dữ liệu bảng order
        CountryEntity country = new CountryEntity();
        country.setId(orderRequest.getCountryId());

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setCountry(country);
//        Khi save thành công thì thuộc tính id của OrderEntity sẽ có giá trị
        orderRepository.save(orderEntity);

//        Thêm dữ liệu cho bảng order detail

        for (OrderProductRequest data : orderRequest.getListProduct()){

//            Gắn giá trị cho key trng OrderDetailEntity
            OrderDetailIds ids = new OrderDetailIds();
            ids.setOrderId(orderEntity.getId());
            ids.setProductId(data.getId());

            OrderDetailEntity orderDetailEntity = new OrderDetailEntity();
            orderDetailEntity.setIds(ids);
            orderDetailEntity.setPrice(data.getPrice());
            orderDetailEntity.setQuantity(data.getQuantity());

            orderDetailRepository.save(orderDetailEntity);
        }

        return false;
    }
}
