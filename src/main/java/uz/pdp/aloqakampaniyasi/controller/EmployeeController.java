package uz.pdp.aloqakampaniyasi.controller;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.aloqakampaniyasi.entity.Employee;
import uz.pdp.aloqakampaniyasi.payload.ApiResponse;
import uz.pdp.aloqakampaniyasi.payload.EmployeeRegisterDTO;
import uz.pdp.aloqakampaniyasi.payload.LoginDTO;
import uz.pdp.aloqakampaniyasi.service.EmployeeService;

import javax.persistence.CascadeType;
import javax.persistence.OneToOne;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;



    //create
    @PostMapping("/register")
    public ResponseEntity<?> RegisterEmployee(@RequestBody EmployeeRegisterDTO employeeRegisterDTO) {
        ApiResponse apiResponse = employeeService.register(employeeRegisterDTO);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }

    @GetMapping("/verifyEmail")
    public ResponseEntity<?> verifyEmail(@RequestParam String emailCode, @RequestParam String email) {
        ApiResponse apiResponse = employeeService.verifyEmail(emailCode, email);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        ApiResponse apiResponse = employeeService.login(loginDTO);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.ACCEPTED : HttpStatus.CONFLICT).body(apiResponse);
    }

    //read
    @PreAuthorize("hasAnyRole('RAHBAR' , 'MENEGER' , 'EMPLOYEE')")
    @GetMapping
    public ResponseEntity<?> getEmployeePage(@RequestParam int page) {
        Page<Employee> employeePage = employeeService.getEmployeePage(page);
        if (employeePage == null)
            return ResponseEntity.status(HttpStatus.CONFLICT).body("activeEmployelar yoq");

        return ResponseEntity.status(HttpStatus.OK).body(employeePage);
    }

    //redad
    @PreAuthorize("hasAnyRole('RAHBAR' , 'MENEGER' , 'EMPLOYEE')")
    @GetMapping("/{id}")
    public Employee getEmployee(@PathVariable Integer id){
        return employeeService.getEmploye(id);
    }

    //update
    @PreAuthorize("hasAnyRole('RAHBAR' , 'MENEGER')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmplooyePage(@PathVariable Integer id, @RequestBody EmployeeRegisterDTO employeeRegisterDTO) {
        ApiResponse apiResponse = employeeService.updateEmployee(id, employeeRegisterDTO);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.ACCEPTED : HttpStatus.CONFLICT).body(apiResponse);

    }

    //delete

    @PreAuthorize("hasAnyRole('RAHBAR' , 'MENEGER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployeePage(@PathVariable Integer id){
        ApiResponse apiResponse = employeeService.deleteEmployee(id);
        return ResponseEntity.status(apiResponse.isSuccess()? 200 : 409).body(apiResponse);
    }


}
