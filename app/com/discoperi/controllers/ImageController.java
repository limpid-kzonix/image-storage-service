package com.discoperi.controllers;

import com.discoperi.model.mongo.entities.Employee;
import com.discoperi.model.service.EmployeeService;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Harmeet Singh(Taara) on 27/12/16.
 */
@Singleton
public class ImageController extends Controller {

  private EmployeeService employeeService;

  @Inject
  public ImageController( EmployeeService employeeService ){
    this.employeeService = employeeService;
  }


  public Result index(){

    List<Employee> list = new ArrayList<>(  );
    for ( int i=0; i<10;i++ ){
      Employee employee = new Employee();
      employee.setName( "ME" + i );
      employee.setAge( 22 + i);
      employee.setSex( "MALE " + i );

      employeeService.saveNewEmployee(  employee );
      list.add( employee );
    }


    return ok( Json.toJson( employeeService.findAllEmployees() ));
  }
}
