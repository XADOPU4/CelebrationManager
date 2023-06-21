package com.eventmanager.coreservicediploma.model.entity.user;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "userinfo")
public class Location {
    @Column(name = "country")
    private String country;
    @Column(name = "region")
    private String region;
    @Column(name = "city")
    private String city;
    @Column(name = "street")
    private String street;
    @Column(name = "house")
    private String house;
    @Column(name = "lat")
    private String lat;
    @Column(name = "lon")
    private String lon;

    public Location(String lat, String lon)
    {
        this.lat = lat;
        this.lon = lon;
    }
}
