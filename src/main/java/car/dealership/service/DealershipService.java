/*
// Program: Promineo Tech Backend Java Back End Development Course
// Author:  Samuel Ajao
// Subject:  Spring Boot API FINAL PROJECT - Car Dealership
// Create Date: July 21, 2024
//
*/
package car.dealership.service;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import car.dealership.controller.model.DealershipData;
import car.dealership.controller.model.DealershipData.CustomerData;
import car.dealership.controller.model.DealershipData.EmployeeData;
import car.dealership.controller.model.DealershipData.VehicleData;
import car.dealership.dao.CustomerDao;
import car.dealership.dao.DealershipDao;
import car.dealership.dao.EmployeeDao;
import car.dealership.dao.VehicleDao;
import car.dealership.entity.Customer;
import car.dealership.entity.Dealership;
import car.dealership.entity.Employee;
import car.dealership.entity.Vehicle;

@Service
public class DealershipService {
	
	@Autowired
	private DealershipDao dealershipDao;
	
	@Autowired
	private EmployeeDao employeeDao;
	
	@Autowired
	private VehicleDao vehicleDao;
	
	@Autowired
	private CustomerDao customerDao;

	/*
	 *  ::::::::::::::::::::::::::: DEALERSHIP TRANSACTIONS :::::::::::::::::::::::::::
	 */
	
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

	/*
	 *  ::::::::::::::::::::::::::: EMPLOYEE TRANSACTIONS :::::::::::::::::::::::::::
	 */
	
	@Transactional(readOnly = false)
	public EmployeeData saveEmployee(Long dealershipId, EmployeeData employeeData) {
		Dealership dealership = findDealershipById(dealershipId);
		Long employeeId = employeeData.getEmployeeId();
		Employee employee = findOrCreateEmploye(dealershipId, employeeId);
		
		copyEmployeeFields(employee, employeeData);
		
		employee.setDealership(dealership);
		dealership.getEmployees().add(employee);
		
		Employee dbEmployee = employeeDao.save(employee);
		return new EmployeeData(dbEmployee);
	}

	private Employee findOrCreateEmploye(Long dealershipId, Long employeeId) {
		Employee employee;
		
		if(Objects.isNull(employeeId)) {
			employee = new Employee();
		}
		else {
			employee = findEmployeeById(dealershipId, employeeId);
		}
		
		return employee;
	}

	private Employee findEmployeeById(Long dealershipId, Long employeeId) {
		Employee employee = employeeDao.findById(employeeId)
				.orElseThrow(() -> new NoSuchElementException(
						"Employee with ID=" + employeeId + " was not found"));
		
		if(employee.getDealership().getDealershipId() == dealershipId) {
			return employee;
		}
		else {
			throw new IllegalArgumentException("Employee with employee_id=" + employeeId + " was not found");
		}
	}
	
	private void copyEmployeeFields(Employee employee, EmployeeData employeeData) {
		employee.setDealership(employeeData.toEmployee().getDealership());
		employee.setEmployeeId(employeeData.getEmployeeId());
		employee.setFirstName(employeeData.getFirstName());
		employee.setLastName(employeeData.getLastName());
		employee.setPhone(employeeData.getPhone());
		employee.setJobTitle(employeeData.getJobTitle());
	}

	@Transactional(readOnly = true)
	public EmployeeData retrieveEmployeeById(Long dealershipId, Long employeeId) {
		findDealershipById(dealershipId); //throws exception if NOT found.
		Employee employee = findEmployeeById(dealershipId, employeeId);
		
		if(employee.getDealership().getDealershipId() != dealershipId) {
			throw new IllegalStateException("Employee with ID=" + employeeId + " is not employed at dealership with ID=" + dealershipId);
		}
		
		return new EmployeeData(employee);
	}

	@Transactional(readOnly = true)
	public List<EmployeeData> retrieveAllEmployeesByDealershipId(Long dealershipId) {
		Dealership dealership = findDealershipById(dealershipId);
		List <EmployeeData> response = new LinkedList<EmployeeData>();
		
		for(Employee employee : dealership.getEmployees()) {
			response.add(new EmployeeData(employee));
		}
		
		return response;
	}

	
	
	/*
	 *  ::::::::::::::::::::::::::: VEHICLE TRANSACTIONS :::::::::::::::::::::::::::
	 */
	
	@Transactional(readOnly = false)
	public VehicleData saveVehicle(Long dealershipId, VehicleData vehicleData) {
		Dealership dealership = findDealershipById(dealershipId);
		Long vehicleId = vehicleData.getVehicleId();
		Vehicle vehicle = findOrCreateVehicle(dealershipId, vehicleId);
		
		copyVehicleFields(vehicle, vehicleData);
		
		vehicle.setDealership(dealership);
		dealership.getVehicles().add(vehicle);
		
		Vehicle dbVehicle = vehicleDao.save(vehicle);
		return new VehicleData(dbVehicle);
	}

	private Vehicle findOrCreateVehicle(Long dealershipId, Long vehicleId) {
		Vehicle vehicle;
		
		if(Objects.isNull(vehicleId)) {
			vehicle = new Vehicle();
		}
		else {
			vehicle = findVehicleById(dealershipId, vehicleId);
		}
		
		return vehicle;
	}

	private Vehicle findVehicleById(Long dealershipId, Long vehicleId) {
		Vehicle vehicle = vehicleDao.findById(vehicleId)
				.orElseThrow(() -> new NoSuchElementException(
						"Vehicle with ID=" + vehicleId + " was not found"));
		
		if(vehicle.getDealership().getDealershipId() == dealershipId) {
			return vehicle;
		}
		else {
			throw new IllegalArgumentException("Vehicle with vehicle_id=" + vehicleId + " was not found at this dealership.");
		}
	}
	
	private void copyVehicleFields(Vehicle vehicle, VehicleData vehicleData) {
		vehicle.setVehicleId(vehicleData.getVehicleId());
		vehicle.setYear(vehicleData.getYear());
		vehicle.setMake(vehicleData.getMake());
		vehicle.setModel(vehicleData.getModel());
		vehicle.setColor(vehicleData.getColor());
		vehicle.setVehicleTrim(vehicleData.getTrim());
		vehicle.setCustomer(vehicleData.getCustomer());
	}

	@Transactional(readOnly = true)
	public VehicleData retrieveVehicleById(Long dealershipId, Long vehicleId) {
		findDealershipById(dealershipId); //throws exception if NOT found.
		Vehicle vehicle = findVehicleById(dealershipId, vehicleId);
		
		if(vehicle.getDealership().getDealershipId() != dealershipId) {
			throw new IllegalStateException("Vehicle with ID=" + vehicleId + " was not at dealership with ID=" + dealershipId);
		}
		
		return new VehicleData(vehicle);
	}

	@Transactional(readOnly = true)
	public List<VehicleData> retrieveAllVehiclesByDealershipId(Long dealershipId) {
		Dealership dealership = findDealershipById(dealershipId);
		List <VehicleData> response = new LinkedList<VehicleData>();
		
		for(Vehicle vehicle : dealership.getVehicles()) {
			response.add(new VehicleData(vehicle));
		}
		
		return response;
	}

	@Transactional(readOnly = false)
	public void deleteVehicleById(Long dealershipId, Long vehicleId) {
		Vehicle vehicle = findVehicleById(dealershipId, vehicleId);
		vehicleDao.delete(vehicle);
	}
	
	
	/*
	 *  ::::::::::::::::::::::::::: CUSTOMER TRANSACTIONS :::::::::::::::::::::::::::
	 */
	
	@Transactional(readOnly = false)
	public CustomerData saveCustomer(Long dealershipId, CustomerData customerData) {
		Dealership dealership = findDealershipById(dealershipId);
		Long customerId = customerData.getCustomerId();
		Customer customer = findOrCreateCustomer(dealershipId, customerId);
		
		copyCustomerFields(customer, customerData);
		
		customer.getDealerships().add(dealership);
		dealership.getCustomers().add(customer);
		
		Customer dbCustomer = customerDao.save(customer);
		
		return new CustomerData(dbCustomer);
	}

	private Customer findOrCreateCustomer(Long dealershipId, Long customerId) {
		Customer customer;
		
		if(Objects.isNull(customerId)) {
			customer = new Customer();
		}
		else {
			customer = findCustomerById(dealershipId, customerId);
		}
		
		return customer;
	}

	private Customer findCustomerById(Long dealershipId, Long customerId) {
		Customer customer = customerDao.findById(customerId)
				.orElseThrow(() -> new NoSuchElementException
						("Customer with ID=" + customerId + " not found in this dealership"));
		
		for(Dealership dealership : customer.getDealerships()) {
			if(dealership.getDealershipId() == dealershipId) {
				return customer;
			}
		}
		
		//Only arrive here if customer is found and dealership is not.
		throw new IllegalArgumentException("Customer with ID=" + customerId + " exists, but dealership does not.");
	}
	
	private void copyCustomerFields(Customer customer, CustomerData customerData) {
		customer.setCustomerId(customerData.getCustomerId());
		customer.setFirstName(customerData.getFirstName());
		customer.setLastName(customerData.getLastName());
		customer.setEmail(customerData.getEmail());
		customer.setPhone(customerData.getPhone());
		customer.setAddress(customerData.getAddress());
		customer.setCity(customerData.getCity());
		customer.setState(customerData.getState());
		customer.setZip(customerData.getZip());
	}

	@Transactional(readOnly = true)
	public CustomerData retrieveCustomerById(Long dealershipId, Long customerId) {
		Dealership dealership = findDealershipById(dealershipId); //throws exception if NOT found.
		Customer customer = findCustomerById(dealershipId, customerId);
		
		if(!customer.getDealerships().contains(dealership) ) {
			throw new IllegalStateException("Customer with ID=" + customerId + " was not found at dealership with ID=" + dealershipId);
		}
		
		return new CustomerData(customer);
	}

	@Transactional(readOnly = true)
	public List<CustomerData> retrieveAllCustomersByDealershipId(Long dealershipId) {
		Dealership dealership = findDealershipById(dealershipId);
		List <CustomerData> response = new LinkedList<CustomerData>();
		
		for(Customer customer : dealership.getCustomers()) {
			response.add(new CustomerData(customer));
		}
		
		return response;
	}

}
