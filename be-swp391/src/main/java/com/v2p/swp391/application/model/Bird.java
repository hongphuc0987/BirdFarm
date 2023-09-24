package com.v2p.swp391.application.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "birds")

public class Bird extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 350)
    private String name;

    @Column(name = "price",nullable = false)
    private Float price;

    @Column(name = "thumbnail", length = 300)
    private String thumbnail;

    @Column(name = "description")
    private String description;

    @Column(name= "gender")
    private String gender;

    @Column(name= "purebred_level")
    private String purebredLevel;

    @Column(name= " reproductive_history ")
    private int  reproductiveHistory;

    @Column(name="competition_achievements")
    private int competitionAchievements;

    @Column(name= "age")
    private int age;

    @Column(name = "color")
    private String color;


    @Column(name = "status")
    private Boolean status;


    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private BirdType birdType;

}
