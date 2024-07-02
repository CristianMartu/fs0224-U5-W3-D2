package cristianmartucci.U5_W3_D2.payloads.employees;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record EmployeeIdDTO(@NotNull(message = "UUID obbligatorio")
                            @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$", message = "Formato UUID non valido")
                            String employeeId) {
}
