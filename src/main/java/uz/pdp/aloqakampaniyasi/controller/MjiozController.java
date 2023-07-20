package uz.pdp.aloqakampaniyasi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.aloqakampaniyasi.entity.Mijoz;
import uz.pdp.aloqakampaniyasi.payload.ApiResponse;
import uz.pdp.aloqakampaniyasi.payload.MijozDTO;
import uz.pdp.aloqakampaniyasi.repository.SimcardRepository;
import uz.pdp.aloqakampaniyasi.repository.TarifRepository;
import uz.pdp.aloqakampaniyasi.service.MijozService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/mijoz")
public class MjiozController {
    @Autowired
    MijozService mijozService;


    //create
    @PostMapping
    public ResponseEntity<?> addMijoz(@Valid @RequestBody MijozDTO mijozDTO) {
        ApiResponse apiResponse = mijozService.addMijoz(mijozDTO);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(apiResponse);
    }

    //read
    @GetMapping
    public ResponseEntity<?> getMijozs(@RequestParam int page) {
        Page<Mijoz> mijozPage = mijozService.getMijozPage(page);
        if (mijozPage.isEmpty())
            return ResponseEntity.status(HttpStatus.CONFLICT).body("mijozlar yoq");

        return ResponseEntity.status(HttpStatus.OK).body(mijozPage);
    }


    //read1
    @GetMapping("/{id}")
    public ResponseEntity<?> getMijoz(@PathVariable Integer id) {
        Mijoz mijoz = mijozService.getMijoz(id);
        if (mijoz.getPassportKodi() == null)
            return ResponseEntity.status(HttpStatus.CONFLICT).body("topilmadi");

        return ResponseEntity.status(HttpStatus.OK).body(mijoz);

    }




    //delete
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delteeMijoz(@PathVariable Integer id){
        ApiResponse apiResponse = mijozService.deleteMijoz(id);
        return ResponseEntity.status(apiResponse.isSuccess()? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResponse);
    }
}
