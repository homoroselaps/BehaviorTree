package simpleBehaviorTree;

public class Composite<T> extends Node<T> {
	@SafeVarargs
	public Composite(Node<T>... children) {
		super(children);
	}

}
