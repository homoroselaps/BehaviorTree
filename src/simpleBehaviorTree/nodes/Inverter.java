package simpleBehaviorTree.nodes;
import simpleBehaviorTree.Decorator;
import simpleBehaviorTree.Node;
import simpleBehaviorTree.NodeStatus;
import simpleBehaviorTree.Tick;

public class Inverter<T> extends Decorator<T> {
	
	public Inverter(Node<T> child) {
		super(child);
	}

	@Override
	protected NodeStatus onTick(Tick<T> tick) {
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
