package com.pusulait.akka.persistence;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.pusulait.akka.persistence.aggregate.Stock;
import com.pusulait.akka.persistence.commands.CreateStock;
import com.pusulait.akka.persistence.commands.TakeSnapshot;
import com.pusulait.akka.persistence.events.StockAdded;
import com.pusulait.akka.persistence.events.StockWithdrawed;

import java.math.BigDecimal;

public class AkkaPersistenceApplicationMain {

	private static final String STOCK_NUMBER = "RFG111";
	private static final String STOCK_NAME = "REFRIGERATOR";

	public static void main(String[] args) {

		ActorSystem system = ActorSystem.create("StockSystem");
		
		final ActorRef persistentActor = system
				.actorOf(Props.create(Stock.class, () -> new Stock(STOCK_NUMBER, STOCK_NAME)));
		persistentActor.tell("print", ActorRef.noSender());
		persistentActor.tell(new CreateStock(STOCK_NUMBER, STOCK_NAME), ActorRef.noSender());
		persistentActor.tell(new TakeSnapshot(), ActorRef.noSender());
		persistentActor.tell("print", ActorRef.noSender());
		persistentActor.tell(new StockAdded(BigDecimal.ONE), ActorRef.noSender());
		persistentActor.tell(new TakeSnapshot(), ActorRef.noSender());
		persistentActor.tell("print", ActorRef.noSender());
		persistentActor.tell(new StockAdded(BigDecimal.TEN), ActorRef.noSender());
		persistentActor.tell(new TakeSnapshot(), ActorRef.noSender());
		persistentActor.tell("print", ActorRef.noSender());
		persistentActor.tell(new StockWithdrawed(BigDecimal.ONE), ActorRef.noSender());
		persistentActor.tell(new TakeSnapshot(), ActorRef.noSender());
		persistentActor.tell("print", ActorRef.noSender());
	}
}
