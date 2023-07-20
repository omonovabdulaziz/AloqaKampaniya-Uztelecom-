package uz.pdp.aloqakampaniyasi.config;


import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import uz.pdp.aloqakampaniyasi.entity.Employee;

import java.util.Optional;

public class KimYozganiniBilish implements AuditorAware<Integer> {
    @Override
    public Optional<Integer> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !authentication.getPrincipal().equals("anonymousUser")) {
            Employee employee = (Employee) authentication.getPrincipal();
            return Optional.of(employee.getId());
        }
        return Optional.empty();
    }
}
