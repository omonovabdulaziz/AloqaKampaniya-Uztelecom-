package uz.pdp.aloqakampaniyasi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.aloqakampaniyasi.entity.TourniquetCard;

import javax.validation.constraints.Email;
import java.util.Optional;

public interface TourniquetRepository extends JpaRepository<TourniquetCard ,Integer> {
    Optional<TourniquetCard> findByEmployee_EmailAndStatusTrue(@Email String employee_email);
}
