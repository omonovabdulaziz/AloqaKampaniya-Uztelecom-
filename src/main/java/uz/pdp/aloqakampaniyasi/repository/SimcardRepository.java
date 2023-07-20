package uz.pdp.aloqakampaniyasi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.RequestParam;
import uz.pdp.aloqakampaniyasi.entity.Simcard;

import java.math.BigInteger;

@RepositoryRestResource(path = "simcard")
public interface SimcardRepository extends JpaRepository<Simcard, Integer> {
    @Modifying
    @Query(nativeQuery = true, value = "update simcard set balance =:balance where id=:id")
    void updateById(@Param("balance") BigInteger balance, @Param("id") Integer id);

    @Modifying
    @Query(nativeQuery = true, value = "update simcard set sms=:sms where id=:id")
    void updatesms(@Param("sms") Long sms, @Param("id") Integer id);


    @Modifying
    @Query(nativeQuery = true, value = "update simcard set daqiqa=:daqiqa where id=:id")
    void updatedaqiqa(@Param("daqiqa") Long daqiqa, @Param("id") Integer id);

    @Modifying
    @Query(nativeQuery = true, value = "update simcard set mb=:mb where id=:id")
    void updatemb(@Param("mb") Long mb, @Param("id") Integer id);

}
