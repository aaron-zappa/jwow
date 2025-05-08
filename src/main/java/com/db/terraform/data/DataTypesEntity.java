package com.db.terraform.data;

public class DataTypesEntity {
    private int integerAttribute;
    private double doubleAttribute;
    private boolean booleanAttribute;
    private String stringAttribute;
    private long longAttribute;
    private float floatAttribute;
    private char charAttribute;
    private byte byteAttribute;
    private short shortAttribute;

    public DataTypesEntity(int integerAttribute, double doubleAttribute, boolean booleanAttribute, String stringAttribute, long longAttribute, float floatAttribute, char charAttribute, byte byteAttribute, short shortAttribute) {
        this.integerAttribute = integerAttribute;
        this.doubleAttribute = doubleAttribute;
        this.booleanAttribute = booleanAttribute;
        this.stringAttribute = stringAttribute;
        this.longAttribute = longAttribute;
        this.floatAttribute = floatAttribute;
        this.charAttribute = charAttribute;
        this.byteAttribute = byteAttribute;
        this.shortAttribute = shortAttribute;
    }

    public int getIntegerAttribute() {
        return integerAttribute;
    }

    public double getDoubleAttribute() {
        return doubleAttribute;
    }

    public boolean isBooleanAttribute() {
        return booleanAttribute;
    }

    public String getStringAttribute() {
        return stringAttribute;
    }

    public long getLongAttribute() {
        return longAttribute;
    }

    public float getFloatAttribute() {
        return floatAttribute;
    }

    public char getCharAttribute() {
        return charAttribute;
    }

    public byte getByteAttribute() {
        return byteAttribute;
    }

    public short getShortAttribute() {
        return shortAttribute;
    }
}