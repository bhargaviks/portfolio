package com.google.sps.data;

/** A single comment in the discussion page */
public final class Comment {

  private final long id;
  private final String text;
  private final long timestamp;
  private final double score;

  public Comment(long id, String text, long timestamp, double score) {
    this.id = id;
    this.text = text;
    this.timestamp = timestamp;
    this.score = score;
  }
}
