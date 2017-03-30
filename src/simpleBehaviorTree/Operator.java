package simpleBehaviorTree;

public class Operator<T> extends Node<T> {
	public Node<T> left,right;

	public Operator(Node<T> left, Node<T> right) {
		super(left, right);
	}

}
