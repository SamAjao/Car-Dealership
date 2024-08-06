/*
// Program: Promineo Tech Backend Java Back End Development Course
// Author:  Samuel Ajao
// Subject:  Spring Boot API FINAL PROJECT - Car Dealership
// Create Date: July 30, 2024
//
*/
package car.dealership.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;

import car.dealership.CarDealershipApplication;
import car.dealership.controller.model.DealershipData;

@SpringBootTest(webEnvironment = WebEnvironment.NONE,
		classes = CarDealershipApplication.class)
@ActiveProfiles("test")
@Sql(scripts = {"classpath:schema.sql"})
@SqlConfig(encoding = "utf-8")
class DealershipControllerTest extends DealershipServiceTestSupport{

	@Test
	void testInsertDealership() {
		//Given : A dealership request
		DealershipData request = buildInsertDealership(1);
		DealershipData expected = buildInsertDealership(1);
		
		//When : the dealership is added to the dealership_table
		DealershipData actual = insertDealership(request);
		
		//Then : the dealership returned is what is expected
		assertThat(actual).isEqualTo(expected);
		
		//And : there is one row in the dealership table.
		assertThat(rowsInDealershipTable()).isOne();
	}
	
	@Test
	void testRetrieveDealership() {
		//Given : Given a dealership
		DealershipData dealership = insertDealership(buildInsertDealership(1));
		DealershipData expected = buildInsertDealership(1);
				
		//When : the dealership is retrieved by dealership ID
		DealershipData actual = retrieveDealership(dealership.getDealershipId());
		
		//Then : the actual dealership is equal to the expected dealership
		assertThat(actual).isEqualTo(expected);
	}
	
	@Test
	void testRetrieveAllDealerships() {
		//Given : two dealerships
		List<DealershipData> expected = insertTwoDealerships();
		
		//When : all dealerships retrieved
		List<DealershipData> actual = retrieveAllDealerships();
		
		//Then : the retrieved dealerships are the same as expected
		assertThat(sorted(actual)).isEqualTo(sorted(expected));
	}

	@Test
	void testUpdateDealership() {
		//Given: a dealership and update request
		insertDealership(buildInsertDealership(1));
		DealershipData expected = buildUpdateDealership();
		
		//When: the dealership is updated
		DealershipData actual = updateDealership(expected);
		
		//Then: the dealership is returned as expected
		assertThat(actual).isEqualTo(expected);
		
		//And: there is one row in the dealership table
		assertThat(rowsInDealershipTable()).isOne();
	}

	@Test
	void testDeleteDealership() {
		//Given: a dealership, employee, vehicle, and customer
		DealershipData dealership = insertDealership(buildInsertDealership(1));
		Long dealershipId = dealership.getDealershipId();
		
		insertEmployee(1);
		insertVehicle(1);
		insertCustomer(1);
		
		assertThat(rowsInDealershipTable()).isOne();
		assertThat(rowsInEmployeeTable()).isOne();
		assertThat(rowsInVehicleTable()).isOne();
		assertThat(rowsInCustomerTable()).isOne();
		
		//When: the dealership is deleted
		deleteDealership(dealershipId);
		
		//Then: there are no dealership, employee, or vehicle rows
		assertThat(rowsInDealershipTable()).isZero();
		assertThat(rowsInEmployeeTable()).isZero();
		assertThat(rowsInVehicleTable()).isZero();
		
		//And: the number of customer rows is unchanged.
		assertThat(rowsInCustomerTable()).isOne();
	}

}
