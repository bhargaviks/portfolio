package com.google.sps.data;

/** Represents an important place at a specific lat lng point with the passed in info window. */
public class Point {
  private double lat;
  private double lng;
  private String info;

  public Point(double lat, double lng, String info) {
    this.lat = lat;
    this.lng = lng;
    this.info = info;
  }
}
