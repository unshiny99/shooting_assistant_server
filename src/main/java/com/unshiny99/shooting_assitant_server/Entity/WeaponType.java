package com.unshiny99.shooting_assitant_server.Entity;

public enum WeaponType {
    RIFLE("carabine"),
    PISTOL("pistolet");

    private final String value;

    private WeaponType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
