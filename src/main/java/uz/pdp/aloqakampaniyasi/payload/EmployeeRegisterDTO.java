package uz.pdp.aloqakampaniyasi.payload;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRegisterDTO {
    @NotNull(message = "name kiriting")
    private String fullName;
    @NotNull(message = "email kirit ushbu narsa username sifatida ishlatiladi")
    private String email;
    @NotNull(message = "passwordni kiriting")
    private String password;
}
