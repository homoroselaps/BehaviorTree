package simpleBehaviorTree.nodes;
import simpleBehaviorTree.Tick;

public final class Succeeder extends Node {
	@Override
	protected <T> NodeStatus onTick(Tick<T> tick) {
		return NodeStatus.Success;
	}
}
