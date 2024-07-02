package cristianmartucci.U5_W3_D2.controllers;

import cristianmartucci.U5_W3_D2.entities.Employee;
import cristianmartucci.U5_W3_D2.exceptions.BadRequestException;
import cristianmartucci.U5_W3_D2.payloads.employees.EmployeeDTO;
import cristianmartucci.U5_W3_D2.payloads.employees.EmployeeResponseDTO;
import cristianmartucci.U5_W3_D2.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    private Page<Employee> getAllEmployee(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "employeeId") String sortBy){
        return this.employeeService.getAllEmployee(page, size, sortBy);
    }

    @GetMapping("/{employeeId}")
    private Employee findById(@PathVariable UUID employeeId){
        return this.employeeService.findByID(employeeId);
    }

    @PutMapping("/{employeeId}")
    public Employee updateEmployee(@PathVariable UUID employeeId, @RequestBody Employee body) {
        return this.employeeService.updateEmployee(employeeId, body);
    }

    @DeleteMapping("/{employeeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployee(@PathVariable UUID employeeId) {
        this.employeeService.deleteEmployee(employeeId);
    }

    @PatchMapping("/{employeeId}/avatar")
    public Employee uploadAvatar(@PathVariable UUID employeeId, @RequestParam("avatar") MultipartFile image) throws IOException {
        return this.employeeService.patchEmployee(employeeId, this.employeeService.uploadAvatar(image));
    }
}
