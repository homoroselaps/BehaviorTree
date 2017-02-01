
public final class Succeeder extends BaseNode {
	@Override
	protected <T> NodeStatus onTick(Tick<T> tick) {
		return NodeStatus.Success;
	}
}
