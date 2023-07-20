package uz.pdp.aloqakampaniyasi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import uz.pdp.aloqakampaniyasi.entity.Employee;
import uz.pdp.aloqakampaniyasi.entity.Fillial;
import uz.pdp.aloqakampaniyasi.entity.Role;
import uz.pdp.aloqakampaniyasi.entity.enums.RoleName;
import uz.pdp.aloqakampaniyasi.payload.ApiResponse;
import uz.pdp.aloqakampaniyasi.payload.FillialDTO;
import uz.pdp.aloqakampaniyasi.repository.EmployeeRepository;
import uz.pdp.aloqakampaniyasi.repository.FillialRepository;
import uz.pdp.aloqakampaniyasi.repository.RoleRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class FillialService {
    @Autowired
    FillialRepository fillialRepository;


    @Autowired
    EmployeeRepository employeeRepository;

    public ApiResponse addFillial(FillialDTO fillialDTO) {
        boolean b = fillialRepository.existsByNameAndViloyat(fillialDTO.getName(), fillialDTO.getViloyat());
        if (b)
            return new ApiResponse("bunday nomli fillial ushbu viloyatta mavjud ", false);

        Optional<Employee> op = employeeRepository.findById(fillialDTO.getEmployeeId());
        if (!op.isPresent())
            return new ApiResponse("budany id li employee ytoq", false);

        Employee employee = op.get();
        Fillial fillial = new Fillial();
        fillial.setRahbar(employee);
        fillial.setName(fillialDTO.getName());
        fillial.setViloyat(fillialDTO.getViloyat());
        fillialRepository.save(fillial);
        return new ApiResponse("saqlandi ", true);
    }

    public List<Fillial> getFillial() {
        return fillialRepository.findAll();
    }

    public Fillial getById(Integer id) {
        Optional<Fillial> optionalFillial = fillialRepository.findById(id);
        if (!optionalFillial.isPresent())
            return new Fillial();

        return optionalFillial.get();
    }

    public ApiResponse updatefillial(Integer id, FillialDTO fillialDTO) {
        Optional<Fillial> optionalFillial = fillialRepository.findById(id);
        if (!optionalFillial.isPresent())
            return new ApiResponse("bunday id li fillial topilmadi ", false);

        Optional<Employee> optionalEmploye = employeeRepository.findById(fillialDTO.getEmployeeId());
        if (!optionalEmploye.isPresent())
            return new ApiResponse("employe yoq", false);

        Employee employee = optionalEmploye.get();

        Fillial fillial = optionalFillial.get();
        fillial.setViloyat(fillialDTO.getViloyat());
        fillial.setId(id);
        fillial.setName(fillialDTO.getName());
        fillial.setRahbar(employee);
        fillialRepository.save(fillial);
        return new ApiResponse("updated", true);

    }

    public ApiResponse delteFillial(Integer id) {
        try {
            fillialRepository.deleteById(id);
            return new ApiResponse("deleted", true);
        } catch (Exception e) {
            return new ApiResponse("eroor", false);
        }
    }

}
