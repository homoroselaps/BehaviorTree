package simpleBehaviorTree.nodes;
import simpleBehaviorTree.Tick;

public class Sequence extends Composite {

	public Sequence(Node... children) {
		super(children);
	}
	
	@Override
	protected <T> NodeStatus onTick(Tick<T> tick) { 
		for (Node node : children) {
			NodeStatus status = node.execute(tick);
			if (!status.equals(NodeStatus.Success)) 
				return status;
		}
		return NodeStatus.Success;
	}
}
