package uz.pdp.aloqakampaniyasi.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TourniquetCardDto implements Serializable {

    @NotNull
    private String employeeEmail;
}
