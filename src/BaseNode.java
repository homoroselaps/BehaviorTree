import java.util.ArrayList;
import java.util.Arrays;

public class BaseNode {
	
	static class Context extends NodeContext {
		private boolean IsOpen = false;
		public Context(NodeContext context) {
			super(context);
		}
	}
	
	public NodeContext buildContext(NodeContext context) { 
		return new Context(context);
	}
	
	private int ID;
	public int getID() { return ID; }
	
	protected ArrayList<BaseNode> children;
	
	public BaseNode(BaseNode... children) {
		this.children = new ArrayList<>(children.length);
		this.children.addAll(Arrays.asList(children));
	}

	public <T> NodeStatus execute(Tick<T> tick) {
		Context c = (Context)tick.GetContext(this);
		enter(tick, c);
		open(tick, c);
		NodeStatus status = this.tick(tick, c);
		if (!status.equals(NodeStatus.Running)) {
			close(tick, c);
		}
		exit(tick, c);
		return status;
	}

	private <T> void enter(Tick<T> tick, Context c) {
		tick.EnterNode(this);
		onEnter(tick,c);
	}

	private <T> void open(Tick<T> tick, Context c) {
		if (!c.IsOpen) {
			tick.OpenNode(this);
			c.IsOpen = true;
			onOpen(tick,c);	
		}		
	}
	
	private <T> NodeStatus tick(Tick<T> tick, Context c) {
		tick.TickNode(this);
		return onTick(tick,c);
	}

	public <T> void close(Tick<T> tick, Context c) {
		if (c.IsOpen) {
			tick.CloseNode(this);
			c.IsOpen = false;
			onClose(tick,c);
		}
	}

	private <T> void exit(Tick<T> tick, Context context) {
		tick.ExitNode(this);
		onExit(tick,context);
	}
	
	protected <T> void onEnter(Tick<T> tick, NodeContext context) { }
	protected <T> void onOpen(Tick<T> tick, NodeContext context) { }
	protected <T> NodeStatus onTick(Tick<T> tick, NodeContext context) { 
		return NodeStatus.Success;
	}
	protected <T> void onClose(Tick<T> tick, NodeContext context) { }
	protected <T> void onExit(Tick<T> tick, NodeContext context) { }

	int initiate(int id) {
		this.ID = id;
		for (BaseNode child : children) {
			id++;
			child.initiate(id);
		}
		return id;
	}
}
