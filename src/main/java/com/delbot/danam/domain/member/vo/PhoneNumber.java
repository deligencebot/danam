package com.delbot.danam.domain.member.vo;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PhoneNumber {
  private String areaNumber;
  private String frontNumber;
  private String backNumber;

  @Override
  public String toString() { 
    StringBuilder builder = new StringBuilder();

    builder.append(areaNumber).append("-");
    builder.append(frontNumber).append("-");
    builder.append(backNumber);

    return builder.toString(); 
  }

  public static PhoneNumber of(String areaNumber, String frontNumber, String backNumber) { return new PhoneNumber(areaNumber, frontNumber, backNumber); }

  public static PhoneNumber samplePhoneNumber() { return new PhoneNumber("010", "1234", "5678"); }
}

  