package com.amigoscode.customer;

import com.amigoscode.exception.DuplicateResourceException;
import com.amigoscode.exception.RequestValidationException;
import com.amigoscode.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    private final CustomerDao customerDao;

    public CustomerService(@Qualifier("jdbc") CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public List<Customer> getAllCustomers(){
        return customerDao.selectAllCustomers();
    }

    public Customer getCustomer(Integer id){
        return customerDao.selectCustomerById(id)
                .orElseThrow(
                () -> new ResourceNotFoundException("customer with id [%s] not found".formatted(id)));
    }
    public void addCustomer(CustomerRegistrationRequest customerRegistrationRequest){
        //check if email exists
        String email = customerRegistrationRequest.email();
        if (customerDao.existsPersonWithEmail(email)){
            throw new DuplicateResourceException("email already taken");
        }
        //add
        Customer customer= new Customer(
                customerRegistrationRequest.name(),
                customerRegistrationRequest.email(),
                customerRegistrationRequest.age()
        );
        customerDao.insertCustomer(customer);
    }
    public void deleteCustomerById(Integer customerId){
        //check if id exists
        Integer id = customerId;
        if (!customerDao.existsPersonWithId(id)){
            throw new ResourceNotFoundException("customer Id [%s] not found".formatted(customerId));
        }
        //delete
        customerDao.deleteCustomerById(customerId);
    }

    public void updateCustomer(Integer customerId, CustomerUpdateRequest updateRequest){
        //for JPA .getReferenceById(customerId) as it does not bring object
        Customer customer = getCustomer(customerId);
        boolean changes = false;


        if(updateRequest.name()!=null && !updateRequest.name().equals(customer.getName())){
            customer.setName(updateRequest.name());
            changes=true;
        }
        if(updateRequest.age()!=null && !updateRequest.age().equals(customer.getAge())){
            customer.setAge(updateRequest.age());
            changes=true;
        }
        if(updateRequest.email()!=null && !updateRequest.email().equals(customer.getEmail())){
            if (customerDao.existsPersonWithEmail(updateRequest.email())){
                throw new DuplicateResourceException("email already taken");
            }
            customer.setEmail(updateRequest.email());
            changes=true;
        }
        if(!changes){
            throw new RequestValidationException("no changes found");
        }
        //update
        customerDao.updateCustomer(customer);
    }
}
