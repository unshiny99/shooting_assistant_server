package com.unshiny99.shooting_assitant_server.Model;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

@Entity
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    private WeaponType weaponType;
    @NotNull
    private TargetType targetType;

    @NotNull
    private int totalPoinsMax;
    @NotNull
    private int totalPointsDone;
    private String name;
    @NotNull
    private boolean isTournament;
    @NotNull
    private Date date;
    private String comment;
}
