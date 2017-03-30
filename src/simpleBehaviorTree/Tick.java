package simpleBehaviorTree;
import java.util.LinkedList;
import java.util.Stack;

public class Tick<T> {
	public Root<T> Root;
	public T Target;
	private Stack<Node<T>> openNodes = new Stack<>();
	private boolean blockOpenNodes = false;
	
	public Tick(T target, Root<T> root) {
		this.Root = root;
		this.Target = target; 
	}

	
	public NodeStatus Tick() {
		LinkedList<Node<T>> lastOpenNodes = new LinkedList<>(openNodes);
		openNodes.clear();
		NodeStatus state = Root.execute(this);
		for(Node<T> node : openNodes) {
			if (lastOpenNodes.size() <= 0) break;
			if (node == lastOpenNodes.peek())
				lastOpenNodes.removeFirst();
			else
				break;
		}
		blockOpenNodes = true;
		for(Node<T> node : lastOpenNodes) {
			node.close(this);
		}
		blockOpenNodes = false;
		return state;
	}
	
	public void EnterNode(Node<T> node) {
		if (!blockOpenNodes)
			openNodes.push(node);
	}
	
	public void OpenNode(Node<T> node) {
		
	}
	
	public void TickNode(Node<T> node) {
		
	}
	
	public void CloseNode(Node<T> node) {
		if (!blockOpenNodes)
			openNodes.pop();
	}
	
	public void ExitNode(Node<T> node) {
		
	}
	

}
