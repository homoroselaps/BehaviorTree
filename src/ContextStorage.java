import java.util.ArrayList;

public class ContextStorage {
	private ArrayList<NodeContext> storage;
	
	public ContextStorage(int size){
		storage = new ArrayList<>(size);
		for (int i = 0; i < size; i++) {
			storage.add(null);
		}
	}
	
	public void SetContext(BaseNode node, NodeContext context) {
		storage.set(node.getID(), context);
	}

	public NodeContext GetContext(BaseNode node) {
		return storage.get(node.getID());
	}
}
