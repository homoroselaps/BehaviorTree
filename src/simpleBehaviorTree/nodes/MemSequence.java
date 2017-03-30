package simpleBehaviorTree.nodes;
import simpleBehaviorTree.Composite;
import simpleBehaviorTree.Node;
import simpleBehaviorTree.NodeStatus;
import simpleBehaviorTree.Tick;

public class MemSequence<T> extends Composite<T> {
	private int index = 0;

	@SafeVarargs
	public MemSequence(Node<T>... children) {
		super(children);
	}
	
	@Override
	protected NodeStatus onTick(Tick<T> tick) { 
		for (; index < children.size(); index++) {
			NodeStatus status = children.get(index).execute(tick);
			if (!status.equals(NodeStatus.Success)) 
				return status;
		}
		return NodeStatus.Success;
	}
	
	@Override
	protected void onOpen(Tick<T> tick) {
		index = 0;
	}
}
