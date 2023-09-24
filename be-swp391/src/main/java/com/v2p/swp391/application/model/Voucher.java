package com.v2p.swp391.application.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "vouchers")
public class Voucher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "discount", nullable = false)
    private int discount;

    @Column(name = "amount")
    private int amount;

    @Column(name = "description", length = 200)
    private String description;

    @Column(name = "expiration_date")
    private LocalDateTime expirationDate;

    @Column(name = "status")
    private Boolean status;


}
