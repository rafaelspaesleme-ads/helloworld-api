package com.hello.world.domain.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "tb_nation")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(setterPrefix = "with", toBuilder = true)
public class Nation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(unique = true)
    private String name;
    private String nativeName;
    private String shortName;
    private Integer geonameId;
    private Integer geonameIdContinent;
    private String flag;
    private Integer population;
    @ManyToOne
    @JoinColumn(name = "continent_id")
    private Continent continent;
    private Integer countNation;
    private Boolean status;
}
