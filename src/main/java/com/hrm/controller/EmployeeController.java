package com.hrm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hrm.custom.exception.EmployeeNotFoundException;
import com.hrm.entity.Employee;
import com.hrm.service.impl.EmployeeServiceImpl;

@RestController
@RequestMapping("/api/criteria")
public class EmployeeController {
	
	@Autowired
	private EmployeeServiceImpl service;
	
	@GetMapping(value = "/get-employee-by-id/{id}",produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Employee> getEmployeeById(@PathVariable int id) {
		Employee employee=service.showtEmployeeById(id);
		if(employee!=null)
		return new ResponseEntity<Employee>(employee, HttpStatus.OK);
		throw new EmployeeNotFoundException("Employee not found with id : "+id);
	}
	
	@GetMapping(value = "/get-employees",produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<List<Employee>> getEmployeeById() {
		List<Employee> employees=service.showEmployees();
		if(employees.size()>0)
		return new ResponseEntity(employees, HttpStatus.OK);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping(value = "/emp-by-sal-name-starts/{sal}/{nameStarts}")
	public ResponseEntity<List<Employee>> getEmpBySalAndnameStarts(@PathVariable double sal,@PathVariable String nameStarts){
		List<Employee> employees=service.showEmpBySalAndNameStarts(sal,nameStarts);
		if(employees.size()>0) {
			return new ResponseEntity<>(employees,HttpStatus.OK);
		}
		
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
