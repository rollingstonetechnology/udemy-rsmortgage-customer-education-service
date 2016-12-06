package com.rollingstone.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.rollingstone.dao.jpa.RsMortgageCustomerEducationRepository;
import com.rollingstone.domain.Contact;
import com.rollingstone.domain.Customer;
import com.rollingstone.domain.Education;

/*
 * Service class to do CRUD for Customer Education through JPS Repository
 */
@Service
public class RsMortgageCustomerEducationService {

    private static final Logger log = LoggerFactory.getLogger(RsMortgageCustomerEducationService.class);

    @Autowired
    private RsMortgageCustomerEducationRepository customerEducationRepository;

    @Autowired
    CounterService counterService;

    @Autowired
    GaugeService gaugeService;
    
    @Autowired
   	private CustomerClient customerClient;

    public RsMortgageCustomerEducationService() {
    }

    public Education createEducation(Education education) throws Exception {
    	Education createdEducation = null;
    	if (education != null && education.getCustomer() != null){
    		
    		log.info("In service education create"+ education.getCustomer().getId());
    		if (customerClient == null){
        		log.info("In customerClient null got customer");
    		}
    		else {
    			log.info("In customerClient not null got customer");
    		}
    		
    		Customer customer = customerClient.getCustomer((new Long(education.getCustomer().getId())));
    		
    		if (customer != null){
    			createdEducation  = customerEducationRepository.save(education);
    		}else {
    			log.info("Invalid Customer");
    			throw new Exception("Invalid Customer");
    		}
    	}
    	else {
    			throw new Exception("Invalid Customer");
    	}
        return createdEducation;
    }

    public Education getEducation(long id) {
        return customerEducationRepository.findOne(id);
    }

    public void updateEducation(Education education) throws Exception {
    	Education createdEducation = null;
    	if (education != null && education.getCustomer() != null){
    		
    		log.info("In service education create"+ education.getCustomer().getId());
    		if (customerClient == null){
        		log.info("In customerClient null got customer");
    		}
    		else {
    			log.info("In customerClient not null got customer");
    		}
    		
    		Customer customer = customerClient.getCustomer((new Long(education.getCustomer().getId())));
    		
    		if (customer != null){
    			createdEducation  = customerEducationRepository.save(education);
    		}else {
    			log.info("Invalid Customer");
    			throw new Exception("Invalid Customer");
    		}
    	}
    	else {
    			throw new Exception("Invalid Customer");
    	}
    }

    public void deleteEducation(Long id) {
    	customerEducationRepository.delete(id);
    }

    public Page<Education> getAllEducationsByPage(Integer page, Integer size) {
        Page pageOfEducations = customerEducationRepository.findAll(new PageRequest(page, size));
        
        // example of adding to the /metrics
        if (size > 50) {
            counterService.increment("com.rollingstone.getAll.largePayload");
        }
        return pageOfEducations;
    }
    
    public List<Education> getAllEducations() {
        Iterable<Education> pageOfEducations = customerEducationRepository.findAll();
        
        List<Education> customerEducations = new ArrayList<Education>();
        
        for (Education contact : pageOfEducations){
        	customerEducations.add(contact);
        }
    	log.info("In Real Service getAllEducations  size :"+customerEducations.size());

    	
        return customerEducations;
    }
    
    public List<Education> getAllEducationsForCustomer(Customer customer) {
        Iterable<Education> pageOfEducations = customerEducationRepository.findCustomerEducationByCustomer(customer);
        
        List<Education> customerEducations = new ArrayList<Education>();
        
        for (Education education : pageOfEducations){
        	customerEducations.add(education);
        }
    	log.info("In Real Service getAllEducations  size :"+customerEducations.size());

    	
        return customerEducations;
    }
}
