package simpleBehaviorTree.nodes;

public interface INodeAction<T> {
	NodeStatus run(T target);
}
