import java.util.LinkedList;
import java.util.Stack;

public class Tick<T> {
	public BaseNode Root;
	public T Target;
	private Stack<BaseNode> openNodes = new Stack<>();
	private boolean blockOpenNodes = false;
	
	public Tick(T target, BaseNode root) {
		this.Root = root;
		this.Target = target; 
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
			node.close(this);
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
