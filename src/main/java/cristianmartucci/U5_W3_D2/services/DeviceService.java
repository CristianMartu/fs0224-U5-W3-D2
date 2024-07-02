package cristianmartucci.U5_W3_D2.services;

import cristianmartucci.U5_W3_D2.entities.Device;
import cristianmartucci.U5_W3_D2.entities.Employee;
import cristianmartucci.U5_W3_D2.enums.DeviceStatus;
import cristianmartucci.U5_W3_D2.enums.DeviceTypology;
import cristianmartucci.U5_W3_D2.exceptions.BadRequestException;
import cristianmartucci.U5_W3_D2.exceptions.NotFoundException;
import cristianmartucci.U5_W3_D2.payloads.devices.DeviceDTO;
import cristianmartucci.U5_W3_D2.payloads.devices.DeviceResponseDTO;
import cristianmartucci.U5_W3_D2.payloads.employees.EmployeeIdDTO;
import cristianmartucci.U5_W3_D2.payloads.employees.EmployeeResponseDTO;
import cristianmartucci.U5_W3_D2.repositories.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeviceService {
    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private  EmployeeService employeeService;

    public Device saveDevice(DeviceDTO deviceDTO){
        Device device = new Device(stringToDeviceTypology(deviceDTO.deviceTypology()), stringToDeviceStatus(deviceDTO.deviceStatus()));
        return this.deviceRepository.save(device);
    }

    public Page<Device> getAllDevice(int pageNumber, int pageSize, String sortBy){
        if(pageSize > 50) pageSize = 50;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        return this.deviceRepository.findAll(pageable);
    }

    public Device findByID(UUID deviceId){
        return this.deviceRepository.findById(deviceId).orElseThrow(() -> new NotFoundException(deviceId));
    }

    public Device updateDevice(UUID deviceId, DeviceDTO updateDevice){
        Device device = this.findByID(deviceId);
        device.setDeviceTypology(stringToDeviceTypology(updateDevice.deviceTypology()));
        device.setDeviceStatus(stringToDeviceStatus(updateDevice.deviceStatus()));
        return this.deviceRepository.save(device);
    }

    public Device patchDeviceEmployee(UUID deviceId, EmployeeIdDTO employeeIdDTO){
        Device device = this.findByID(deviceId);
        Employee employee = this.employeeService.findByID(UUID.fromString(employeeIdDTO.employeeId()));
        device.setEmployee(employee);
        return this.deviceRepository.save(device);
    }

    public void deleteDevice(UUID deviceId) {
        Device device = this.findByID(deviceId);
        this.deviceRepository.delete(device);
    }

    private static DeviceTypology stringToDeviceTypology(String type) {
        try {
            return DeviceTypology.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Tipologia dispositivo inserito non coretto!\n Tipologie disponibili: LAPTOP, SMARTPHONE, COMPUTER, TABLET.");
        }
    }

    private static DeviceStatus stringToDeviceStatus(String status) {
        try {
            return DeviceStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Stato dispositivo inserito non coretto!\n Stati disponibili: AVAILABLE, ASSIGNED, MAINTENANCE, DECOMMISSIONED");
        }
    }
}
