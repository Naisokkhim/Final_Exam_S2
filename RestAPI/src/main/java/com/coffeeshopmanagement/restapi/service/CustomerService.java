package com.coffeeshopmanagement.restapi.service;

import com.coffeeshopmanagement.restapi.model.Customer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class CustomerService {
    private List<Customer> customers = new ArrayList<>();
    private AtomicLong counter = new AtomicLong();

    public Customer addCustomer(Customer customer) {
        customer.setId(counter.incrementAndGet());
        customers.add(customer);
        return customer;
    }

    public Optional<Customer> findCustomerById(Long id) {
        return customers.stream().filter(c -> c.getId().equals(id)).findFirst();
    }

    public List<Customer> getAllCustomers() {
        return new ArrayList<>(customers);
    }

    public Optional<Customer> updateCustomer(Long id, Customer updatedCustomer) {
        return findCustomerById(id).map(customer -> {
            customer.setName(updatedCustomer.getName());
            customer.setEmail(updatedCustomer.getEmail());
            return customer;
        });
    }
}
