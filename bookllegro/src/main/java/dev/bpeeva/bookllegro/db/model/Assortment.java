package dev.bpeeva.bookllegro.db.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "assortments")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Assortment {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Integer amount;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User seller;
}
