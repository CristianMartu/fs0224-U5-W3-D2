package cristianmartucci.U5_W3_D2.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import cristianmartucci.U5_W3_D2.entities.Employee;
import cristianmartucci.U5_W3_D2.exceptions.BadRequestException;
import cristianmartucci.U5_W3_D2.exceptions.NotFoundException;
import cristianmartucci.U5_W3_D2.payloads.employees.EmployeeDTO;
import cristianmartucci.U5_W3_D2.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private Cloudinary cloudinary;

    public Employee saveEmployee(EmployeeDTO employeeDTO){
        if(employeeRepository.findByEmail(employeeDTO.email()).isEmpty()){
            Employee employee = new Employee(employeeDTO.username(), employeeDTO.name(), employeeDTO.surname(), employeeDTO.email(), employeeDTO.password());
            employee.setAvatar("https://ui-avatars.com/api/?name=" + employee.getName() + "+" +employee.getSurname());
            return this.employeeRepository.save(employee);
        }else {
            throw new BadRequestException("Email già in uso!");
        }
    }

    public Page<Employee> getAllEmployee(int pageNumber, int pageSize, String sortBy){
        if(pageSize > 50) pageSize = 50;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        return this.employeeRepository.findAll(pageable);
    }

    public Employee findByID(UUID employeeId){
        return this.employeeRepository.findById(employeeId).orElseThrow(() -> new NotFoundException(employeeId));
    }

    public Employee updateEmployee(UUID employeeId, Employee updateEmployee){
        Employee employee = this.findByID(employeeId);
        employee.setUsername(updateEmployee.getUsername());
        employee.setName(updateEmployee.getName());
        employee.setSurname(updateEmployee.getSurname());
        employee.setEmail(updateEmployee.getEmail());
        employee.setAvatar("https://ui-avatars.com/api/?name=" + employee.getName() + "+" + employee.getSurname());
        return this.employeeRepository.save(employee);
    }

    public void deleteEmployee(UUID employeeId) {
        Employee employee = this.findByID(employeeId);
        this.employeeRepository.delete(employee);
    }

    public String uploadAvatar(MultipartFile file) throws IOException {
        return (String) this.cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
    }

    public Employee patchEmployee(UUID employeeId, String url){
        Employee employee = this.findByID(employeeId);
        employee.setAvatar(url);
        return this.employeeRepository.save(employee);
    }

    public Employee findByEmail(String email){
        return employeeRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Dipendente con email " + email + " non trovato!"));
    }
}

