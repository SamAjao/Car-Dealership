/*
// Program: Promineo Tech Backend Java Back End Development Course
// Author:  Samuel Ajao
// Subject:  Spring Boot API FINAL PROJECT - Car Dealership
// Create Date: July 21, 2024
//
*/
package car.dealership.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import car.dealership.controller.model.DealershipData;
import car.dealership.dao.DealershipDao;
import car.dealership.entity.Dealership;

@Service
public class DealershipService {
	
	@Autowired
	private DealershipDao dealershipDao;

	@Transactional(readOnly = false)
	public DealershipData saveDealership(DealershipData dealershipData) {
		Long dealershipId = dealershipData.getDealershipId();
		Dealership dealership = findOrCreateDealership(dealershipId);
		
		copyDealershipFields(dealership, dealershipData);
		
		return new DealershipData(dealershipDao.save(dealership));
	}

	private void copyDealershipFields(Dealership dealership, DealershipData dealershipData) {
		dealership.setDealershipId(dealershipData.getDealershipId());
		dealership.setName(dealershipData.getName());
		dealership.setAddress(dealershipData.getAddress());
		dealership.setCity(dealershipData.getCity());
		dealership.setState(dealershipData.getState());
		dealership.setZip(dealershipData.getZip());
		dealership.setPhone(dealershipData.getPhone());
	}

	/*
	 * This method allows for the updating of specific fields in the Dealership object without setting existing fields
	 * not being passed in the update to NULL. Creates a new object otherwise.
	 */
	private Dealership findOrCreateDealership(Long dealershipId) {
		Dealership dealership;
		
		if(Objects.isNull(dealershipId)) {
			dealership = new Dealership();
		}
		else {
			dealership = findDealershipById(dealershipId);
		}
		
		return dealership;
	}
	
	private Dealership findDealershipById(Long dealershipId) {
		return dealershipDao.findById(dealershipId).orElseThrow(() -> new NoSuchElementException(
				"Dealership with ID=" +dealershipId + " was not found"));
	}

	@Transactional(readOnly = true)
	public DealershipData retrieveDealershipById(Long dealershipId) {
		Dealership dealership = findDealershipById(dealershipId);
		return new DealershipData(dealership);
	}

	@Transactional(readOnly = true)
	public List<DealershipData> retrieveAllDealerships() {
		// @formatter:off
		return dealershipDao.findAll()
				.stream()
				.map(dealer -> new DealershipData(dealer))
				.toList();
		// @formatter:on
	}

	@Transactional(readOnly = false)
	public void deleteDealershipById(Long dealershipId) {
		Dealership dealership = findDealershipById(dealershipId);
		dealershipDao.delete(dealership);
	}
	

}
