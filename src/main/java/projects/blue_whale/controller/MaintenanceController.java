package projects.blue_whale.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projects.blue_whale.dto.ApiResponse;
import projects.blue_whale.entity.Maintenance;
import projects.blue_whale.service.MaintenanceService;
import projects.exceptions.CustomException;

import java.util.List;


@CrossOrigin
@RestController
@RequestMapping("/api/maintenance")
public class MaintenanceController {
    @Autowired
    private MaintenanceService maintenanceService;

    @PostMapping("/save")
    public ResponseEntity<ApiResponse> saveMaintenance(@RequestBody Maintenance maintenance) {
        maintenanceService.saveMaintenance(maintenance);
        return new ResponseEntity<>(new ApiResponse("Maintenance updated successfully"), HttpStatus.CREATED);
    }

    @GetMapping("/get")
    public ResponseEntity<ApiResponse> getAllMaintenance() {
        List<Maintenance> maintenanceList = maintenanceService.getAllMaintenance();
        return new ResponseEntity<>(new ApiResponse(maintenanceList), HttpStatus.OK);
    }

    @DeleteMapping("/{maintenanceId}/delete")
    public ResponseEntity<ApiResponse> deleteMaintenance(@PathVariable Long maintenanceId) {
        maintenanceService.deleteMaintenance(maintenanceId);
        return new ResponseEntity<>(new ApiResponse("Maintenance deleted successfully."), HttpStatus.OK);
    }
}
