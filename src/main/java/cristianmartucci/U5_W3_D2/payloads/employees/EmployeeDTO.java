package cristianmartucci.U5_W3_D2.payloads.employees;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record EmployeeDTO(@NotEmpty(message = "Username obbligatorio")
                          @Size(min = 3, max = 30 , message = "Username deve avere minimo 3 caratteri, massimo 30")
                          String username,
                          @NotBlank(message = "Nome obbligatorio")
                          String name,
                          @NotBlank(message = "Cognome obbligatorio")
                          String surname,
                          @NotEmpty(message = "Email obbligatoria")
                          @Email(message = "Email non valida")
                          String email,
                          @NotEmpty(message = "Password obbligatoria")
                          String password) {
}
