package uz.pdp.aloqakampaniyasi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import uz.pdp.aloqakampaniyasi.entity.Tarif;
import uz.pdp.aloqakampaniyasi.entity.UssdCode;
import uz.pdp.aloqakampaniyasi.payload.ApiResponse;
import uz.pdp.aloqakampaniyasi.repository.MijozRepository;
import uz.pdp.aloqakampaniyasi.repository.TarifRepository;
import uz.pdp.aloqakampaniyasi.repository.UssdCodeRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UssdCodeService {
    @Autowired
    UssdCodeRepository ussdCodeRepository;

    @Autowired
    TarifRepository tarifRepository;
    @Autowired
    MijozRepository mijozRepository;

    public List<UssdCode> getUssdCode() {
        return ussdCodeRepository.findAll();
    }


    public ApiResponse getTarif(Integer kodi, Integer simcardId) {
        if (kodi == 1001) {
            boolean existsBySimCardId = mijozRepository.existsBySimCardId(simcardId);
            if (existsBySimCardId) {
                Integer tarifId = mijozRepository.findBySimCardId(simcardId);
                Optional<Tarif> optionalTarif = tarifRepository.findById(tarifId);
                return new ApiResponse(true, "hammasi joyida", optionalTarif.get());
            }
            return new ApiResponse("bunday simcard egasi yoq", false);
        }
        return new ApiResponse("notogri kod kiritildi tarif koruvchi kod 1001", false);
    }


    public ApiResponse updateTarif(Integer kodi, Integer simcardId, Integer newtarifId) {
        if (kodi == 2002) {
            boolean existsBySimCardId = mijozRepository.existsBySimCardId(simcardId);
            if (existsBySimCardId) {
                    mijozRepository.updateBysimCardId(newtarifId, simcardId);
                    return new ApiResponse(true, "tarifingiz yangilandi ", "tarifingiz id si  " + newtarifId);
            }
            return new ApiResponse("bunday simcard egasi yoq", false);
        }
        return new ApiResponse("notogri kod kiritildi tarif o'zgartiruvchi kod 2002", false);
    }

}
