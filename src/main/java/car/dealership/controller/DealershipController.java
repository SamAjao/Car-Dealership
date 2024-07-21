/*
// Program: Promineo Tech Backend Java Back End Development Course
// Author:  Samuel Ajao
// Subject:  Spring Boot API FINAL PROJECT - Car Dealership
// Create Date: July 18, 2024
//
*/
package car.dealership.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import car.dealership.controller.model.DealershipData;
import car.dealership.service.DealershipService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/car_dealership")
@Slf4j
public class DealershipController {
	
	@Autowired
	private DealershipService dealershipService;
	
	@PostMapping("/dealership")
	@ResponseStatus(code = HttpStatus.CREATED)
	public DealershipData createDealership(@RequestBody DealershipData dealershipData) {
		log.info("Creating dealership {}", dealershipData);
		return dealershipService.saveDealership(dealershipData);
	}
}
