public class Inverter extends BaseNode {
	protected BaseNode child;

	public Inverter(BaseNode child) {
		super();
		this.child = child;
	}
	
	@Override
	protected <T> NodeStatus onTick(Tick<T> tick) {
		NodeStatus result = child.execute(tick);
		if (result.equals(NodeStatus.Success)) {
			return NodeStatus.Failure;
		} else if (result.equals(NodeStatus.Failure)) {
			return NodeStatus.Success; 
		} else {
			return result;
		}
	}
}
