/*
// Program: Promineo Tech Backend Java Back End Development Course
// Author:  Samuel Ajao
// Subject:  Spring Boot API FINAL PROJECT - Car Dealership
// Create Date: July 30, 2024
//
*/
package car.dealership.controller;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;

import car.dealership.controller.model.DealershipData;
import car.dealership.entity.Dealership;

public class DealershipServiceTestSupport {
	private static final String EMPLOYEE_TABLE = "employee";

	private static final String VEHICLE_TABLE = "vehicle";

	private static final String CUSTOMER_TABLE = "customer";

	private static final String DEALERSHIP_TABLE = "dealership";

	private static final String INSERT_EMPLOYEE_1_SQL = """
			INSERT INTO employee 
			(dealership_id, first_name, last_name, phone, job_title) 
			VALUES (1, 'Jack', 'Johnson', '(123)456-7890', 'Floor Rep')
			""";

	private static final String INSERT_EMPLOYEE_2_SQL = """
			INSERT INTO employee 
			(dealership_id, first_name, last_name, phone, job_title) 
			VALUES (1, 'John', 'Jackson', '(123)567-8901', 'Sales Manager')
			""";

	private static final String INSERT_CUSTOMER_1_SQL = """
			INSERT INTO customer 
			(first_name, last_name, email, phone, address, city, state, zip) 
			VALUES ('Peter', 'Parker', 'peter.parker@marvel.mail', '(123)684-8332', '123 Friendly Street', 'New York', 'New York', '98765')
			""";

	private static final String INSERT_CUSTOMER_2_SQL = """
			INSERT INTO customer 
			(first_name, last_name, email, phone, address, city, state, zip) 
			VALUES ('Bruce', 'Wayne', 'bruce.wayne@dcu.mail', '(456)684-8332', '456 Not So Friendly Street', 'Gotham City', 'Not New York', '88765')
			""";

	private static final String INSERT_VEHICLE_1_SQL = """
			INSERT INTO vehicle 
			(dealership_id, vehicle_year, make, model, color, vehicle_trim) 
			VALUES (1, 2024, 'Nissan', 'Maxima', 'Black', 'SE')
			""";

	private static final String INSERT_VEHICLE_2_SQL = """
			INSERT INTO vehicle 
			(dealership_id, vehicle_year, make, model, color, vehicle_trim) 
			VALUES (1, 2024, 'Nissan', 'Maxima', 'Silver', 'Platinum')
			""";

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
			"AAA Top Gear Auto",
			"777 Steel Point",
			"Las Vegas",
			"Nevada",
			"98765",
			"(234)567-8901"
			);
	//@formatter:on
	
	private DealershipData updateAddress1 = new DealershipData(
			1L,
			"Jill's Car Shack",
			"227 Iron Point",
			"High Point",
			"Michigan",
			"54321",
			"(456)456-7890"
			);
	
	
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

	protected DealershipData insertDealership(DealershipData dealershipData) {
		Dealership dealership = dealershipData.toDealership();
		DealershipData clone = new DealershipData(dealership);
		
		clone.setDealershipId(null);
		
		return dealershipController.createDealership(clone);
	}
	
	protected DealershipData retrieveDealership(Long dealershipId) {
		return dealershipController.retrieveDealership(dealershipId);
	}
	
	protected List<DealershipData> insertTwoDealerships() {
		DealershipData dealership1 = insertDealership(buildInsertDealership(1));
		DealershipData dealership2 = insertDealership(buildInsertDealership(2));
		
		return List.of(dealership1, dealership2);
	}

	protected List<DealershipData> retrieveAllDealerships() {
		return dealershipController.retrieveAllDealerships();
	}
	
	protected List<DealershipData> sorted(List<DealershipData> list) {
		List<DealershipData> data = new LinkedList<DealershipData>(list);
		data.sort((dealer1, dealer2) -> dealer1.getName().compareTo(dealer2.getName()));
		
		return data;
	}
	
	protected DealershipData updateDealership(DealershipData dealershipData) {
		return dealershipController.updateDealership(dealershipData.getDealershipId(), dealershipData);
	}

	protected DealershipData buildUpdateDealership() {
		return updateAddress1;
	}
	
	protected void insertEmployee(int which) {
		String employeeSql = which == 1 ? INSERT_EMPLOYEE_1_SQL : INSERT_EMPLOYEE_2_SQL;
		
		jdbcTemplate.update(employeeSql);
	}
	
	protected void insertCustomer(int which) {
		String customerSql = which == 1 ? INSERT_CUSTOMER_1_SQL : INSERT_CUSTOMER_2_SQL;
		
		jdbcTemplate.update(customerSql);
	}

	protected void insertVehicle(int which) {
		String vehicleSql = which == 1 ? INSERT_VEHICLE_1_SQL : INSERT_VEHICLE_2_SQL;
		
		jdbcTemplate.update(vehicleSql);
	}
	
	protected int rowsInCustomerTable() {
		return JdbcTestUtils.countRowsInTable(jdbcTemplate, CUSTOMER_TABLE);
	}

	protected int rowsInVehicleTable() {
		return JdbcTestUtils.countRowsInTable(jdbcTemplate, VEHICLE_TABLE);
	}

	protected int rowsInEmployeeTable() {
		return JdbcTestUtils.countRowsInTable(jdbcTemplate, EMPLOYEE_TABLE);
	}
	
	protected void deleteDealership(Long dealershipId) {
		dealershipController.deleteDealershipById(dealershipId);
	}

}// END DealershipServiceTestSupport{} Class
