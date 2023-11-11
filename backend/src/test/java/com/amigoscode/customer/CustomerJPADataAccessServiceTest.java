package com.amigoscode.customer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;


class CustomerJPADataAccessServiceTest {

    private CustomerJPADataAccessService underTest;
    private AutoCloseable autoCloseable;
    @Mock
    private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new CustomerJPADataAccessService(customerRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void selectAllCustomers() {
        //Given
        //When
        underTest.selectAllCustomers();

        //Then
        Mockito.verify(customerRepository).findAll();
    }

    @Test
    void selectCustomerById() {
        //Given
        int id =1;
        //When
        underTest.selectCustomerById(id);
        //Then
        Mockito.verify(customerRepository).findById(id);
    }

    @Test
    void insertCustomer() {
        //Given
        Customer customer=new Customer("Jack", "jack@gmail.com",23);
        //When
        underTest.insertCustomer(customer);
        //Then
        Mockito.verify(customerRepository).save(customer);

    }

    @Test
    void existsPersonWithEmail() {
        //Given
        String email = "jack@gmail.com";
        //When
        underTest.existsPersonWithEmail(email);
        //Then
        Mockito.verify(customerRepository).existsCustomerByEmail(email);
    }

    @Test
    void deleteCustomerById() {
        //Given
        int id = 1;
        //When
        underTest.deleteCustomerById(id);
        //Then
        Mockito.verify(customerRepository).deleteById(id);
    }

    @Test
    void existsPersonWithId() {
        //Given
        int id =1;
        //When
        underTest.existsPersonWithId(id);
        //Then
        Mockito.verify(customerRepository).existsCustomerById(id);
    }

    @Test
    void updateCustomer() {
        //Given
        Customer update = new Customer("Jack", "jack@gmail.com",23);
        //When
        underTest.updateCustomer(update);
        //Then
        Mockito.verify(customerRepository).save(update);
    }
}