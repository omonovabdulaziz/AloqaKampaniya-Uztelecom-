package uz.pdp.aloqakampaniyasi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.aloqakampaniyasi.payload.ApiResponse;
import uz.pdp.aloqakampaniyasi.service.DashboardService;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {
    @Autowired
    DashboardService dashboardService;


    @PostMapping
    public ResponseEntity<?> setActiveTarif() {
        ApiResponse apiResponse = dashboardService.setActiveThings();
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResponse);
    }

}
