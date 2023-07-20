package uz.pdp.aloqakampaniyasi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.aloqakampaniyasi.entity.Fillial;
import uz.pdp.aloqakampaniyasi.payload.ApiResponse;
import uz.pdp.aloqakampaniyasi.payload.FillialDTO;
import uz.pdp.aloqakampaniyasi.service.FillialService;

import java.util.List;

@RestController
@RequestMapping("/api/fillial")
public class FillialController {
    @Autowired
    FillialService fillialService;

    @PreAuthorize("hasAnyRole('RAHBAR')")
    @PostMapping
    public ResponseEntity<?> addFillial(@RequestBody FillialDTO fillialDTO) {
        ApiResponse apiResponse = fillialService.addFillial(fillialDTO);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(apiResponse);
    }

    @PreAuthorize("hasAnyRole('RAHBAR' , 'EMPLOYEE')")
    @GetMapping
    public List<Fillial> getFillial() {
        return fillialService.getFillial();
    }

    @PreAuthorize("hasAnyRole('RAHBAR' , 'EMPLOYEE')")
    @GetMapping("/{id}")
    public Fillial getFileById(@PathVariable Integer id) {
        return fillialService.getById(id);
    }

    @PreAuthorize("hasAnyRole('RAHBAR')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateFillial(@PathVariable Integer id, @RequestBody FillialDTO fillialDTO) {
        ApiResponse updatefillial = fillialService.updatefillial(id, fillialDTO);
        return ResponseEntity.status(updatefillial.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(updatefillial);
    }

    @PreAuthorize("hasAnyRole('RAHBAR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFillial(@PathVariable  Integer id) {
        ApiResponse apiResponse = fillialService.delteFillial(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResponse);
    }

}
