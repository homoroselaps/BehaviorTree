
public final class Failer extends BaseNode {
	@Override
	protected <T> NodeStatus onTick(Tick<T> tick, NodeContext context) {
		return NodeStatus.Failure;
	}
}
