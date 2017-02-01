
public class Sequence extends Composite {

	public Sequence(BaseNode... children) {
		super(children);
	}
	
	@Override
	protected <T> NodeStatus onTick(Tick<T> tick) { 
		for (BaseNode node : children) {
			NodeStatus status = node.execute(tick);
			if (!status.equals(NodeStatus.Success)) 
				return status;
		}
		return NodeStatus.Success;
	}
}
