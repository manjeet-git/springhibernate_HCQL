package com.hrm.service.repository;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hrm.entity.Employee;

@Repository
public class EmployeeRepository {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	
	
	public Session getSession() {
		Session session=sessionFactory.getCurrentSession();
		if(session==null) {
			session=sessionFactory.openSession();
		}
		return session;
	}
	
	
	
	public Employee getEmployeeById(int id) {
		
		  Session session=getSession(); 
		  
		/*
		 * Criteria criteria=session.createCriteria(Employee.class);
		 * criteria.add(Restrictions.eq("id",id)); Employee employee=(Employee)
		 * criteria.uniqueResult();
		 * 
		 * return employee;
		 */
		  
		  CriteriaBuilder builder=session.getCriteriaBuilder();
			CriteriaQuery<Employee> criteriaQ=builder.createQuery(Employee.class);
			Root<Employee> root=criteriaQ.from(Employee.class);
			criteriaQ.where(builder.equal(root.get("id"), id));
			List<Employee>employees=session.createQuery(criteriaQ).getResultList();
			if(employees.size()==1)
				return employees.get(0);
			return null;
			
		 
	}
	
	
	public List<Employee> getEmployees() {
		Session session=getSession();
		CriteriaBuilder builder=session.getCriteriaBuilder();
		CriteriaQuery<Employee> criteriaQ=builder.createQuery(Employee.class);
		criteriaQ.from(Employee.class);
		
		List<Employee> emoloyees=session.createQuery(criteriaQ).getResultList();
		return emoloyees;
		
		/*
		 * Criteria criteria=session.createCriteria(Employee.class); List<Employee>
		 * emoloyees= criteria.list(); return emoloyees;
		 */
		
	}
	
	public List<Employee> getEmployeeBySalAndNameStarts(double sal,String nameStarts){
		Session session=getSession();
		/*
		 * Criteria criteria=session.createCriteria(Employee.class);
		 * criteria.add(Restrictions.gt("salary", sal));
		 * criteria.add(Restrictions.like("empName", nameStarts+"%")); return
		 * criteria.list();
		 */
		
		CriteriaBuilder builder=session.getCriteriaBuilder();
		CriteriaQuery<Employee> criteria=builder.createQuery(Employee.class);
		Root<Employee> root=criteria.from(Employee.class);
		criteria.where(builder.gt(root.get("salary"),sal));
		criteria.where(builder.like(root.get("empName"), "%"+nameStarts+"%"));
		criteria.orderBy(builder.asc(root.get("empName")));
		
		return session.createQuery(criteria).getResultList();
		
		
	}
		


}
