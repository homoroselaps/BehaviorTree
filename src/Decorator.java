public class Decorator extends BaseNode {

	public BaseNode child;
	public Decorator(BaseNode child) {
		super(child);
		this.child = child;
	}
}
