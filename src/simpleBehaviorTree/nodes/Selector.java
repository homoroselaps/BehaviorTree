package simpleBehaviorTree.nodes;
import simpleBehaviorTree.Composite;
import simpleBehaviorTree.Node;
import simpleBehaviorTree.NodeStatus;
import simpleBehaviorTree.Tick;

public class Selector<T> extends Composite<T> {

	@SafeVarargs
	public Selector(Node<T>... children) {
		super(children);
	}
	
	@Override
	protected NodeStatus onTick(Tick<T> tick) { 
		for (Node<T> node : children) {
			NodeStatus status = node.execute(tick);
			if (!status.equals(NodeStatus.Failure)) 
				return status;
		}
		return NodeStatus.Failure;
	}
}
