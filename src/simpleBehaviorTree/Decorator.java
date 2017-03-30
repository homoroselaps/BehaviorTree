package simpleBehaviorTree;

public class Decorator<T> extends Node<T> {
	public Node<T> child;
	public Decorator(Node<T> child) {
		super(child);
		this.child = child;
	}
}
