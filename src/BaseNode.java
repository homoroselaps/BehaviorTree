import java.util.ArrayList;
import java.util.Arrays;

public class BaseNode {
	private boolean isOpen = false;
	
	private int ID;
	public int getID() { return ID; }
	
	protected ArrayList<BaseNode> children;
	
	public BaseNode(BaseNode... children) {
		this.children = new ArrayList<>(children.length);
		this.children.addAll(Arrays.asList(children));
	}

	public <T> NodeStatus execute(Tick<T> tick) {
		enter(tick);
		open(tick);
		NodeStatus status = this.tick(tick);
		if (!status.equals(NodeStatus.Running)) {
			close(tick);
		}
		exit(tick);
		return status;
	}

	private <T> void enter(Tick<T> tick) {
		tick.EnterNode(this);
		onEnter(tick);
	}

	private <T> void open(Tick<T> tick) {
		if (!isOpen) {
			tick.OpenNode(this);
			isOpen = true;
			onOpen(tick);	
		}		
	}
	
	private <T> NodeStatus tick(Tick<T> tick) {
		tick.TickNode(this);
		return onTick(tick);
	}

	public <T> void close(Tick<T> tick) {
		if (isOpen) {
			tick.CloseNode(this);
			isOpen = false;
			onClose(tick);
		}
	}

	private <T> void exit(Tick<T> tick) {
		tick.ExitNode(this);
		onExit(tick);
	}
	
	protected <T> void onEnter(Tick<T> tick) { }
	protected <T> void onOpen(Tick<T> tick) { }
	protected <T> NodeStatus onTick(Tick<T> tick) { 
		return NodeStatus.Success;
	}
	protected <T> void onClose(Tick<T> tick) { }
	protected <T> void onExit(Tick<T> tick) { }

	int initiate(int id) {
		this.ID = id;
		for (BaseNode child : children) {
			id++;
			child.initiate(id);
		}
		return id;
	}
}
