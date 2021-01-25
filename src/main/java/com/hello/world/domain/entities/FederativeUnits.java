package com.hello.world.domain.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "tb_federative_units")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(setterPrefix = "with", toBuilder = true)
public class FederativeUnits implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private String name;
    private String shortName;
    private Integer geonameId;
    private Integer geonameIdNation;
    private Integer population;
    @ManyToOne
    @JoinColumn(name = "nation_id")
    private Nation nation;

    private String shortNameNation;
    private Integer countFU;
    private Boolean status;
}
