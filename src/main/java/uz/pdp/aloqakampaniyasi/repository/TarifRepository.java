package uz.pdp.aloqakampaniyasi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uz.pdp.aloqakampaniyasi.entity.Tarif;

import java.util.Optional;


@RepositoryRestResource(path = "tarif")
public interface TarifRepository extends JpaRepository<Tarif, Integer> {

}
