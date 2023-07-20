package uz.pdp.aloqakampaniyasi.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.pdp.aloqakampaniyasi.entity.Mijoz;


public interface MijozRepository extends JpaRepository<Mijoz, Integer> {
    Page<Mijoz> findByActive(boolean active, Pageable pageable);

    boolean existsBySimCardId(Integer simCard_id);

    @Query(nativeQuery = true, value = "select tarif_id from mijoz where sim_card_id =:simcardid")
    Integer findBySimCardId(@Param("simcardid") Integer simcardid);


    @Modifying
    @Query(nativeQuery = true, value = "update mijoz set tarif_id=:tarifid where sim_card_id=:simcardid")
    void updateBysimCardId(@Param("tarifid") Integer tarifid, @Param("simcardid") Integer simcardid);


    @Query(nativeQuery = true, value = "select count(*) from mijoz where tarif_id =:tarifid")
    Integer calcaulateTarif(@Param("tarifid") Integer tarifid);

}
