
public final class Failer extends Node {
	@Override
	protected <T> NodeStatus onTick(Tick<T> tick) {
		return NodeStatus.Failure;
	}
}
