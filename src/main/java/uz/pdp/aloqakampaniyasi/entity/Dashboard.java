package uz.pdp.aloqakampaniyasi.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Dashboard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToMany
    private Set<Tarif> activeTarif;
    @ManyToMany
    private Set<Paket> activePaket;
    @CreationTimestamp@Column(updatable = false)
    private Timestamp createdTime;


    public void setActiveTarif(Set<Tarif> activeTarif) {
        this.activeTarif = activeTarif;
    }

    public void setActivePaket(Set<Paket> activePaket) {
        this.activePaket = activePaket;
    }
}
