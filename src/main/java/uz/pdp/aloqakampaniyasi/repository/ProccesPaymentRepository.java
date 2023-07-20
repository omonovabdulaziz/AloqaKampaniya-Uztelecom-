package uz.pdp.aloqakampaniyasi.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.pdp.aloqakampaniyasi.entity.ProccessPayment;

public interface ProccesPaymentRepository extends JpaRepository<ProccessPayment , Integer> {
    Page<ProccessPayment> findAllByTolovTuri(String tolovTuri, Pageable pageable);

    @Query(nativeQuery = true, value = "select count(*) from proccess_payment where paket_id =:paketId")
    Integer calcaulatePaket(@Param("paketId") Integer paketId);
}
