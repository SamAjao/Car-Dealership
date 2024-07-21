package car.dealership.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import car.dealership.entity.Dealership;

public interface DealershipDao extends JpaRepository<Dealership, Long> {

}
