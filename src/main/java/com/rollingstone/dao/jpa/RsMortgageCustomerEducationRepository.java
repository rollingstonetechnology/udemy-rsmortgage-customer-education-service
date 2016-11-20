package com.rollingstone.dao.jpa;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.rollingstone.domain.Contact;
import com.rollingstone.domain.Customer;
import com.rollingstone.domain.Education;



public interface RsMortgageCustomerEducationRepository extends PagingAndSortingRepository<Education, Long> {
    List<Education> findCustomerEducationByCustomer(Customer customer);

    Page findAll(Pageable pageable);
}
