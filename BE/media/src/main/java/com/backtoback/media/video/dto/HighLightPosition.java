package com.backtoback.media.video.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class HighLightPosition implements Serializable {

  private double start;
  private double finish;

  public HighLightPosition(double start, double finish) {
    this.start = start;
    this.finish = finish;
  }
}
