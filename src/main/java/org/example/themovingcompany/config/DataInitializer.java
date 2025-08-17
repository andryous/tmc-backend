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
import java.time.Month;
import java.util.List;

@Configuration
//@Profile("dev")
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(PersonRepository personRepository, OrderRepository orderRepository) {
        return args -> {
            if (personRepository.count() != 0) {
                System.out.println("Database already contains data. Skipping initialization.");
                return;
            }

            System.out.println("Database is empty. Initializing with a rich set of realistic test data...");

            // --- Create Persons (5 Consultants, 10 Customers) ---
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

            personRepository.saveAll(List.of(consultant1, consultant2, consultant3, consultant4, consultant5, customer1, customer2, customer3, customer4, customer5, customer6, customer7, customer8, customer9, customer10));

            // --- Create 12 Orders ---

            // CHANGED: Using default constructor and setters instead of all-args constructor
            Order order1 = new Order();
            order1.setCustomer(customer1);
            order1.setConsultant(consultant1);
            order1.setStatus(OrderStatus.COMPLETED);
            order1.setCreationDate(LocalDate.of(2025, Month.JULY, 15).atStartOfDay());
            order1.addItem(createOrderItem(ServiceType.PACKING, OrderStatus.COMPLETED, "123 Main St, Austin, TX", "N/A", LocalDate.of(2025, Month.JULY, 20), LocalDate.of(2025, Month.JULY, 21), "Full house packing"));
            order1.addItem(createOrderItem(ServiceType.MOVING, OrderStatus.COMPLETED, "123 Main St, Austin, TX", "900 Red River St, Austin, TX", LocalDate.of(2025, Month.JULY, 22), LocalDate.of(2025, Month.JULY, 22), "Handle fragile items with care"));
            order1.addItem(createOrderItem(ServiceType.CLEANING, OrderStatus.COMPLETED, "123 Main St, Austin, TX", "N/A", LocalDate.of(2025, Month.JULY, 23), LocalDate.of(2025, Month.JULY, 23), "Move-out cleaning"));

            Order order2 = new Order();
            order2.setCustomer(customer2);
            order2.setConsultant(consultant3);
            order2.setStatus(OrderStatus.IN_PROGRESS);
            order2.setCreationDate(LocalDate.of(2025, Month.AUGUST, 1).atStartOfDay());
            order2.addItem(createOrderItem(ServiceType.PACKING, OrderStatus.COMPLETED, "14 Oxford St, London, UK", "N/A", LocalDate.of(2025, Month.AUGUST, 5), LocalDate.of(2025, Month.AUGUST, 6), "International packing standards"));
            order2.addItem(createOrderItem(ServiceType.MOVING, OrderStatus.IN_PROGRESS, "14 Oxford St, London, UK", "5th Avenue, New York, NY", LocalDate.of(2025, Month.AUGUST, 10), LocalDate.of(2025, Month.SEPTEMBER, 5), "Sea freight"));

            Order order3 = new Order();
            order3.setCustomer(customer3);
            order3.setConsultant(consultant2);
            order3.setStatus(OrderStatus.PENDING);
            order3.setCreationDate(LocalDate.of(2025, Month.AUGUST, 10).atStartOfDay());
            order3.addItem(createOrderItem(ServiceType.PACKING, OrderStatus.PENDING, "25 Rock St, New York, NY", "N/A", LocalDate.of(2025, Month.AUGUST, 20), LocalDate.of(2025, Month.AUGUST, 20), "Packing of office electronics only"));

            Order order4 = new Order();
            order4.setCustomer(customer4);
            order4.setConsultant(consultant4);
            order4.setStatus(OrderStatus.PENDING);
            order4.setCreationDate(LocalDate.of(2025, Month.AUGUST, 12).atStartOfDay());
            order4.addItem(createOrderItem(ServiceType.MOVING, OrderStatus.PENDING, "50 Palm Ave, Miami, FL", "75 Ocean Drive, Miami, FL", LocalDate.of(2025, Month.AUGUST, 25), LocalDate.of(2025, Month.AUGUST, 25), "Evening move requested"));

            Order order5 = new Order();
            order5.setCustomer(customer5);
            order5.setConsultant(consultant1);
            order5.setStatus(OrderStatus.IN_PROGRESS);
            order5.setCreationDate(LocalDate.of(2025, Month.JULY, 20).atStartOfDay());
            order5.addItem(createOrderItem(ServiceType.PACKING, OrderStatus.COMPLETED, "75 Market St, San Francisco, CA", "N/A", LocalDate.of(2025, Month.AUGUST, 1), LocalDate.of(2025, Month.AUGUST, 5), "Packing 50 workstations"));
            order5.addItem(createOrderItem(ServiceType.PACKING, OrderStatus.IN_PROGRESS, "77 Market St, San Francisco, CA", "N/A", LocalDate.of(2025, Month.AUGUST, 10), LocalDate.of(2025, Month.AUGUST, 14), "Packing server room"));
            order5.addItem(createOrderItem(ServiceType.MOVING, OrderStatus.PENDING, "75 & 77 Market St", "101 Silicon Valley Rd", LocalDate.of(2025, Month.AUGUST, 22), LocalDate.of(2025, Month.AUGUST, 23), "Phase 1 move"));

            Order order6 = new Order();
            order6.setCustomer(customer6);
            order6.setConsultant(consultant5);
            order6.setStatus(OrderStatus.COMPLETED);
            order6.setCreationDate(LocalDate.of(2025, Month.AUGUST, 2).atStartOfDay());
            order6.addItem(createOrderItem(ServiceType.CLEANING, OrderStatus.COMPLETED, "100 Queen St, Sydney, Australia", "N/A", LocalDate.of(2025, Month.AUGUST, 5), LocalDate.of(2025, Month.AUGUST, 5), "Post-renovation cleaning"));

            Order order7 = new Order();
            order7.setCustomer(customer7);
            order7.setConsultant(consultant2);
            order7.setStatus(OrderStatus.CANCELLED);
            order7.setCreationDate(LocalDate.of(2025, Month.JULY, 30).atStartOfDay());
            order7.addItem(createOrderItem(ServiceType.MOVING, OrderStatus.CANCELLED, "200 Bloor St, Toronto, Canada", "250 Bloor St, Toronto, Canada", LocalDate.of(2025, Month.AUGUST, 15), LocalDate.of(2025, Month.AUGUST, 15), "Client cancelled on Aug 8th"));

            Order order8 = new Order();
            order8.setCustomer(customer8);
            order8.setConsultant(consultant1);
            order8.setStatus(OrderStatus.IN_PROGRESS);
            order8.setCreationDate(LocalDate.of(2025, Month.AUGUST, 5).atStartOfDay());
            order8.addItem(createOrderItem(ServiceType.MOVING, OrderStatus.COMPLETED, "300 Lake Shore Dr, Chicago, IL", "Storage Unit #123", LocalDate.of(2025, Month.AUGUST, 10), LocalDate.of(2025, Month.AUGUST, 10), "Move to storage"));
            order8.addItem(createOrderItem(ServiceType.MOVING, OrderStatus.PENDING, "Storage Unit #123", "55 Michigan Ave, Chicago, IL", LocalDate.of(2025, Month.SEPTEMBER, 1), LocalDate.of(2025, Month.SEPTEMBER, 1), "Final move from storage"));

            Order order9 = new Order();
            order9.setCustomer(customer9);
            order9.setConsultant(consultant4);
            order9.setStatus(OrderStatus.PENDING);
            order9.setCreationDate(LocalDate.of(2025, Month.AUGUST, 14).atStartOfDay());
            order9.addItem(createOrderItem(ServiceType.PACKING, OrderStatus.PENDING, "400 Pike St, Seattle, WA", "N/A", LocalDate.of(2025, Month.AUGUST, 28), LocalDate.of(2025, Month.AUGUST, 29), null));
            order9.addItem(createOrderItem(ServiceType.CLEANING, OrderStatus.PENDING, "400 Pike St, Seattle, WA", "N/A", LocalDate.of(2025, Month.SEPTEMBER, 2), LocalDate.of(2025, Month.SEPTEMBER, 2), "Post-packing cleaning"));

            Order order10 = new Order();
            order10.setCustomer(customer10);
            order10.setConsultant(consultant5);
            order10.setStatus(OrderStatus.COMPLETED);
            order10.setCreationDate(LocalDate.of(2025, Month.JULY, 25).atStartOfDay());
            order10.addItem(createOrderItem(ServiceType.MOVING, OrderStatus.COMPLETED, "500 Congress Ave, Austin, TX", "600 Congress Ave, Austin, TX", LocalDate.of(2025, Month.AUGUST, 1), LocalDate.of(2025, Month.AUGUST, 1), null));

            Order order11 = new Order();
            order11.setCustomer(customer3);
            order11.setConsultant(consultant1);
            order11.setStatus(OrderStatus.CANCELLED);
            order11.setCreationDate(LocalDate.of(2025, Month.AUGUST, 1).atStartOfDay());
            order11.addItem(createOrderItem(ServiceType.PACKING, OrderStatus.CANCELLED, "25 Rock St, New York, NY", "N/A", LocalDate.of(2025, Month.AUGUST, 10), LocalDate.of(2025, Month.AUGUST, 10), "Project scope changed."));

            Order order12 = new Order();
            order12.setCustomer(customer5);
            order12.setConsultant(consultant1);
            order12.setStatus(OrderStatus.IN_PROGRESS);
            order12.setCreationDate(LocalDate.of(2025, Month.AUGUST, 12).atStartOfDay());
            order12.addItem(createOrderItem(ServiceType.PACKING, OrderStatus.COMPLETED, "Client's Art Gallery", "N/A", LocalDate.of(2025, Month.AUGUST, 14), LocalDate.of(2025, Month.AUGUST, 15), "Handle art with extreme care."));
            order12.addItem(createOrderItem(ServiceType.MOVING, OrderStatus.IN_PROGRESS, "Client's Art Gallery", "New Museum Downtown", LocalDate.of(2025, Month.AUGUST, 18), LocalDate.of(2025, Month.AUGUST, 19), "Climate controlled transport required."));

            orderRepository.saveAll(List.of(order1, order2, order3, order4, order5, order6, order7, order8, order9, order10, order11, order12));

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