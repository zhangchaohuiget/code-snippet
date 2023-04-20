package com.app.service;

import com.app.dao.entity.Customer;
import com.app.dao.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * CustomerService
 */
@Service
public class CustomerService {
    @Resource
    private CustomerRepository repository;

    /**
     * 删除所有的客户
     */
    public void deleteAll() {
        repository.deleteAll();
    }

    /**
     * 保存客户
     *
     * @param customer 客户
     */
    public void save(Customer customer) {
        repository.save(customer);
    }

    /**
     * 查询所有客户列表
     *
     * @return 客户列表
     */
    public Iterable<Customer> findAll() {
        return repository.findAll();
    }

    /**
     * 通过名查找某个客户
     *
     * @param firstName
     * @return
     */
    public Customer findByFirstName(String firstName) {
        return repository.findByFirstName(firstName);
    }

    /**
     * 通过姓查找客户列表
     *
     * @param lastName
     * @return
     */
    public List<Customer> findByLastName(String lastName) {
        return repository.findByLastName(lastName);
    }

}
