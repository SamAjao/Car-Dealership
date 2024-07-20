/*
// Program: Promineo Tech Backend Java Back End Development Course
// Author:  Samuel Ajao
// Subject:  Spring Boot API FINAL PROJECT - Car Dealership
// Create Date: July 17, 2024
//
*/
package car.dealership.controller.model;

import java.util.HashSet;
import java.util.Set;

import car.dealership.entity.Customer;
import car.dealership.entity.Dealership;
import car.dealership.entity.Employee;
import car.dealership.entity.Vehicle;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DealershipData {
	private Long dealershipId;
	private String name;
	private String address;
	private String city;
	private String state;
	private String zip;
	private String phone;
	private Set<VehicleData> vehicles = new HashSet<VehicleData>();
	private Set<EmployeeData> employees = new HashSet<EmployeeData>();
	private Set<CustomerData> customers = new HashSet<CustomerData>();
	
	public DealershipData(Dealership dealership) {
		this.dealershipId = dealership.getDealershipId();
		this.name = dealership.getName();
		this.address = dealership.getAddress();
		this.city = dealership.getCity();
		this.state = dealership.getState();
		this.zip = dealership.getZip();
		this.phone = dealership.getPhone();
		
		for(Vehicle vehicle : dealership.getVehicles() ) {
			this.vehicles.add(new VehicleData(vehicle));
		}
		
		for(Employee employee : dealership.getEmployees()) {
			this.employees.add(new EmployeeData(employee));
		}
		
		for(Customer customer : dealership.getCustomers()) {
			this.customers.add(new CustomerData(customer));
		}
	}
	
	@Data
	@NoArgsConstructor
	public static class VehicleData{
		private Long vehicleId;
		private int year;
		private String make;
		private String model;
		private String color;
		private String trim;
		private Customer customer;
		
		public VehicleData(Vehicle vehicle) {
			this.vehicleId = vehicle.getVehicleId();
			this.year = vehicle.getYear();
			this.make = vehicle.getMake();
			this.model = vehicle.getModel();
			this.color = vehicle.getColor();
			this.trim = vehicle.getTrim();
			this.customer = vehicle.getCustomer();
		}	
	} // END VehicleData{} inner class
	
	@Data
	@NoArgsConstructor
	public static class EmployeeData{
		private Long employeeId;
		private String firstName;
		private String lastName;
		private String phone;
		private String jobTitle;
		
		public EmployeeData(Employee employee) {
			this.employeeId = employee.getEmployeeId();
			this.firstName = employee.getFirstName();
			this.lastName = employee.getLastName();
			this.phone = employee.getPhone();
			this.jobTitle = employee.getJobTitle();
		}
	} // END EmployeeData{} inner class
	
	@Data
	@NoArgsConstructor
	public static class CustomerData{
		private Long customerId;
		private String firstName;
		private String lastName;
		private String email;
		private String phone;
		private String address;
		private String city;
		private String state;
		private String zip;
		private Set<VehicleData> vehicles = new HashSet<VehicleData>();
		private Set<DealershipData> dealerships = new HashSet<DealershipData>();
		
		public CustomerData(Customer customer) {
			this.customerId = customer.getCustomerId();
			this.firstName = customer.getFirstName();
			this.lastName = customer.getLastName();
			this.email = customer.getEmail();
			this.phone = customer.getEmail();
			this.address = customer.getAddress();
			this.city = customer.getCity();
			this.state = customer.getState();
			this.zip = customer.getZip();
			
			for(Dealership dealership : customer.getDealerships()) {
				this.dealerships.add(new DealershipData(dealership));
			}
			
			for(Vehicle vehicle : customer.getVehicles()) {
				this.vehicles.add(new VehicleData(vehicle));
			}
		}
	} // END CustomerData{} inner class
	
} // END DealershipData{} class