package simpleBehaviorTree.nodes;
import simpleBehaviorTree.Node;
import simpleBehaviorTree.NodeStatus;
import simpleBehaviorTree.Tick;

public final class Failer<T> extends Node<T> {
	@Override
	protected NodeStatus onTick(Tick<T> tick) {
		return NodeStatus.Failure;
	}
}
