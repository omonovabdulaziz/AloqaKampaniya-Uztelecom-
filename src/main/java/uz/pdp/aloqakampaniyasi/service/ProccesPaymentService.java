package uz.pdp.aloqakampaniyasi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import uz.pdp.aloqakampaniyasi.entity.*;
import uz.pdp.aloqakampaniyasi.payload.ApiResponse;
import uz.pdp.aloqakampaniyasi.payload.ProccesPaymentDTO;
import uz.pdp.aloqakampaniyasi.repository.*;

import java.math.BigInteger;
import java.util.Optional;


@Service
public class ProccesPaymentService {
    @Autowired
    ProccesPaymentRepository proccesPaymentRepository;
    @Autowired
    TarifRepository tarifRepository;
    @Autowired
    MijozRepository mijozRepository;
    @Autowired
    SimcardRepository simcardRepository;
    @Autowired
    PaketRepository paketRepository;

    //create
    public ApiResponse addProccesPaymentService(ProccesPaymentDTO proccesPaymentDTO) {
        Optional<Simcard> optionalSimcard = simcardRepository.findById(proccesPaymentDTO.getSimCardId());
        if (!optionalSimcard.isPresent())
            return new ApiResponse("bunay sim karta mavjud emas", false);
        Simcard simcard = optionalSimcard.get();


        if (proccesPaymentDTO.getType().equalsIgnoreCase("balance")) {
            Long newBalance = simcard.getBalance() + proccesPaymentDTO.getCount();
            simcardRepository.updateById(BigInteger.valueOf(newBalance), proccesPaymentDTO.getSimCardId());
            ProccessPayment proccessPayment = new ProccessPayment();
            proccessPayment.setTolovTuri(proccesPaymentDTO.getTolovTuri());
            proccessPayment.setCount(proccesPaymentDTO.getCount());
            proccessPayment.setSimcard(simcard);
            ProccessPayment save = proccesPaymentRepository.save(proccessPayment);
            return new ApiResponse("o'tkazildi balancingizda " + save.getCount() + "som bor", true);
        } else {
            Optional<Paket> optionalPaket = paketRepository.findById(proccesPaymentDTO.getPaketId());
            if (!optionalPaket.isPresent())
                return new ApiResponse("bunday id li paket yoq", false);

            Paket paket = optionalPaket.get();
            String type = paket.getType();
            if (type.equals("sms")) {
                long newsms = simcard.getSms() + paket.getCount();
                simcardRepository.updatesms(newsms, proccesPaymentDTO.getSimCardId());
                return new ApiResponse("o'tkazildi balancingizda " + newsms + "ta sms bor", true);
            } else {
                if (type.equals("daqiqa")) {
                    long newdaqiqa = simcard.getDaqiqa() + paket.getCount();
                    simcardRepository.updatedaqiqa(newdaqiqa, proccesPaymentDTO.getSimCardId());
                    return new ApiResponse("o'tkazildi balancingizda " + newdaqiqa + "ta daqiqa bor", true);

                } else {
                    if (type.equals("mb")) {
                        long newmb = simcard.getMb() + paket.getCount();
                        simcardRepository.updatemb(newmb, proccesPaymentDTO.getPaketId());
                        return new ApiResponse("o'tkazildi balancingizda " + newmb + "ta mb bor", true);

                    }
                }
            }
            return new ApiResponse("qandaydir xatolik yuz berdi", true);
        }


    }


    //readbyTolovTuri
    public Page<ProccessPayment> getPageProccesPayment(String tolovTuri, int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return proccesPaymentRepository.findAllByTolovTuri(tolovTuri, pageable);
    }

    //readbyid
    public ProccessPayment getProccessPayment(Integer id) {
        Optional<ProccessPayment> optionalProccess = proccesPaymentRepository.findById(id);
        if (!optionalProccess.isPresent())
            return null;

        return optionalProccess.get();
    }

    //updateable false


    //delete
    public ApiResponse deleteProccesPayment(Integer id) {
        try {
            proccesPaymentRepository.deleteById(id);
            return new ApiResponse("deleted", true);
        } catch (Exception e) {
            return new ApiResponse("xatolik", false);
        }
    }


}
