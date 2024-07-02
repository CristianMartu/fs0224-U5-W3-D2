package cristianmartucci.U5_W3_D2.services;

import cristianmartucci.U5_W3_D2.entities.Employee;
import cristianmartucci.U5_W3_D2.exceptions.UnauthorizedException;
import cristianmartucci.U5_W3_D2.payloads.employees.EmployeeDTO;
import cristianmartucci.U5_W3_D2.payloads.logins.EmployeeLoginDTO;
import cristianmartucci.U5_W3_D2.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private JWTTools jwtTools;

    @Autowired
    private EmployeeService employeeService;

    public String authGenerateToken(EmployeeLoginDTO employeeLoginDTO){
        Employee employee = this.employeeService.findByEmail(employeeLoginDTO.email());
        if (employee.getPassword().equals(employeeLoginDTO.password())){
            return jwtTools.createToken(employee);
        }else{
            throw new UnauthorizedException("Credenziali non corrette!");
        }
    }
}
