package car.dealership.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import car.dealership.controller.model.DealershipData;
import car.dealership.dao.DealershipDao;
import car.dealership.entity.Dealership;

@Service
public class DealershipService {
	
	@Autowired
	private DealershipDao dealershipDao;

	public DealershipData saveDealership(DealershipData dealershipData) {
		Dealership dealership = dealershipData.toDealership();
		Dealership dbDealership = dealershipDao.save(dealership);
		
		return new DealershipData(dbDealership);
	}
	

}
