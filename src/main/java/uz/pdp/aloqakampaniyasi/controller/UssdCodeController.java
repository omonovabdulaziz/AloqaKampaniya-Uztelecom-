package uz.pdp.aloqakampaniyasi.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import uz.pdp.aloqakampaniyasi.entity.UssdCode;
import uz.pdp.aloqakampaniyasi.payload.ApiResponse;

import uz.pdp.aloqakampaniyasi.service.UssdCodeService;


import java.util.List;


@RestController
@RequestMapping("/api/ussdcode")
public class UssdCodeController {
    @Autowired
    UssdCodeService ussdCodeService;


    //ussd codelar listini koramiz
    @PreAuthorize("hasAnyRole('RAHBAR' , 'EMPLOYEE' , 'MIJOZ')")
    @GetMapping
    public List<UssdCode> getUssdCode() {
        return ussdCodeService.getUssdCode();
    }

    //ussd kodlarni ishlatish  tarifni korish
    @PreAuthorize("hasAnyRole('RAHBAR' , 'EMPLOYEE' , 'MIJOZ')")
    @GetMapping("/{kodi}/{simcardId}")
    public ResponseEntity<?> getTarif(@PathVariable Integer kodi, @PathVariable Integer simcardId) {
        ApiResponse apiresponse = ussdCodeService.getTarif(kodi, simcardId);
        return ResponseEntity.status(apiresponse.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiresponse);
    }

    //ussdkolarni ishlatish tarif o'zgartirish
    @PreAuthorize("hasAnyRole('RAHBAR' , 'EMPLOYEE' , 'MIJOZ')")
    @Transactional
    @PatchMapping("/{kodi}/{simcardId}/{newtarifId}")
    public ResponseEntity<?> updateTarif(@PathVariable Integer simcardId, @PathVariable Integer kodi, @PathVariable Integer newtarifId) {
        ApiResponse apiResponse = ussdCodeService.updateTarif(kodi, simcardId, newtarifId);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResponse);
    }


}
