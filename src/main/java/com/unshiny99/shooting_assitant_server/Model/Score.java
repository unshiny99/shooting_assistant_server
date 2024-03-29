package com.unshiny99.shooting_assitant_server.Model;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private WeaponType weaponType;
    @Column(nullable = false)
    private TargetType targetType;

    @Column(nullable = false)
    private int totalPoinsMax;
    @Column(nullable = false)
    private int totalPointsDone;
    private String name;
    @Column(nullable = false)
    private boolean isTournament;
    @Column(nullable = false)
    private Date date;
    private String comment;
}
