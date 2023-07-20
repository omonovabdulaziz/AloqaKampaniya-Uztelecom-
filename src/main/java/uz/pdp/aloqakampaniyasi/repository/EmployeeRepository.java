package uz.pdp.aloqakampaniyasi.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.aloqakampaniyasi.entity.Employee;

import javax.swing.text.html.Option;
import javax.validation.constraints.Email;
import java.util.Optional;
import java.util.UUID;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    Optional<Employee> findByEmail(@Email String email);

    Optional<Employee> findByEmailAndEmailCode(@Email String email, String emailCode);

    Page<Employee> findAllByEnabled(boolean enabled, Pageable pageable);
}
