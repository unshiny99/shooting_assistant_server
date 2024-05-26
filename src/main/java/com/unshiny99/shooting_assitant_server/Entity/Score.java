package com.unshiny99.shooting_assitant_server.Entity;

import java.util.Date;

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
    private int totalPointsMax;
    @NotNull
    private int totalPointsDone;
    private String name;
    @NotNull
    private boolean isTournament;
    @NotNull
    private Date date;
    private String comment;

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public WeaponType getWeaponType() {
        return weaponType;
    }
    public void setWeaponType(WeaponType weaponType) {
        this.weaponType = weaponType;
    }
    public TargetType getTargetType() {
        return targetType;
    }
    public void setTargetType(TargetType targetType) {
        this.targetType = targetType;
    }
    public int gettotalPointsMax() {
        return totalPointsMax;
    }
    public void settotalPointsMax(int totalPointsMax) {
        this.totalPointsMax = totalPointsMax;
    }
    public int getTotalPointsDone() {
        return totalPointsDone;
    }
    public void setTotalPointsDone(int totalPointsDone) {
        this.totalPointsDone = totalPointsDone;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public boolean isTournament() {
        return isTournament;
    }
    public void setTournament(boolean isTournament) {
        this.isTournament = isTournament;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }

    public Score() {}

    public Score(@NotNull WeaponType weaponType, @NotNull TargetType targetType, @NotNull int totalPointsMax,
            @NotNull int totalPointsDone, @NotNull boolean isTournament, @NotNull Date date) {
        this.weaponType = weaponType;
        this.targetType = targetType;
        this.totalPointsMax = totalPointsMax;
        this.totalPointsDone = totalPointsDone;
        this.isTournament = isTournament;
        this.date = date;
    }
}
