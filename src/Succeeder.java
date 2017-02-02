
public final class Succeeder extends Node {
	@Override
	protected <T> NodeStatus onTick(Tick<T> tick) {
		return NodeStatus.Success;
	}
}
