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
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long customerId;
	private String firstName;
	private String lastName;
	private String email;
	private String phone;
	private String address;
	private String city;
	private String state;
	private String zip;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToMany(mappedBy = "customers", cascade = CascadeType.PERSIST)
	private Set<Dealership> dealershipId = new HashSet<Dealership>();
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Vehicle> vehicles = new HashSet<Vehicle>();
}
