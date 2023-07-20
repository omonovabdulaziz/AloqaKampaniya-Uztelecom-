package uz.pdp.aloqakampaniyasi.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data@AllArgsConstructor@NoArgsConstructor
public class FillialDTO {
    private String name;
    private String viloyat;
    private Integer employeeId;
}
