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
import car.dealership.controller.model.DealershipData.EmployeeData;
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
		log.info("Deleting dealership with ID=" + dealershipId);
		
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
		
}
