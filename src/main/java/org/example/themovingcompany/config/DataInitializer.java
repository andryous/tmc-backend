// File: src/main/java/org/example/themovingcompany/config/DataInitializer.java
package org.example.themovingcompany.config;

import org.example.themovingcompany.model.Order;
import org.example.themovingcompany.model.OrderItem;
import org.example.themovingcompany.model.Person;
import org.example.themovingcompany.model.enums.OrderStatus;
import org.example.themovingcompany.model.enums.PersonRole;
import org.example.themovingcompany.model.enums.ServiceType;
import org.example.themovingcompany.repository.OrderRepository;
import org.example.themovingcompany.repository.PersonRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.LocalDate;
import java.util.List;

@Configuration
@Profile("dev")
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(PersonRepository personRepository, OrderRepository orderRepository) {
        return args -> {
            // FIX: Only add data if the database is empty (specifically, if there are no persons).
            if (personRepository.count() == 0) {
                System.out.println("Database is empty. Initializing with test data...");

                // --- Create Persons (Customers and Consultants) ---
                Person customer1 = new Person();
                customer1.setFirstName("Carlos");
                customer1.setLastName("Ramirez");
                customer1.setEmail("carlos.ramirez@example.com");
                customer1.setPhoneNumber("+123456789");
                customer1.setAddress("123 Main St, Austin, TX");
                customer1.setPersonRole(PersonRole.CUSTOMER);

                Person customer2 = new Person();
                customer2.setFirstName("Aisha");
                customer2.setLastName("Khan");
                customer2.setEmail("aisha.khan@example.com");
                customer2.setPhoneNumber("+987654321");
                customer2.setAddress("14 Oxford St, London, UK");
                customer2.setPersonRole(PersonRole.CUSTOMER);

                Person consultant1 = new Person();
                consultant1.setFirstName("John");
                consultant1.setLastName("Doe");
                consultant1.setEmail("john.doe@example.com");
                consultant1.setPhoneNumber("+112233445");
                consultant1.setAddress("456 Oak Ave, Austin, TX");
                consultant1.setPersonRole(PersonRole.CONSULTANT);

                Person consultant2 = new Person();
                consultant2.setFirstName("Karl");
                consultant2.setLastName("Nilssen");
                consultant2.setEmail("karl.nilssen@example.com");
                consultant2.setPhoneNumber("+556677889");
                consultant2.setAddress("1 Nydalen Alle, Oslo, Norway");
                consultant2.setPersonRole(PersonRole.CONSULTANT);

                personRepository.saveAll(List.of(customer1, customer2, consultant1, consultant2));

                // --- Create Order 1 (Multi-service for Carlos) ---
                Order order1 = new Order();
                order1.setCustomer(customer1);
                order1.setConsultant(consultant2);
                order1.setStatus(OrderStatus.IN_PROGRESS);

                OrderItem item1_moving = new OrderItem();
                item1_moving.setServiceType(ServiceType.MOVING);
                item1_moving.setStatus(OrderStatus.COMPLETED);
                item1_moving.setFromAddress("123 Main St, Austin, TX");
                item1_moving.setToAddress("900 Red River St, Austin, TX");
                item1_moving.setStartDate(LocalDate.now().plusDays(1));
                item1_moving.setEndDate(LocalDate.now().plusDays(2));
                item1_moving.setNote("Main move");
                order1.addItem(item1_moving);

                OrderItem item1_packing = new OrderItem();
                item1_packing.setServiceType(ServiceType.PACKING);
                item1_packing.setStatus(OrderStatus.IN_PROGRESS);
                item1_packing.setFromAddress("123 Main St, Austin, TX");
                item1_packing.setToAddress("N/A");
                item1_packing.setStartDate(LocalDate.now());
                item1_packing.setEndDate(LocalDate.now());
                item1_packing.setNote("Pack kitchen first");
                order1.addItem(item1_packing);

                orderRepository.save(order1);

                // --- Create Order 2 (Single service for Aisha) ---
                Order order2 = new Order();
                order2.setCustomer(customer2);
                order2.setConsultant(consultant1);
                order2.setStatus(OrderStatus.PENDING);

                OrderItem item2_cleaning = new OrderItem();
                item2_cleaning.setServiceType(ServiceType.CLEANING);
                item2_cleaning.setStatus(OrderStatus.PENDING);
                item2_cleaning.setFromAddress("14 Oxford St, London, UK");
                item2_cleaning.setToAddress("N/A");
                item2_cleaning.setStartDate(LocalDate.now().plusWeeks(1));
                item2_cleaning.setEndDate(LocalDate.now().plusWeeks(1));
                order2.addItem(item2_cleaning);

                orderRepository.save(order2);
                System.out.println("Test data initialization complete.");
            } else {
                System.out.println("Database already contains data. Skipping initialization.");
            }
        };
    }
}