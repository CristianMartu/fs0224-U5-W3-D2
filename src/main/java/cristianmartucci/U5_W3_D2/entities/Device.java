package cristianmartucci.U5_W3_D2.entities;

import cristianmartucci.U5_W3_D2.enums.DeviceStatus;
import cristianmartucci.U5_W3_D2.enums.DeviceTypology;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Entity
@Table(name = "devices")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "device_id")
    private UUID deviceId;
    @Enumerated(EnumType.STRING)
    @Column(name = "device_typology")
    private DeviceTypology deviceTypology;
    @Enumerated(EnumType.STRING)
    @Column(name = "device_status")
    private DeviceStatus deviceStatus;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    public Device(DeviceTypology deviceTypology, DeviceStatus deviceStatus) {
        this.deviceTypology = deviceTypology;
        this.deviceStatus = deviceStatus;
    }
}
