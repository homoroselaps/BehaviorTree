
public final class Succeeder extends BaseNode {
	@Override
	protected <T> NodeStatus onTick(Tick<T> tick, NodeContext context) {
		return NodeStatus.Success;
	}
}
