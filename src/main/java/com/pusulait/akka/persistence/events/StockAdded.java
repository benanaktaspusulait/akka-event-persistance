package com.pusulait.akka.persistence.events;

import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;

@Value
@ToString
@AllArgsConstructor
public class StockAdded implements Serializable {

	private static final long serialVersionUID = 2011488881258695646L;

	private BigDecimal amount;

}
