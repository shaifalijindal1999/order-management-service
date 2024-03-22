package com.ordermanagementservice.repositories;

import com.ordermanagementservice.database.schema.CustomerData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;


// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
@Component
public interface CustomerDataRepository extends CrudRepository<CustomerData, String> {
 // String is type of ID
}

