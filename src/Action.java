
public class Action extends BaseNode {
	private INodeAction action;

	public Action(INodeAction action) {
		super();
		this.action = action;
	}
	
	@Override
	protected <T> NodeStatus onTick(Tick<T> tick) {
		return action.run(tick.Target);
	}
}
