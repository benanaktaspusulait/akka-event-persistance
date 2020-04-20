package com.pusulait.akka.persistence.aggregate;

import akka.persistence.AbstractPersistentActor;
import akka.persistence.RecoveryCompleted;
import com.pusulait.akka.persistence.commands.AddStock;
import com.pusulait.akka.persistence.commands.CreateStock;
import com.pusulait.akka.persistence.commands.TakeSnapshot;
import com.pusulait.akka.persistence.commands.WithdrawStock;
import com.pusulait.akka.persistence.events.StockAdded;
import com.pusulait.akka.persistence.events.StockCreated;
import com.pusulait.akka.persistence.events.StockWithdrawed;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

@Slf4j
@ToString
public class Stock extends AbstractPersistentActor {

	private String id;
	private String name;
	private BigDecimal amount;
	private StockState state = new StockState();

	public Stock(String id, String name) {
		this.amount = BigDecimal.ZERO;
		this.id = id;
		this.name = name;
	}
	
	@Override
	public String persistenceId() {
		return id;
	}


	@Override
	public Receive createReceiveRecover() {
		return receiveBuilder().match(StockCreated.class, event -> {
			state.update(event);
			this.amount = BigDecimal.ZERO;
			this.id = event.getId();
			this.name = event.getName();
			System.out.println("Replay Event StockCreated. " + toString());
		}).match(AddStock.class, event -> {
			state.update(event);
			this.amount = amount.add(event.getAmount());
			System.out.println("Replay Event StockAdded " + event.getAmount() + " " + toString());
		}).match(WithdrawStock.class, event -> {
			state.update(event);
			this.amount = amount.subtract(event.getAmount());
			System.out.println("Replay Event StockWithdrawn " + event.getAmount() + " " + toString());
		}).match(RecoveryCompleted.class, e -> {
			System.out.println("Replay Events Completed!...");
		}).build();
	}

	@Override
	public Receive createReceive() {
		return receiveBuilder()
				.match(CreateStock.class, command -> {
					log.info("New Stock Created");
					persist(new StockCreated(command.getId(), command.getName()), this::applyEvent);
				})
				.match(AddStock.class, command -> {
					log.info("Added " + command.getAmount());
					persist(new StockAdded(command.getAmount()), this::applyEvent);
				})
				.match(WithdrawStock.class, command -> {
					log.info("WithDraw " + command.getAmount());
					persist(new StockWithdrawed(command.getAmount()), this::applyEvent);
				})
				.match(TakeSnapshot.class, event -> {
					log.info("TakeSnapshot ");
					saveSnapshot(state.copy());
				})
				.matchEquals("print", command -> {
					System.out.println(toString());
				}).build();
	}

	private void applyEvent(Object event) {

		if (event instanceof StockCreated) {
			this.state.update(event);
			this.id = ((StockCreated) event).getId();
			this.name = ((StockCreated) event).getName();
			this.amount = BigDecimal.ZERO;
		}
		if (event instanceof StockAdded) {
			this.state.update(event);
			this.amount = amount.add(((StockAdded) event).getAmount());
		}
		if (event instanceof StockWithdrawed) {
			this.state.update(event);
			this.amount = amount.subtract(((StockWithdrawed) event).getAmount());
		}
		getContext().getSystem().eventStream().publish(event);
	}
}
