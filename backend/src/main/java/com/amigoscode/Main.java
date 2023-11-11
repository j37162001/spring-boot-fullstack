package com.amigoscode;

import com.amigoscode.customer.Customer;
import com.amigoscode.customer.CustomerRepository;
import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

import java.util.Random;


@SpringBootApplication
public class Main {


    public static void main(String[] args) {
//      System.out.println(customers);
/*      Never do this
        CustomerService customerService =
              new CustomerService(new CustomerDataAccessService());
        CustomerController customerController =
              new CustomerController(CustomerService());
 */
//        ConfigurableApplicationContext applicationContext =
        SpringApplication.run(Main.class, args);
    }
//        printBeans(applicationContext);

        @Bean
        CommandLineRunner runner(CustomerRepository customerRepository){
        return args ->{
            var faker =new Faker();
            Random random =new Random();
            Name name = faker.name();
            String firstname = name.firstName().toLowerCase();
            String lastname = name.lastName().toLowerCase();
            Customer customer = new Customer(
                    firstname + " " + lastname,
                    firstname +"." + lastname + "@gmail.com",
                    random.nextInt(16,90)
                    );

            customerRepository.save(customer);
        };
        }


    @Bean("foo")
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
//    @RequestScope()
    public Foo getFoo() {
        return new Foo("bar");
    }

    record Foo(String name) {
    }

    public static void printBeans(ConfigurableApplicationContext ctx) {
        String[] beanDefinitionNames = ctx.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            System.out.println(beanDefinitionName);
        }
    }
}