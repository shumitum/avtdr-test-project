package com.avtdr.vehicletracks.device;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class DeviceServiceImpl implements DeviceService {
    private final DeviceRepository deviceRepository;

    @Override
    @Transactional(readOnly = true)
    public void checkDeviceExistence(String deviceId) {
        if(!deviceRepository.existsById(deviceId)) {
            throw new NoSuchElementException(String.format("Устройства с deviceID=%s не существует", deviceId));
        }
    }
}
