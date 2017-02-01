public class Guard extends BaseNode {
	protected INodeCondition condition;
	protected BaseNode child;

	public Guard(INodeCondition condition, BaseNode child) {
		super();
		this.condition = condition;
		this.child = child;
	}
	
	@Override
	protected <T> NodeStatus onTick(Tick<T> tick) {
		boolean result = condition.run(tick.Target);
		if (result) 
			return child.execute(tick);
		else 
			return NodeStatus.Failure;
	}
}
