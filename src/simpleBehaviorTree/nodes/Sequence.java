package simpleBehaviorTree.nodes;
import simpleBehaviorTree.Composite;
import simpleBehaviorTree.Node;
import simpleBehaviorTree.NodeStatus;
import simpleBehaviorTree.Tick;

public class Sequence<T> extends Composite<T> {

	@SafeVarargs
	public Sequence(Node<T>... children) {
		super(children);
	}
	
	@Override
	protected NodeStatus onTick(Tick<T> tick) { 
		for (Node<T> node : children) {
			NodeStatus status = node.execute(tick);
			if (!status.equals(NodeStatus.Success)) 
				return status;
		}
		return NodeStatus.Success;
	}
}
