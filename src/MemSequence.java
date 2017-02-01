public class MemSequence extends Composite {
	private int index = 0;

	public MemSequence(BaseNode... children) {
		super(children);
	}
	
	@Override
	protected <T> NodeStatus onTick(Tick<T> tick) { 
		for (; index < children.size(); index++) {
			NodeStatus status = children.get(index).execute(tick);
			if (!status.equals(NodeStatus.Success)) 
				return status;
		}
		return NodeStatus.Success;
	}
	
	@Override
	protected <T> void onOpen(Tick<T> tick) {
		index = 0;
	}
}
