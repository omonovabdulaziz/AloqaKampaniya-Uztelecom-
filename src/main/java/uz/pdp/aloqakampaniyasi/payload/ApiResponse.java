package uz.pdp.aloqakampaniyasi.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse {
    private boolean success;
    private String message;
    private Object object;

    public ApiResponse( String message , boolean success) {
        this.success = success;
        this.message = message;
    }
}
