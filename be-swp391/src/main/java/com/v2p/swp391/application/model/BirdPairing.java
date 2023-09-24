package com.v2p.swp391.application.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bird_pairing")
public class BirdPairing extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "bird_types_id")
    private BirdType birdType;

    @ManyToOne
    @JoinColumn(name = "father_bird_id")
    private Bird fatherBird;

    @ManyToOne
    @JoinColumn(name = "mother_bird_id")
    private Bird motherBird;

}
