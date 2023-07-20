package uz.pdp.aloqakampaniyasi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import uz.pdp.aloqakampaniyasi.entity.Mijoz;
import uz.pdp.aloqakampaniyasi.entity.Simcard;
import uz.pdp.aloqakampaniyasi.entity.Tarif;
import uz.pdp.aloqakampaniyasi.payload.ApiResponse;
import uz.pdp.aloqakampaniyasi.payload.MijozDTO;
import uz.pdp.aloqakampaniyasi.repository.MijozRepository;
import uz.pdp.aloqakampaniyasi.repository.SimcardRepository;
import uz.pdp.aloqakampaniyasi.repository.TarifRepository;

import javax.validation.Valid;
import java.util.Optional;

@Service
public class MijozService {
    @Autowired
    MijozRepository mijozRepository;
    @Autowired
    SimcardRepository simcardRepository;

    @Autowired
    TarifRepository tarifRepository;

    //add
    public ApiResponse addMijoz(MijozDTO mijozDTO) {
        try {
            Optional<Simcard> optionalSimcard = simcardRepository.findById(mijozDTO.getSimCardId());
            if (!optionalSimcard.isPresent())
                return new ApiResponse("bunday id li simcarda topilmadi", true);
            boolean existsBySimCardId = mijozRepository.existsBySimCardId(mijozDTO.getSimCardId());
            if (existsBySimCardId)
                return new ApiResponse("bu simkarta boshqa mijozga tegishli " ,false);

            Simcard simcard = optionalSimcard.get();
            Optional<Tarif> optionalTarif = tarifRepository.findById(mijozDTO.getTarifId());
            if (!optionalTarif.isPresent())
                return new ApiResponse("bunda id li tarif topilmadi", false);


            Tarif tarif = optionalTarif.get();

            Mijoz mijoz = new Mijoz();
            mijoz.setTarif(tarif);
            mijoz.setYuridik(true);
            mijoz.setFullName(mijozDTO.getFullName());
            mijoz.setPassportKodi(mijozDTO.getPassportKodi());
            mijoz.setSimCard(simcard);
            mijozRepository.save(mijoz);
            return new ApiResponse("saved", true);
        } catch (Exception e) {
            return new ApiResponse("qanday dir hatolik boldi yahshilab tekshirib yozing menimcha boshqa mijoz pasporrtidan foydalangansiz", false);
        }

    }

    //read
    public Page<Mijoz> getMijozPage(int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return mijozRepository.findByActive(true, pageable);
    }

    //read1
    public Mijoz getMijoz(Integer id) {
        Optional<Mijoz> optionalMijoz = mijozRepository.findById(id);
        return optionalMijoz.orElseGet(Mijoz::new);

    }


    //delte
    public ApiResponse deleteMijoz(Integer id) {
        try {
            mijozRepository.deleteById(id);
            return new ApiResponse("deleted", true);
        } catch (Exception e) {
            return new ApiResponse("delete rejected", false);
        }
    }

}
