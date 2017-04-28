package com.discoperi.model.service;

import com.discoperi.model.mongo.entities.Employee;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Harmeet Singh(Taara) on 27/12/16.
 */
public interface EmployeeService {

  @Transactional
  void saveNewEmployee(Employee employee);
  @Transactional
  Employee findEmployeeById(int id);
  @Transactional
  List<Employee> findAllEmployees();
}
