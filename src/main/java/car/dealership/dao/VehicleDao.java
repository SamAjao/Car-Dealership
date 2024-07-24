package car.dealership.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import car.dealership.entity.Vehicle;

public interface VehicleDao extends JpaRepository<Vehicle, Long> {

}
