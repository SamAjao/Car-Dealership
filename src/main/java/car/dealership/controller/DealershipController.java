/*
// Program: Promineo Tech Backend Java Back End Development Course
// Author:  Samuel Ajao
// Subject:  Spring Boot API FINAL PROJECT - Car Dealership
// Create Date: July 18, 2024
//
*/
package car.dealership.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import car.dealership.controller.model.DealershipData;
import car.dealership.controller.model.DealershipData.CustomerData;
import car.dealership.controller.model.DealershipData.EmployeeData;
import car.dealership.controller.model.DealershipData.VehicleData;
import car.dealership.service.DealershipService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/car_dealership")
@Slf4j
public class DealershipController {
	
	@Autowired
	private DealershipService dealershipService;
	
	/*
	 * DEALERSHIP CRUD OPERATIONS
	 */
	
	@PostMapping("/dealership")
	@ResponseStatus(code = HttpStatus.CREATED)
	public DealershipData createDealership(@RequestBody DealershipData dealershipData) {
		log.info("Creating dealership {}", dealershipData);
		return dealershipService.saveDealership(dealershipData);
	}
	
	@PutMapping("/dealership/{dealershipId}")
	public DealershipData updateDealership(@PathVariable Long dealershipId, @RequestBody DealershipData dealershipData) {
		log.info("Updating dealership {}", dealershipData);
		dealershipData.setDealershipId(dealershipId);
		return dealershipService.saveDealership(dealershipData);
	}
	
	@GetMapping("/dealership/{dealershipId}")
	public DealershipData retrieveDealership(@PathVariable Long dealershipId) {
		log.info("Retrieving dealership with ID={}", dealershipId);
		
		return dealershipService.retrieveDealershipById(dealershipId);
	}
	
	@GetMapping("/dealership")
	public List<DealershipData> retrieveAllDealerships(){
		log.info("Retrieving all dealerships...");
		return dealershipService.retrieveAllDealerships();
	}
	
	@DeleteMapping("/dealership")
	public void deleteAllDealerships() {
		log.info("Attempting to delete all dealerships...");
		throw new UnsupportedOperationException("Deleting all dealerships is not allowed!!!");
	}
	
	@DeleteMapping("/dealership/{dealershipId}")
	public Map<String, String> deleteDealershipById(@PathVariable Long dealershipId){
		log.info("Deleting dealership with ID={}", dealershipId);
		
		dealershipService.deleteDealershipById(dealershipId);
		
		return Map.of("message", "Deletion of dealership with ID=" + dealershipId + " was successful");
	}
	
	/*
	 * EMPLOYEE CRUD OPERATIONS
	 */
	
	@PostMapping("/dealership/{dealershipId}/employee")
	@ResponseStatus(code = HttpStatus.CREATED)
	public EmployeeData insertEmployee(@PathVariable Long dealershipId, @RequestBody EmployeeData employeeData) {
		log.info("Creating employee {}", employeeData);
		
		return dealershipService.saveEmployee(dealershipId, employeeData);
	}
	
	@PutMapping("/dealership/{dealershipId}/employee/{employeeId}")
	public EmployeeData updateEmployee(@PathVariable Long dealershipId, @RequestBody EmployeeData employeeData,
			@PathVariable Long employeeId) {
		log.info("Updating employee {} at dealershipID={}", employeeData, dealershipId);
		employeeData.setEmployeeId(employeeId);
		return dealershipService.saveEmployee(dealershipId, employeeData);
	}
	
	@GetMapping("/dealership/{dealershipId}/employee/{employeeId}")
	public EmployeeData retrieveEmployeeById(@PathVariable Long dealershipId, @PathVariable Long employeeId) {
		log.info("Retrieving employee with ID={} for dealership with ID={}", employeeId, dealershipId);
		
		return dealershipService.retrieveEmployeeById(dealershipId, employeeId);
	}
	
	@GetMapping("/dealership/{dealershipId}/employee")
	public List<EmployeeData> retrieveAllEmployeesByDealershipId(@PathVariable Long dealershipId){
		log.info("Retrieving all employees at dealership ID={}", dealershipId);
		
		return dealershipService.retrieveAllEmployeesByDealershipId(dealershipId);
	}
	
	/*
	 * VEHICLE CRUD OPERATIONS
	 */
	
	@PostMapping("/dealership/{dealershipId}/vehicle")
	@ResponseStatus(code = HttpStatus.CREATED)
	public VehicleData insertVehicle(@PathVariable Long dealershipId, @RequestBody VehicleData vehicleData) {
		log.info("Creating vehicle {}", vehicleData);
		
		return dealershipService.saveVehicle(dealershipId, vehicleData);
	}
	
	@PutMapping("/dealership/{dealershipId}/vehicle/{vehicleId}")
	public VehicleData updateVehicle(@PathVariable Long dealershipId, @RequestBody VehicleData vehicleData,
			@PathVariable Long vehicleId) {
		
		log.info("Updating vehicleID={} at dealershipID={}", vehicleId, dealershipId);
		vehicleData.setVehicleId(vehicleId);
		return dealershipService.saveVehicle(dealershipId, vehicleData);
	}
	
	@GetMapping("/dealership/{dealershipId}/vehicle/{vehicleId}")
	public VehicleData retrieveVehicleById(@PathVariable Long dealershipId, @PathVariable Long vehicleId) {
		log.info("Retrieving vehicle with ID={} at dealership with ID={}", vehicleId, dealershipId);
		
		return dealershipService.retrieveVehicleById(dealershipId, vehicleId);
	}
	
	@GetMapping("/dealership/{dealershipId}/vehicle")
	public List<VehicleData> retrieveAllVehiclesByDealershipId(@PathVariable Long dealershipId){
		log.info("Retrieving all vehicles at dealership with ID={}", dealershipId);
		
		return dealershipService.retrieveAllVehiclesByDealershipId(dealershipId);
	}
	
	@DeleteMapping("/dealership/{dealershipId}/vehicle")
	public void deleteAllVehicles(@PathVariable Long dealershipId) {
		log.info("Attempting to delete all vehicles at dealershipID=", dealershipId);
		throw new UnsupportedOperationException("Deleting all vehicles from dealership is not allowed!!!");
	}
	
	@DeleteMapping("/dealership/{dealershipId}/vehicle/{vehicleId}")
	public Map<String, String> deleteVehicleById(@PathVariable Long dealershipId, @PathVariable Long vehicleId){
		log.info("Deleting vehicle with ID={}", dealershipId);
		
		dealershipService.deleteVehicleById(dealershipId, vehicleId);
		
		return Map.of("message", "Deletion of vehicle with ID=" + vehicleId + 
				" at dealershipID=" + dealershipId + " was successful");
	}
	
	/*
	 * CUSTOMER CRUD OPERATIONS
	 */
	
	@PostMapping("/dealership/{dealershipId}/customer")
	@ResponseStatus(code = HttpStatus.CREATED)
	public CustomerData insertCustomer(@PathVariable Long dealershipId, @RequestBody CustomerData customerData) {
		log.info("Creating customer in dealershipID={}", dealershipId);
		
		return dealershipService.saveCustomer(dealershipId, customerData);
	}
	
	@PutMapping("/dealership/{dealershipId}/customer/{customerId}")
	public CustomerData updateCustomer(@PathVariable Long dealershipId, @RequestBody CustomerData customerData,
			@PathVariable Long customerId) {
		
		log.info("Updating customer with ID={} at dealershipID={}", customerId, dealershipId);
		customerData.setCustomerId(customerId);
		return dealershipService.saveCustomer(dealershipId, customerData);
	}
	
	@GetMapping("/dealership/{dealershipId}/customer/{customerId}")
	public CustomerData retrieveCustomerById(@PathVariable Long dealershipId, @PathVariable Long customerId) {
		log.info("Retrieving customer with ID={} at dealership with ID={}", customerId, dealershipId);
		
		return dealershipService.retrieveCustomerById(dealershipId, customerId);
	}
	
	@GetMapping("/dealership/{dealershipId}/customer")
	public List<CustomerData> retrieveAllCustomersByDealershipId(@PathVariable Long dealershipId){
		log.info("Retrieving all customers at dealership with ID={}", dealershipId);
		
		return dealershipService.retrieveAllCustomersByDealershipId(dealershipId);
	}
}
