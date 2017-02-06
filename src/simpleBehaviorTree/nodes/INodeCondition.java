package simpleBehaviorTree.nodes;

public interface INodeCondition<T> {
	boolean run(T target);
}
