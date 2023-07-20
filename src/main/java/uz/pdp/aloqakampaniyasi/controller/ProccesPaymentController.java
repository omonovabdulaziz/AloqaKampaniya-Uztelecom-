package uz.pdp.aloqakampaniyasi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import uz.pdp.aloqakampaniyasi.entity.ProccessPayment;
import uz.pdp.aloqakampaniyasi.payload.ApiResponse;
import uz.pdp.aloqakampaniyasi.payload.ProccesPaymentDTO;
import uz.pdp.aloqakampaniyasi.service.ProccesPaymentService;

@RestController

@RequestMapping("/api/procces")
public class ProccesPaymentController {
    @Autowired
    ProccesPaymentService proccesPaymentService;

    @Transactional   // agar hatoliklar bolsa ushbu transactional anotatsiya barcha jarayonni ortga qaytaradi
    @PreAuthorize("hasAnyRole('RAHBAR' , 'MANAGER' , 'EMPLOYEE' , 'MIJOZ')")
    @PostMapping
    public ResponseEntity<?> addProccesPaymentService(@RequestBody ProccesPaymentDTO proccesPaymentDTO) {
        ApiResponse apiResponse = proccesPaymentService.addProccesPaymentService(proccesPaymentDTO);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(apiResponse);
    }


    //readByTolovTuriPage
    @PreAuthorize("hasAnyRole('RAHBAR' , 'MANAGER' , 'EMPLOYEE')")
    @GetMapping("/{tolovturi}")
    public Page<ProccessPayment> getPageProccesPayment(@RequestParam int page, @PathVariable String tolovturi) {
        return proccesPaymentService.getPageProccesPayment(tolovturi, page);
    }

    //readbyid
    @PreAuthorize("hasAnyRole('RAHBAR' , 'MANAGER' , 'EMPLOYEE')")
    @GetMapping("/{id}")
    public ProccessPayment getProccesPayment(@PathVariable Integer id) {
        return proccesPaymentService.getProccessPayment(id);
    }

    //delete
    @PreAuthorize("hasAnyRole('RAHBAR' , 'MANAGER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProccesPayment(@PathVariable Integer id) {
        ApiResponse apiResponse = proccesPaymentService.deleteProccesPayment(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResponse);
    }


}
