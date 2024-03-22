package com.ordermanagementservice.repositories;

import com.ordermanagementservice.database.schema.ProductData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface ProductDataRepository extends CrudRepository<ProductData, String> {

}
