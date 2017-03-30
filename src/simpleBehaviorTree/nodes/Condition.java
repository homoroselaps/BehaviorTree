package simpleBehaviorTree.nodes;
import java.util.function.Function;

import simpleBehaviorTree.Node;
import simpleBehaviorTree.NodeStatus;
import simpleBehaviorTree.Tick;

public class Condition<T> extends Node<T> {
	private Function<T,Boolean> condition;

	public Condition(Function<T,Boolean> condition) {
		super();
		this.condition = condition;
	}
	
	@Override
	protected NodeStatus onTick(Tick<T> tick) {
		return condition.apply(tick.Target) ? NodeStatus.Success : NodeStatus.Failure;
	}
}
