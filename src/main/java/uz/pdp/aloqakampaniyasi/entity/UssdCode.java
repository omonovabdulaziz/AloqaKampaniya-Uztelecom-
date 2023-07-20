package uz.pdp.aloqakampaniyasi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

@Data@AllArgsConstructor@NoArgsConstructor@Entity@EntityListeners(AuditingEntityListener.class)
public class UssdCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Integer id;
    private Long kodi;
    private String vazifasi;



}

