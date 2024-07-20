/*
// Program: Promineo Tech Backend Java Back End Development Course
// Author:  Samuel Ajao
// Subject:  Spring Boot API FINAL PROJECT - Car Dealership
// Create Date: July 17, 2024
//
*/
package car.dealership.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class Dealership {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long dealershipId;
	
	private String name;
	private String address;
	private String city;
	private String state;
	private String zip;
	private String phone;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(mappedBy = "dealership", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Vehicle> vehicles = new HashSet<Vehicle>();
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(mappedBy = "dealership", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Employee> employees = new HashSet<Employee>();
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "dealership_customer", joinColumns = 
	@JoinColumn(name = "dealership_id"), inverseJoinColumns = 
	@JoinColumn(name = "customer_id"))
	private Set<Customer> customers = new HashSet<Customer>();
}
