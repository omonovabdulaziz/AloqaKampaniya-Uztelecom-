package uz.pdp.aloqakampaniyasi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Simcard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false, unique = true)
    private String number;
    private Long balance;
    private Long sms;
    private Long daqiqa;
    private Long mb;
    private Date oyliktolovexpired = new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 30);
}
