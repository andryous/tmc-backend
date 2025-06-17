package org.example.themovingcompany.config;

import jakarta.annotation.PostConstruct;
import org.example.themovingcompany.model.*;
import org.example.themovingcompany.model.enums.OrderStatus;
import org.example.themovingcompany.model.enums.PersonRole;
import org.example.themovingcompany.model.enums.ServiceType;
import org.example.themovingcompany.repository.OrderRepository;
import org.example.themovingcompany.repository.PersonRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataInitializer {

    private final PersonRepository personRepository;
    private final OrderRepository orderRepository;

    //CONSTRUCTOR
    public DataInitializer(PersonRepository personRepository, OrderRepository orderRepository) {
        this.personRepository = personRepository;
        this.orderRepository = orderRepository;
    }

    @PostConstruct
    public void init() {
        // Check if data already exists
        if (personRepository.count() > 0 || orderRepository.count() > 0) return;

        // Create customer
        Person customer = new Person();
        customer.setFirstName("Lucas");
        customer.setLastName("Hansen");
        customer.setEmail("lucas.hansen@example.com");
        customer.setPhoneNumber("+4740010020");
        customer.setAddress("Storgata 45, Oslo");
        customer.setPersonRole(PersonRole.CUSTOMER);
        personRepository.save(customer);

        // Create consultant
        Person consultant = new Person();
        consultant.setFirstName("Elena");
        consultant.setLastName("Sørensen");
        consultant.setEmail("elena.sorensen@example.com");
        consultant.setPhoneNumber("+4740090080");
        consultant.setAddress("Frognerveien 12, Oslo");
        consultant.setPersonRole(PersonRole.CONSULTANT);
        personRepository.save(consultant);

        // Create order
        Order order = new Order();
        order.setFromAddress("Rosenkrantz gate 15, Oslo");
        order.setToAddress("Kongens gate 30, Oslo");
        order.setServiceType(ServiceType.MOVING);
        order.setStartDate(LocalDate.of(2025, 6, 20));
        order.setEndDate(LocalDate.of(2025, 6, 21));
        order.setNote("Handle fragile items with care");
        order.setStatus(OrderStatus.PENDING);
        order.setCustomer(customer);
        order.setConsultant(consultant);
        orderRepository.save(order);
    }
}
