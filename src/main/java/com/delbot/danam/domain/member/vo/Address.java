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
public class Address {
  private String zipCode;
  private String address;

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();

    builder.append(address).append(" ");
    builder.append(zipCode);

    return builder.toString();
  }

  public static Address of(String zipCode, String address) { return new Address(zipCode, address); }

  public static Address sampleAddress() { return new Address("21015", "인천 계양구 다남로 143번길 1"); }
}