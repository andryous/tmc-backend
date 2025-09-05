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
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(PersonRepository personRepository, OrderRepository orderRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (personRepository.count() != 0) {
                System.out.println("Database already contains data. Skipping initialization.");
                return;
            }

            System.out.println("Database is empty. Initializing with test data...");

            // --- Create Persons (Consultants, Customers) ---
            // This section is unchanged as requested.
            Person authUser = createPerson("Auth", "User", "auth.user@tmc.no", "+000000000", "123 Login Lane", PersonRole.CONSULTANT);
            authUser.setPassword(passwordEncoder.encode("demo123")); // Set the encoded password

            Person consultant1 = createPerson("John", "Doe", "john.doe@tmc.no", "+112233445", "456 Oak Ave, Austin, TX", PersonRole.CONSULTANT);
            Person consultant2 = createPerson("Karl", "Nilssen", "karl.nilssen@tmc.no", "+556677889", "1 Nydalen Alle, Oslo, Norway", PersonRole.CONSULTANT);
            Person consultant3 = createPerson("Maria", "Chen", "maria.chen@tmc.no", "+777888999", "88 Financial Dist, Hong Kong", PersonRole.CONSULTANT);
            Person consultant4 = createPerson("David", "Smith", "david.smith@tmc.no", "+223344556", "10 King St, Toronto, Canada", PersonRole.CONSULTANT);
            Person consultant5 = createPerson("Sofia", "Rossi", "sofia.rossi@tmc.no", "+334455667", "Via del Corso 1, Rome, Italy", PersonRole.CONSULTANT);
            Person customer1 = createPerson("Michael", "Johnson", "michael.j@gmail.com", "+123456789", "123 Main St, Austin, TX", PersonRole.CUSTOMER);
            Person customer2 = createPerson("Emily", "Williams", "emily.w@hotmail.com", "+987654321", "14 Oxford St, London, UK", PersonRole.CUSTOMER);
            Person customer3 = createPerson("James", "Brown", "james.brown@outlook.com", "+111222333", "25 Rock St, New York, NY", PersonRole.CUSTOMER);
            Person customer4 = createPerson("Linda", "Jones", "linda.jones@yahoo.com", "+444555666", "50 Palm Ave, Miami, FL", PersonRole.CUSTOMER);
            Person customer5 = createPerson("Robert", "Miller", "rob.miller@gmail.com", "+555666777", "75 Market St, San Francisco, CA", PersonRole.CUSTOMER);
            Person customer6 = createPerson("Patricia", "Davis", "patricia.d@hotmail.com", "+666777888", "100 Queen St, Sydney, Australia", PersonRole.CUSTOMER);
            Person customer7 = createPerson("Charles", "Wilson", "charlie.w@outlook.com", "+777888999", "200 Bloor St, Toronto, Canada", PersonRole.CUSTOMER);
            Person customer8 = createPerson("Jennifer", "Moore", "jen.moore@gmail.com", "+888999000", "300 Lake Shore Dr, Chicago, IL", PersonRole.CUSTOMER);
            Person customer9 = createPerson("William", "Taylor", "will.taylor@yahoo.com", "+999000111", "400 Pike St, Seattle, WA", PersonRole.CUSTOMER);
            Person customer10 = createPerson("Mary", "Anderson", "mary.a@hotmail.com", "+000111222", "500 Congress Ave, Austin, TX", PersonRole.CUSTOMER);

            personRepository.saveAll(List.of(
                    authUser, consultant1, consultant2, consultant3, consultant4, consultant5,
                    customer1, customer2, customer3, customer4, customer5, customer6, customer7, customer8, customer9, customer10
            ));

            // --- Create 15 New Varied Orders ---
            // All dates are from September 4, 2025 onwards.

            // Order 1: Completed, 3 items
            Order order1 = new Order();
            order1.setCustomer(customer1);
            order1.setConsultant(consultant1);
            order1.setStatus(OrderStatus.COMPLETED);
            order1.setCreationDate(LocalDate.of(2025, Month.SEPTEMBER, 5).atStartOfDay());
            order1.addItem(createOrderItem(ServiceType.PACKING, OrderStatus.COMPLETED, "123 Main St, Austin, TX", "N/A", LocalDate.of(2025, Month.SEPTEMBER, 10), LocalDate.of(2025, Month.SEPTEMBER, 11), "Full house packing"));
            order1.addItem(createOrderItem(ServiceType.MOVING, OrderStatus.COMPLETED, "123 Main St, Austin, TX", "900 Red River St, Austin, TX", LocalDate.of(2025, Month.SEPTEMBER, 12), LocalDate.of(2025, Month.SEPTEMBER, 12), "Handle fragile items with care"));
            order1.addItem(createOrderItem(ServiceType.CLEANING, OrderStatus.COMPLETED, "123 Main St, Austin, TX", "N/A", LocalDate.of(2025, Month.SEPTEMBER, 13), LocalDate.of(2025, Month.SEPTEMBER, 13), "Move-out cleaning"));

            // Order 2: In Progress, 2 items with different statuses
            Order order2 = new Order();
            order2.setCustomer(customer2);
            order2.setConsultant(consultant3);
            order2.setStatus(OrderStatus.IN_PROGRESS);
            order2.setCreationDate(LocalDate.of(2025, Month.SEPTEMBER, 6).atStartOfDay());
            order2.addItem(createOrderItem(ServiceType.PACKING, OrderStatus.COMPLETED, "14 Oxford St, London, UK", "N/A", LocalDate.of(2025, Month.SEPTEMBER, 15), LocalDate.of(2025, Month.SEPTEMBER, 16), "International packing standards"));
            order2.addItem(createOrderItem(ServiceType.MOVING, OrderStatus.IN_PROGRESS, "14 Oxford St, London, UK", "5th Avenue, New York, NY", LocalDate.of(2025, Month.SEPTEMBER, 20), LocalDate.of(2025, Month.OCTOBER, 5), "Sea freight"));

            // Order 3: Pending, 1 item
            Order order3 = new Order();
            order3.setCustomer(customer3);
            order3.setConsultant(consultant2);
            order3.setStatus(OrderStatus.PENDING);
            order3.setCreationDate(LocalDate.of(2025, Month.SEPTEMBER, 7).atStartOfDay());
            order3.addItem(createOrderItem(ServiceType.PACKING, OrderStatus.PENDING, "25 Rock St, New York, NY", "N/A", LocalDate.of(2025, Month.SEPTEMBER, 25), LocalDate.of(2025, Month.SEPTEMBER, 25), "Packing of office electronics only"));

            // Order 4: Cancelled, 1 item
            Order order4 = new Order();
            order4.setCustomer(customer4);
            order4.setConsultant(consultant4);
            order4.setStatus(OrderStatus.CANCELLED);
            order4.setCreationDate(LocalDate.of(2025, Month.SEPTEMBER, 8).atStartOfDay());
            order4.addItem(createOrderItem(ServiceType.MOVING, OrderStatus.CANCELLED, "50 Palm Ave, Miami, FL", "1 Ocean Dr, Miami, FL", LocalDate.of(2025, Month.SEPTEMBER, 14), LocalDate.of(2025, Month.SEPTEMBER, 14), "Cancelled by customer"));

            // Order 5: In Progress, 1 item
            Order order5 = new Order();
            order5.setCustomer(customer5);
            order5.setConsultant(consultant5);
            order5.setStatus(OrderStatus.IN_PROGRESS);
            order5.setCreationDate(LocalDate.of(2025, Month.SEPTEMBER, 9).atStartOfDay());
            order5.addItem(createOrderItem(ServiceType.CLEANING, OrderStatus.IN_PROGRESS, "75 Market St, San Francisco, CA", "N/A", LocalDate.of(2025, Month.SEPTEMBER, 18), LocalDate.of(2025, Month.SEPTEMBER, 19), "Post-renovation cleaning"));

            // Order 6: Completed, 2 items
            Order order6 = new Order();
            order6.setCustomer(customer6);
            order6.setConsultant(consultant1);
            order6.setStatus(OrderStatus.COMPLETED);
            order6.setCreationDate(LocalDate.of(2025, Month.SEPTEMBER, 10).atStartOfDay());
            order6.addItem(createOrderItem(ServiceType.MOVING, OrderStatus.COMPLETED, "100 Queen St, Sydney, Australia", "200 George St, Sydney, Australia", LocalDate.of(2025, Month.SEPTEMBER, 22), LocalDate.of(2025, Month.SEPTEMBER, 23), ""));
            order6.addItem(createOrderItem(ServiceType.CLEANING, OrderStatus.COMPLETED, "200 George St, Sydney, Australia", "N/A", LocalDate.of(2025, Month.SEPTEMBER, 24), LocalDate.of(2025, Month.SEPTEMBER, 24), ""));

            // Order 7: Pending, 2 items
            Order order7 = new Order();
            order7.setCustomer(customer7);
            order7.setConsultant(consultant2);
            order7.setStatus(OrderStatus.PENDING);
            order7.setCreationDate(LocalDate.of(2025, Month.SEPTEMBER, 11).atStartOfDay());
            order7.addItem(createOrderItem(ServiceType.PACKING, OrderStatus.PENDING, "200 Bloor St, Toronto, Canada", "N/A", LocalDate.of(2025, Month.OCTOBER, 1), LocalDate.of(2025, Month.OCTOBER, 2), "Art collection"));
            order7.addItem(createOrderItem(ServiceType.MOVING, OrderStatus.PENDING, "200 Bloor St, Toronto, Canada", "300 Bay St, Toronto, Canada", LocalDate.of(2025, Month.OCTOBER, 3), LocalDate.of(2025, Month.OCTOBER, 3), ""));

            // Order 8: In Progress, 3 items
            Order order8 = new Order();
            order8.setCustomer(customer8);
            order8.setConsultant(consultant3);
            order8.setStatus(OrderStatus.IN_PROGRESS);
            order8.setCreationDate(LocalDate.of(2025, Month.SEPTEMBER, 12).atStartOfDay());
            order8.addItem(createOrderItem(ServiceType.PACKING, OrderStatus.COMPLETED, "300 Lake Shore Dr, Chicago, IL", "N/A", LocalDate.of(2025, Month.SEPTEMBER, 15), LocalDate.of(2025, Month.SEPTEMBER, 16), ""));
            order8.addItem(createOrderItem(ServiceType.MOVING, OrderStatus.IN_PROGRESS, "300 Lake Shore Dr, Chicago, IL", "400 Michigan Ave, Chicago, IL", LocalDate.of(2025, Month.SEPTEMBER, 17), LocalDate.of(2025, Month.SEPTEMBER, 17), ""));
            order8.addItem(createOrderItem(ServiceType.CLEANING, OrderStatus.PENDING, "400 Michigan Ave, Chicago, IL", "N/A", LocalDate.of(2025, Month.SEPTEMBER, 18), LocalDate.of(2025, Month.SEPTEMBER, 18), ""));

            // Order 9: Completed, 1 item
            Order order9 = new Order();
            order9.setCustomer(customer9);
            order9.setConsultant(consultant4);
            order9.setStatus(OrderStatus.COMPLETED);
            order9.setCreationDate(LocalDate.of(2025, Month.SEPTEMBER, 13).atStartOfDay());
            order9.addItem(createOrderItem(ServiceType.MOVING, OrderStatus.COMPLETED, "400 Pike St, Seattle, WA", "500 Pine St, Seattle, WA", LocalDate.of(2025, Month.SEPTEMBER, 20), LocalDate.of(2025, Month.SEPTEMBER, 20), "Local move"));

            // Order 10: Pending, 1 item
            Order order10 = new Order();
            order10.setCustomer(customer10);
            order10.setConsultant(consultant5);
            order10.setStatus(OrderStatus.PENDING);
            order10.setCreationDate(LocalDate.of(2025, Month.SEPTEMBER, 14).atStartOfDay());
            order10.addItem(createOrderItem(ServiceType.PACKING, OrderStatus.PENDING, "500 Congress Ave, Austin, TX", "N/A", LocalDate.of(2025, Month.OCTOBER, 5), LocalDate.of(2025, Month.OCTOBER, 6), "Books and records"));

            // Order 11: In Progress, 1 item
            Order order11 = new Order();
            order11.setCustomer(customer1);
            order11.setConsultant(consultant2);
            order11.setStatus(OrderStatus.IN_PROGRESS);
            order11.setCreationDate(LocalDate.of(2025, Month.SEPTEMBER, 15).atStartOfDay());
            order11.addItem(createOrderItem(ServiceType.MOVING, OrderStatus.IN_PROGRESS, "123 Main St, Austin, TX", "1000 Congress Ave, Austin, TX", LocalDate.of(2025, Month.SEPTEMBER, 26), LocalDate.of(2025, Month.SEPTEMBER, 27), "Urgent"));

            // Order 12: Completed, 2 items
            Order order12 = new Order();
            order12.setCustomer(customer2);
            order12.setConsultant(consultant3);
            order12.setStatus(OrderStatus.COMPLETED);
            order12.setCreationDate(LocalDate.of(2025, Month.SEPTEMBER, 16).atStartOfDay());
            order12.addItem(createOrderItem(ServiceType.PACKING, OrderStatus.COMPLETED, "14 Oxford St, London, UK", "N/A", LocalDate.of(2025, Month.SEPTEMBER, 18), LocalDate.of(2025, Month.SEPTEMBER, 18), ""));
            order12.addItem(createOrderItem(ServiceType.CLEANING, OrderStatus.COMPLETED, "14 Oxford St, London, UK", "N/A", LocalDate.of(2025, Month.SEPTEMBER, 19), LocalDate.of(2025, Month.SEPTEMBER, 19), ""));

            // Order 13: Pending, 3 items
            Order order13 = new Order();
            order13.setCustomer(customer4);
            order13.setConsultant(consultant4);
            order13.setStatus(OrderStatus.PENDING);
            order13.setCreationDate(LocalDate.of(2025, Month.SEPTEMBER, 17).atStartOfDay());
            order13.addItem(createOrderItem(ServiceType.PACKING, OrderStatus.PENDING, "50 Palm Ave, Miami, FL", "N/A", LocalDate.of(2025, Month.OCTOBER, 10), LocalDate.of(2025, Month.OCTOBER, 11), ""));
            order13.addItem(createOrderItem(ServiceType.MOVING, OrderStatus.PENDING, "50 Palm Ave, Miami, FL", "2 Beachfront Rd, Miami, FL", LocalDate.of(2025, Month.OCTOBER, 12), LocalDate.of(2025, Month.OCTOBER, 12), ""));
            order13.addItem(createOrderItem(ServiceType.CLEANING, OrderStatus.PENDING, "2 Beachfront Rd, Miami, FL", "N/A", LocalDate.of(2025, Month.OCTOBER, 13), LocalDate.of(2025, Month.OCTOBER, 13), ""));

            // Order 14: In Progress, 2 items
            Order order14 = new Order();
            order14.setCustomer(customer6);
            order14.setConsultant(consultant5);
            order14.setStatus(OrderStatus.IN_PROGRESS);
            order14.setCreationDate(LocalDate.of(2025, Month.SEPTEMBER, 18).atStartOfDay());
            order14.addItem(createOrderItem(ServiceType.PACKING, OrderStatus.COMPLETED, "100 Queen St, Sydney, Australia", "N/A", LocalDate.of(2025, Month.SEPTEMBER, 20), LocalDate.of(2025, Month.SEPTEMBER, 21), ""));
            order14.addItem(createOrderItem(ServiceType.MOVING, OrderStatus.IN_PROGRESS, "100 Queen St, Sydney, Australia", "300 Pitt St, Sydney, Australia", LocalDate.of(2025, Month.SEPTEMBER, 22), LocalDate.of(2025, Month.SEPTEMBER, 22), ""));

            // Order 15: Completed, 1 item
            Order order15 = new Order();
            order15.setCustomer(customer8);
            order15.setConsultant(consultant1);
            order15.setStatus(OrderStatus.COMPLETED);
            order15.setCreationDate(LocalDate.of(2025, Month.SEPTEMBER, 19).atStartOfDay());
            order15.addItem(createOrderItem(ServiceType.CLEANING, OrderStatus.COMPLETED, "300 Lake Shore Dr, Chicago, IL", "N/A", LocalDate.of(2025, Month.SEPTEMBER, 29), LocalDate.of(2025, Month.SEPTEMBER, 30), "Final deep clean"));


            orderRepository.saveAll(List.of(
                    order1, order2, order3, order4, order5, order6, order7, order8,
                    order9, order10, order11, order12, order13, order14, order15
            ));

            System.out.println("Test data initialization complete. " + personRepository.count() + " persons and " + orderRepository.count() + " orders created.");
        };
    }

    private Person createPerson(String firstName, String lastName, String email, String phone, String address, PersonRole role) {
        Person person = new Person();
        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setEmail(email);
        person.setPhoneNumber(phone);
        person.setAddress(address);
        person.setPersonRole(role);
        return person;
    }

    private OrderItem createOrderItem(ServiceType type, OrderStatus status, String from, String to, LocalDate start, LocalDate end, String note) {
        OrderItem item = new OrderItem();
        item.setServiceType(type);
        item.setStatus(status);
        item.setFromAddress(from);
        item.setToAddress(to);
        item.setStartDate(start);
        item.setEndDate(end);
        item.setNote(note);
        return item;
    }
}