package com.hello.world.domain.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "tb_continent")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(setterPrefix = "with", toBuilder = true)
public class Continent implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    private Integer geonameId;
    private Integer geonameIdWorld;
    @Column(unique = true)
    private String name;
    @Column(unique = true)
    private String namePtBr;
    private String population;
    @ManyToOne
    @JoinColumn(name = "world_id")
    private World world;
    private Integer countContinent;
    private Boolean status;

}
