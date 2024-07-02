package cristianmartucci.U5_W3_D2.controllers;

import cristianmartucci.U5_W3_D2.exceptions.BadRequestException;
import cristianmartucci.U5_W3_D2.payloads.employees.EmployeeDTO;
import cristianmartucci.U5_W3_D2.payloads.employees.EmployeeResponseDTO;
import cristianmartucci.U5_W3_D2.payloads.logins.EmployeeLoginDTO;
import cristianmartucci.U5_W3_D2.payloads.logins.EmployeeLoginResponseDTO;
import cristianmartucci.U5_W3_D2.services.AuthService;
import cristianmartucci.U5_W3_D2.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/login")
    public EmployeeLoginResponseDTO login(@RequestBody EmployeeLoginDTO employeeLoginDTO){
        return new EmployeeLoginResponseDTO(this.authService.authGenerateToken(employeeLoginDTO));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    private EmployeeResponseDTO saveEmployee(@RequestBody @Validated EmployeeDTO employeeDTO, BindingResult validationResult){
        if (validationResult.hasErrors()){
            System.out.println(validationResult.getAllErrors());
            throw new BadRequestException(validationResult.getAllErrors());
        }
        return new EmployeeResponseDTO(this.employeeService.saveEmployee(employeeDTO).getEmployeeId());
    }
}
