package com.avtdr.vehicletracks.device;

import com.avtdr.vehicletracks.device.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<Device, String> {
}