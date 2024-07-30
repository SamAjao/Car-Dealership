/*
// Program: Promineo Tech Backend Java Back End Development Course
// Author:  Samuel Ajao
// Subject:  Spring Boot API FINAL PROJECT - Car Dealership
// Create Date: July 30, 2024
//
*/
package car.dealership.controller;

import static org.assertj.core.api.Assertions.assertThat;

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
@Sql(scripts = {"classpath:schema.sql", "data.sql"})
@SqlConfig(encoding = "utf-8")
class DealershipControllerTest extends DealershipServiceTestSupport{

	@Test
	void testInsertDealership() {
		//Given : A dealership request
		DealershipData request = buildInsertDealership(1);
		DealershipData expected = buildInsertDealership(1);
		
		//When : the dealership is added to the dealership_table
		DealershipData actual = insertLocation(request);
		
		//Then : the dealership returned is what is expected
		assertThat(actual).isEqualTo(expected);
		
		//And : there is one row in the dealership table.
		assertThat(rowsInDealershipTable()).isOne();
	}



}
