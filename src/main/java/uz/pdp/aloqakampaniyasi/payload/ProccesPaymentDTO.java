package uz.pdp.aloqakampaniyasi.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProccesPaymentDTO {

    @NotNull
    private String tolovTuri;
    @NotNull
    private Integer simCardId;
    private Long count;
    private String type = "balance";
    private Integer paketId;

}
