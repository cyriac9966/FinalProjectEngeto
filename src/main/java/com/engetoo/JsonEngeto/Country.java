package com.engetoo.JsonEngeto;

import com.google.gson.JsonObject;

import java.math.BigDecimal;

public class Country {
    String name;
    String abbreviation;
    BigDecimal rate;
    BigDecimal reduceRate;
    BigDecimal reducedRateAlt;
    BigDecimal parkingRate;
    BigDecimal superReducedRate;


    public Country(JsonObject countryJson, String abbreviation) {
        this.name = countryJson.get("country").toString();
        this.abbreviation = abbreviation;
         this.rate = extractBigDecimal(countryJson.get("standard_rate").toString());
        this.reduceRate = extractBigDecimal(countryJson.get("reduced_rate").toString());
        this.reducedRateAlt = extractBigDecimal(countryJson.get("reduced_rate_alt").toString());
        this.superReducedRate = extractBigDecimal(countryJson.get("super_reduced_rate").toString());
        this.parkingRate = extractBigDecimal(countryJson.get("parking_rate").toString());
    }

    public BigDecimal getReduceRate() {
        return reduceRate;
    }

    public void setReduceRate(String reduceRate) {
        this.reduceRate = extractBigDecimal(reduceRate);
    }

    public BigDecimal getReducedRateAlt() {
        return reducedRateAlt;
    }

    public void setReducedRateAlt(String reducedRateAlt) {

        this.reducedRateAlt = extractBigDecimal(reducedRateAlt);
    }
    private BigDecimal extractBigDecimal(String attribute){
        return attribute.equals("false") ? null : new BigDecimal(attribute);
    }

    public BigDecimal getSuperReducedRate() {
        return superReducedRate;
    }

    public void setSuperReducedRate(String superReducedRate) {
        this.superReducedRate = extractBigDecimal(superReducedRate);
    }

    public BigDecimal getParkingRate() {
        return parkingRate;
    }

    public void setParkingRate(String parkingRate) {
        this.parkingRate = extractBigDecimal(parkingRate);
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = extractBigDecimal(rate);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "Country{" +
                "name='" + name + '\'' +
                ", abbreviation='" + abbreviation + '\'' +
                ", rate=" + rate +
                ", reduceRate=" + reduceRate +
                ", reducedRateAlt=" + reducedRateAlt +
                ", parkingRate=" + parkingRate +
                ", superReducedRate=" + superReducedRate +
                '}';
    }



}
