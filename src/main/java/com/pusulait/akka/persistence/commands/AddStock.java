package com.pusulait.akka.persistence.commands;

import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.Value;

import java.math.BigDecimal;

@Value
@ToString
@AllArgsConstructor
public class AddStock {
	
	private BigDecimal amount;
}