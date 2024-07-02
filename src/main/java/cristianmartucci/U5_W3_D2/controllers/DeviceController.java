package cristianmartucci.U5_W3_D2.controllers;

import cristianmartucci.U5_W3_D2.entities.Device;
import cristianmartucci.U5_W3_D2.entities.Employee;
import cristianmartucci.U5_W3_D2.exceptions.BadRequestException;
import cristianmartucci.U5_W3_D2.payloads.devices.DeviceDTO;
import cristianmartucci.U5_W3_D2.payloads.devices.DeviceResponseDTO;
import cristianmartucci.U5_W3_D2.payloads.employees.EmployeeDTO;
import cristianmartucci.U5_W3_D2.payloads.employees.EmployeeIdDTO;
import cristianmartucci.U5_W3_D2.payloads.employees.EmployeeResponseDTO;
import cristianmartucci.U5_W3_D2.services.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/devices")
public class DeviceController {
    @Autowired
    private DeviceService deviceService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    private DeviceResponseDTO saveDevice(@RequestBody @Validated DeviceDTO deviceDTO, BindingResult validationResult){
        if (validationResult.hasErrors()){
            System.out.println(validationResult.getAllErrors());
            throw new BadRequestException(validationResult.getAllErrors());
        }
        return new DeviceResponseDTO(this.deviceService.saveDevice(deviceDTO).getDeviceId());
    }

    @GetMapping
    private Page<Device> getAllDevice(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "deviceId") String sortBy){
        return this.deviceService.getAllDevice(page, size, sortBy);
    }

    @GetMapping("/{deviceId}")
    private Device findById(@PathVariable UUID deviceId){
        return this.deviceService.findByID(deviceId);
    }

    @PutMapping("/{deviceId}")
    public Device updateDevice(@PathVariable UUID deviceId, @RequestBody DeviceDTO body) {
        return this.deviceService.updateDevice(deviceId, body);
    }

    @PatchMapping("/{deviceId}/employee")
    public Device patchUpdateDeviceEmployee(@PathVariable UUID deviceId, @RequestBody @Validated EmployeeIdDTO employeeIdDTO, BindingResult validationResult){
        if (validationResult.hasErrors()){
            System.out.println(validationResult.getAllErrors());
            throw new BadRequestException(validationResult.getAllErrors());
        }
        return  this.deviceService.patchDeviceEmployee(deviceId, employeeIdDTO);
    }

    @DeleteMapping("/{deviceId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDevice(@PathVariable UUID deviceId) {
        this.deviceService.deleteDevice(deviceId);
    }
}
