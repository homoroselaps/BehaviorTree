
public class Root extends BaseNode {
	protected BaseNode child;
	public Root(BaseNode child) {
		super(child);
		this.child = child;
	}
	@Override
	protected <T> NodeStatus onTick(Tick<T> tick, NodeContext context) { 
		return child.execute(tick); 
	}
	public int initiate() {
		return child.initiate(1);
	}
}
