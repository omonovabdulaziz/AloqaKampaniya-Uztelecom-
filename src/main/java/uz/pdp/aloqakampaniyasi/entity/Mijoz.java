package uz.pdp.aloqakampaniyasi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.pdp.aloqakampaniyasi.entity.enums.RoleName;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Mijoz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String fullName;
    @ManyToOne
    private Simcard simCard;
    @CreatedBy
    @Column(updatable = false)
    private Integer createdBy;
    @LastModifiedBy
    private Integer updateBy;
    @OneToOne
    private Tarif tarif;
    private boolean active = true;
    private boolean isYuridik = false;
    @Column(unique = true)
    private String passportKodi;

    //    ushbu funksiya tarif tugaganda activeligini ochirish uchun

}
