package com.hello.world.domain.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "tb_city")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(setterPrefix = "with", toBuilder = true)
public class City implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    private String name;
    private String zipCode;
    private Integer geonameId;
    private Integer geonameIdFU;
    private Integer geonameIdNation;
    private Integer population;

    @ManyToOne
    @JoinColumn(name = "federative_units_id")
    private FederativeUnits federativeUnits;
    private Integer countCity;
    private Boolean status;

}
