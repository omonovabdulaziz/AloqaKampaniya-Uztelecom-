package uz.pdp.aloqakampaniyasi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.aloqakampaniyasi.entity.*;
import uz.pdp.aloqakampaniyasi.payload.ApiResponse;
import uz.pdp.aloqakampaniyasi.repository.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Service
public class DashboardService {
    @Autowired
    DashboardRepository dashboardRepository;

    @Autowired
    MijozRepository mijozRepository;
    @Autowired
    TarifRepository tarifRepository;

    @Autowired
    ProccesPaymentRepository proccesPaymentRepository;

    @Autowired
    PaketRepository paketRepository;

    public ApiResponse setActiveThings() {
        Dashboard dashboard = new Dashboard();
        Integer maximal = 0;
        Integer activeTarifId = 0;
        Integer i = 1;
        for (Mijoz mijoz : mijozRepository.findAll()) {
            Integer tarifCount = mijozRepository.calcaulateTarif(i);
            if (maximal < tarifCount) {
                maximal = tarifCount;
                activeTarifId = i;
            }
            i++;
        }

        maximal = 0;
        Integer activePaketId = 0;
        i = 1;
        for (ProccessPayment proccessPayment : proccesPaymentRepository.findAll()) {
            Integer paketCount = proccesPaymentRepository.calcaulatePaket(i);
            if (maximal < paketCount) {
                maximal = paketCount;
                activePaketId = i;
            }
            i++;
        }

        Set<Tarif> tarifs = new HashSet<>();
        Tarif tarif = tarifRepository.findById(activeTarifId).get();
        tarifs.add(tarif);
        dashboard.setActiveTarif(tarifs);
        Set<Paket> pakets = new HashSet<>();
        Paket paket = paketRepository.findById(activePaketId).get();
        pakets.add(paket);
        dashboard.setActivePaket(pakets);
        dashboardRepository.save(dashboard);
        return new ApiResponse("active paket va tariflar saqlandi", true);
    }

}
