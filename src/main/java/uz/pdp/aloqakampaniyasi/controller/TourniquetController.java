package uz.pdp.aloqakampaniyasi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.aloqakampaniyasi.payload.ApiResponse;
import uz.pdp.aloqakampaniyasi.payload.TourniquetCardDto;
import uz.pdp.aloqakampaniyasi.payload.TourniquetHistoryDto;
import uz.pdp.aloqakampaniyasi.service.TourniquetService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/tourniquet")
public class TourniquetController {

    @Autowired
    TourniquetService tourniquetService;

    @PreAuthorize("hasAnyRole('RAHBAR')")
    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody TourniquetCardDto tourniquetCardDto) {
        ApiResponse apiResponse = tourniquetService.create(tourniquetCardDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }

    @PreAuthorize("hasRole('RAHBAR')")
    @PatchMapping("/update/{email}")
    public ResponseEntity<?> edit(@PathVariable String email, @Valid @RequestBody TourniquetCardDto dto){
        ApiResponse apiResponse = tourniquetService.edit(dto,email);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }


    @PreAuthorize("hasAnyRole( 'RAHBAR')")
    @PatchMapping("/update")
    public ResponseEntity<?> activate(@Valid @RequestBody TourniquetHistoryDto dto) {
        ApiResponse apiResponse = tourniquetService.activate(dto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PostMapping("/enter")
    public ResponseEntity<?> enter(@Valid @RequestBody TourniquetHistoryDto dto){
        ApiResponse apiResponse = tourniquetService.enter(dto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 401).body(apiResponse);
    }

    @PostMapping("/exit")
    public ResponseEntity<?> exit(@Valid @RequestBody TourniquetHistoryDto dto){
        ApiResponse apiResponse = tourniquetService.exit(dto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 401).body(apiResponse);
    }

    @PreAuthorize("hasRole('RAHBAR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        ApiResponse apiResponse = tourniquetService.delete(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 401).body(apiResponse);
    }
}
