package uz.pdp.aloqakampaniyasi.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.aloqakampaniyasi.entity.Role;
import uz.pdp.aloqakampaniyasi.entity.enums.RoleName;

import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role , Integer> {
Role findByRoleName(RoleName roleName);
}
