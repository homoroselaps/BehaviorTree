
public class Sequence extends Composite {
	static class Context extends Composite.Context {
	public Context(NodeContext context) {
			super(context);
		}	
	}

	public Sequence(BaseNode... children) {
		super(children);
	}
	
	@Override
	protected <T> NodeStatus onTick(Tick<T> tick, NodeContext context) { 
		for (BaseNode node : children) {
			NodeStatus status = node.execute(tick);
			if (status != NodeStatus.Success) 
				return status;
		}
		return NodeStatus.Success;
	}
}
