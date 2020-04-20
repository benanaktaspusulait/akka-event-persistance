package com.pusulait.akka.persistence.events;

import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.Value;

import java.io.Serializable;

@Value
@ToString
@AllArgsConstructor
public class StockCreated implements Serializable {

	private static final long serialVersionUID = -2962494667385976277L;

	private String id;
	private String name;
}