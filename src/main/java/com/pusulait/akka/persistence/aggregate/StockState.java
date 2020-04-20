package com.pusulait.akka.persistence.aggregate;

import com.pusulait.akka.persistence.commands.TakeSnapshot;
import com.pusulait.akka.persistence.events.StockAdded;
import com.pusulait.akka.persistence.events.StockCreated;
import com.pusulait.akka.persistence.events.StockWithdrawed;
import lombok.AllArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ToString
@AllArgsConstructor
public class StockState implements Serializable {

    private static final long serialVersionUID = 5097176732735911600L;

    private List<Object> events;

    public StockState() {
        this(new ArrayList<>());
    }

    public StockState copy() {
        return new StockState(new ArrayList<>(events));
    }

    public int size() {
        return events.size();
    }

    public void update(Object event) {
        if (event instanceof StockCreated
                || event instanceof StockAdded
                || event instanceof StockWithdrawed
                || event instanceof TakeSnapshot
        ) {
            events.add(event);
        }
    }

}
