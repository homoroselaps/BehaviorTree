public class Decorator extends Node {

	public Node child;
	public Decorator(Node child) {
		super(child);
		this.child = child;
	}
}
