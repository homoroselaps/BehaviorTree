public class MemSequence extends Composite {
	static class Context extends Composite.Context {
		private int index = 0;
		public Context(NodeContext context) {
			super(context);
		}	
	}
	
	public NodeContext buildContext(NodeContext context) { 
		return new Context(context);
	}

	public MemSequence(BaseNode... children) {
		super(children);
	}
	
	@Override
	protected <T> NodeStatus onTick(Tick<T> tick, NodeContext context) {
		Context c = (Context)context; 
		for (; c.index < children.size(); c.index++) {
			NodeStatus status = children.get(c.index).execute(tick);
			if (status != NodeStatus.Success) 
				return status;
		}
		return NodeStatus.Success;
	}
	
	@Override
	protected <T> void onOpen(Tick<T> tick, NodeContext context) {
		Context c = (Context)tick.GetContext(this);
		c.index = 0;
	}
	
}
