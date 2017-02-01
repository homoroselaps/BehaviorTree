import java.util.LinkedList;
import java.util.Stack;

public class Tick<T> {
	public BaseNode Root;
	public T Target;
	private Stack<BaseNode> openNodes = new Stack<>();
	private boolean blockOpenNodes = false;
	private ContextStorage storage;
	
	public Tick(T target, BaseNode root, ContextStorage storage) {
		this.storage = storage;
		this.Root = root;
		this.Target = target; 
	}
	
	public NodeContext GetContext(BaseNode node) {
		NodeContext context = storage.GetContext(node);
		if (context == null) {
			context = node.buildContext(new NodeContext(null));
			storage.SetContext(node, context);
		}
		return context;
	}
	
	public NodeStatus Tick() {
		LinkedList<BaseNode> lastOpenNodes = new LinkedList<BaseNode>(openNodes);
		openNodes.clear();
		NodeStatus state = Root.execute(this);
		for(BaseNode node : openNodes) {
			if (lastOpenNodes.size() <= 0) break;
			if (node == lastOpenNodes.peek())
				lastOpenNodes.removeFirst();
			else
				break;
		}
		blockOpenNodes = true;
		for(BaseNode node : lastOpenNodes) {
			node.close(this, (BaseNode.Context)storage.GetContext(node));
		}
		blockOpenNodes = false;
		return state;
	}
	
	public void EnterNode(BaseNode node) {
		if (!blockOpenNodes)
			openNodes.push(node);
	}
	
	public void OpenNode(BaseNode node) {
		
	}
	
	public void TickNode(BaseNode node) {
		
	}
	
	public void CloseNode(BaseNode node) {
		if (!blockOpenNodes)
			openNodes.pop();
	}
	
	public void ExitNode(BaseNode node) {
		
	}
	

}
