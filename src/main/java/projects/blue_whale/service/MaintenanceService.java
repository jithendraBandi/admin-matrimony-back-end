package projects.blue_whale.service;

import projects.blue_whale.entity.Maintenance;
import projects.exceptions.CustomException;

import java.util.List;

public interface MaintenanceService {
    void saveMaintenance(Maintenance maintenance);

    List<Maintenance> getAllMaintenance();

    void deleteMaintenance(Long maintenanceId);
}
