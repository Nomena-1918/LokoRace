package org.race.loko.models.business;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.race.loko.utils.csv.dto.PointCSV;

@Setter
@Getter
@Entity(name = "point_classements")
public class PointClassement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rang", nullable = false)
    private Integer rang;

    @Column(name = "points", nullable = false)
    private Integer points;


    public PointClassement(PointCSV pointCSV) {
        this.rang = pointCSV.getClassement();
        this.points = pointCSV.getPoints();
    }

    public PointClassement() {
    }
}
