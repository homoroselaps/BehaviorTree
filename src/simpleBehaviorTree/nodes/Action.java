package simpleBehaviorTree.nodes;
import java.util.function.Consumer;
import java.util.function.Function;

import simpleBehaviorTree.Node;
import simpleBehaviorTree.NodeStatus;
import simpleBehaviorTree.Tick;

public class Action<T> extends Node<T> {
	private Function<T,NodeStatus> action;

	public Action(Function<T,NodeStatus> action) {
		super();
		this.action = action;
	}
	public Action(Consumer<T> action) {
		super();
		this.action = (t)->{ action.accept(t); return NodeStatus.Success;};
	}
	
	@Override
	protected NodeStatus onTick(Tick<T> tick) {
		return action.apply(tick.Target);
	}
}
