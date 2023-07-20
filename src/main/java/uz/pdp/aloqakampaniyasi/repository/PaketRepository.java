package uz.pdp.aloqakampaniyasi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uz.pdp.aloqakampaniyasi.entity.Paket;
import uz.pdp.aloqakampaniyasi.projection.PaketProjection;

@RepositoryRestResource(path = "paket" , excerptProjection = PaketProjection.class)
public interface PaketRepository extends JpaRepository<Paket ,Integer> {
}
