package uz.pdp.aloqakampaniyasi.projection;

import org.springframework.data.rest.core.config.Projection;
import uz.pdp.aloqakampaniyasi.entity.Paket;

import java.sql.Timestamp;

@Projection(types = Paket.class)
public interface PaketProjection {
    Integer getId();
    String getType();
    Long getCount();
    Timestamp getCreatedAt();
    Timestamp getUpdateAt();
}
