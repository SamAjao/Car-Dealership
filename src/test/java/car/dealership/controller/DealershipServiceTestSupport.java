/*
// Program: Promineo Tech Backend Java Back End Development Course
// Author:  Samuel Ajao
// Subject:  Spring Boot API FINAL PROJECT - Car Dealership
// Create Date: July 30, 2024
//
*/
package car.dealership.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;

import car.dealership.controller.model.DealershipData;
import car.dealership.entity.Dealership;

public class DealershipServiceTestSupport {
	private static final String DEALERSHIP_TABLE = "dealership";

	//@formatter:off
	private DealershipData insertAddress1 = new DealershipData(
			1L,
			"Jack's Car Shack",
			"227 Iron Point",
			"Detroit",
			"Michigan",
			"98765",
			"(123)456-7890"
			);
	
	private DealershipData insertAddress2 = new DealershipData(
			2L,
			"Top Gear Auto",
			"777 Steel Point",
			"Las Vegas",
			"Nevada",
			"98765",
			"(234)567-8901"
			);
	//@formatter:on
	
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private DealershipController dealershipController;
	
	protected DealershipData buildInsertDealership(int which) {
		return which == 1 ? insertAddress1 : insertAddress2;
	}
	
	protected int rowsInDealershipTable() {
		return JdbcTestUtils.countRowsInTable(jdbcTemplate, DEALERSHIP_TABLE);
	}

	protected DealershipData insertLocation(DealershipData dealershipData) {
		Dealership dealership = dealershipData.toDealership();
		DealershipData clone = new DealershipData(dealership);
		
		clone.setDealershipId(null);
		
		return dealershipController.createDealership(clone);
	}

}// END DealershipServiceTestSupport{} Class
