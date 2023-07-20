package uz.pdp.aloqakampaniyasi.service;

import org.apache.logging.log4j.message.SimpleMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.aloqakampaniyasi.entity.Employee;
import uz.pdp.aloqakampaniyasi.entity.Role;
import uz.pdp.aloqakampaniyasi.entity.enums.RoleName;
import uz.pdp.aloqakampaniyasi.payload.ApiResponse;
import uz.pdp.aloqakampaniyasi.payload.EmployeeRegisterDTO;
import uz.pdp.aloqakampaniyasi.payload.LoginDTO;
import uz.pdp.aloqakampaniyasi.repository.EmployeeRepository;
import uz.pdp.aloqakampaniyasi.repository.RoleRepository;
import uz.pdp.aloqakampaniyasi.security.JwtProvider;


import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@Service
public class EmployeeService implements UserDetailsService {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    JavaMailSender javaMailSender;
    @Autowired
    JwtProvider jwtProvider;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return employeeRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email + "topilmadi"));
    }

    //create
    public ApiResponse register(EmployeeRegisterDTO employeeRegisterDTO) {
        Optional<Employee> optionalEmployee = employeeRepository.findByEmail(employeeRegisterDTO.getEmail());
        if (optionalEmployee.isPresent())
            return new ApiResponse("bunday email pochtali user mavjud", false);

        Employee employee = new Employee();
        employee.setEmail(employeeRegisterDTO.getEmail());
        employee.setRole(Collections.singleton(roleRepository.findByRoleName(RoleName.ROLE_EMPLOYEE)));
        employee.setFullName(employeeRegisterDTO.getFullName());
        employee.setPassword(passwordEncoder.encode(employeeRegisterDTO.getPassword()));
        employee.setEmailCode(UUID.randomUUID().toString());
        Employee emloyeinbase = employeeRepository.save(employee);


        sendEmail(emloyeinbase.getEmail(), emloyeinbase.getEmailCode());
        return new ApiResponse("Tasdiqlang", true);

    }


    public Boolean sendEmail(String sendingEmail, String emailCode) {
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom("aloqa@pdp.com");
            simpleMailMessage.setTo(sendingEmail);
            simpleMailMessage.setSubject("Tasdiqlash Kodi");
            simpleMailMessage.setText("<a href = 'http://localhost:8080/api/employee/verifyEmail?emailCode=" + emailCode + "&email=" + sendingEmail + "' >Tasdiqlang</a>");
            javaMailSender.send(simpleMailMessage);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public ApiResponse verifyEmail(String emailCode, String email) {
        Optional<Employee> optionalEmployee = employeeRepository.findByEmailAndEmailCode(email, emailCode);
        if (optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();
            employee.setEnabled(true);
            employee.setEmailCode(null);
            employeeRepository.save(employee);
            return new ApiResponse("tasdiqlandi", true);

        }
        return new ApiResponse("Allaqachon tasdiqlang", false);
    }

    public ApiResponse login(LoginDTO loginDTO) {
        try {
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
            Employee employee = (Employee) authenticate.getPrincipal();
            String token = jwtProvider.generateToken(employee.getUsername(), employee.getRole());
            return new ApiResponse(true, "Token", token);
        } catch (Exception e) {
            return new ApiResponse("parol yoki login notog'ri", false);
        }
    }


    //read
    public Page<Employee> getEmployeePage(int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return employeeRepository.findAllByEnabled(true, pageable);
    }

    //read
    public Employee getEmploye(Integer id){
        Optional<Employee> byId = employeeRepository.findById(id);
        return byId.orElseGet(Employee::new);

    }

    //update
    public ApiResponse updateEmployee(Integer id, EmployeeRegisterDTO employeeRegisterDTO) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (!optionalEmployee.isPresent())
            return new ApiResponse("bunday id li employee topilmadi ", false);

        Employee employee = optionalEmployee.get();
        employee.setFullName(employeeRegisterDTO.getFullName());
        employee.setPassword(employeeRegisterDTO.getPassword());
        employee.setEmail(employeeRegisterDTO.getEmail());
        employee.setId(id);
        employeeRepository.save(employee);
        return new ApiResponse("updated", true);
    }


    //delete
    public ApiResponse deleteEmployee(Integer id){
        try {
            employeeRepository.deleteById(id);
            return new ApiResponse("o'chirildi" , true);
        }catch (Exception e){
            return new ApiResponse("xatolik" ,false );
        }
    }
}
