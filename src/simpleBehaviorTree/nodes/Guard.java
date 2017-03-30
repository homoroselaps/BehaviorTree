package simpleBehaviorTree.nodes;
import java.util.function.Function;

import simpleBehaviorTree.Node;
import simpleBehaviorTree.NodeStatus;
import simpleBehaviorTree.Tick;

public class Guard<T> extends Node<T> {
	protected Function<T,Boolean> condition;
	protected Node<T> child;
	protected boolean value;

	public Guard(Function<T,Boolean> condition, Node<T> child) {
		super(child);
		this.condition = condition;
		this.child = child;
		value = true;
	}
	
	public Guard(Function<T,Boolean> condition, boolean shouldBe, Node<T> child) {
		super(child);
		this.condition = condition;
		this.child = child;
		value = shouldBe;
	}
	
	@Override
	protected NodeStatus onTick(Tick<T> tick) {
		boolean result = condition.apply(tick.Target);
		if (result == value) 
			return child.execute(tick);
		else 
			return NodeStatus.Failure;
	}
}
