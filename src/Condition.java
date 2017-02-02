public class Condition extends Node {
	private INodeCondition condition;

	public Condition(INodeCondition condition) {
		super();
		this.condition = condition;
	}
	
	@Override
	protected <T> NodeStatus onTick(Tick<T> tick) {
		return condition.run(tick.Target) ? NodeStatus.Success : NodeStatus.Failure;
	}
}
