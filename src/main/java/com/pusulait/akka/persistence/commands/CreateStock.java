package com.pusulait.akka.persistence.commands;

import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.Value;

@Value
@ToString
@AllArgsConstructor
public class CreateStock {

	private String id;
	private String name;
}
