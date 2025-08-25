package projects.blue_whale.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import projects.blue_whale.constants.Constants;
import projects.blue_whale.entity.Maintenance;
import projects.blue_whale.repository.MaintenanceRepository;
import projects.blue_whale.service.MaintenanceService;
import projects.exceptions.CustomException;

import java.util.Collections;
import java.util.List;

@Service
public class MaintenanceServiceImpl implements MaintenanceService {
    @Autowired
    private MaintenanceRepository maintenanceRepository;

    @Override
    public void saveMaintenance(Maintenance maintenance) {
        maintenanceRepository.save(maintenance);
    }

    @Override
    public List<Maintenance> getAllMaintenance() {
        List<Maintenance> maintenanceList = maintenanceRepository.findAll();
        Collections.reverse(maintenanceList);
        return maintenanceList;
    }

    @Override
    public void deleteMaintenance(Long maintenanceId) {
        maintenanceRepository.deleteById(maintenanceId);
    }
}
