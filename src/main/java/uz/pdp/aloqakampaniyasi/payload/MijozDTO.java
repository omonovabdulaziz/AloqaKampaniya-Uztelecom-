package uz.pdp.aloqakampaniyasi.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.aloqakampaniyasi.entity.Role;
import uz.pdp.aloqakampaniyasi.entity.enums.RoleName;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MijozDTO {
    @NotNull(message = "bosh bolmasin")
    private String fullName;
    @NotNull(message = "bosh bolmasin")
    private Integer simCardId;
    @NotNull(message = "bosh bolmasin")
    private Integer tarifId;
    @NotNull(message = "bosh bolmasin")
    private String passportKodi;
    private boolean isYuridik = false;
}
