package uz.pdp.aloqakampaniyasi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.aloqakampaniyasi.entity.Fillial;

public interface FillialRepository extends JpaRepository<Fillial , Integer> {
    boolean existsByNameAndViloyat(String name, String viloyat);
}
