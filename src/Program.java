import java.util.Scanner;

class DebugNode extends Action {
	@Override
	protected <T> void onEnter(Tick<T> tick, NodeContext context) { 
		System.out.println(this.getID() + ": onEnter");
	}
	@Override
	protected <T> void onOpen(Tick<T> tick, NodeContext context) {
		System.out.println(this.getID() + ": onOpen"); 
	}
	@Override
	protected <T> NodeStatus onTick(Tick<T> tick, NodeContext context) {
		System.out.println(this.getID() + ": onTick"); 
		return super.onTick(tick, context);
	}
	@Override
	protected <T> void onClose(Tick<T> tick, NodeContext context) {
		System.out.println(this.getID() + ": onClose"); 
	}
	@Override
	protected <T> void onExit(Tick<T> tick, NodeContext context) {
		System.out.println(this.getID() + ": onExit"); 
	}
}

class CounterNode extends DebugNode {
	static class Context extends DebugNode.Context {
		private int count = 0;
		private int currentCount;
		public Context(int count, NodeContext context) {
			super(context);
			this.count = count;
		}	
	}
	@Override
	public NodeContext buildContext(NodeContext context) { 
		return new Context(0, context);
	}
	@Override
	protected <T> void onOpen(Tick<T> tick, NodeContext context) {
		super.onOpen(tick, context);
		Context c = (Context)context;
		c.currentCount = c.count;
	}
	@Override
	protected <T> NodeStatus onTick(Tick<T> tick, NodeContext context) {
		super.onTick(tick, context);
		Context c = (Context)context; 
		c.currentCount--;
		return c.currentCount <= 0 ? NodeStatus.Success : NodeStatus.Running;
	}
}

class DummyNode extends CounterNode {
	static class Context extends CounterNode.Context {

		public Context(int count, NodeContext context) {
			super(count, context);
		}
	}
	@Override
	public NodeContext buildContext(NodeContext context) { 
		return new Context(2, context);
	}
}

public class Program {
	public static void main(String[] args) {
		System.out.println("Welome to the SimpleBehaviorTree Testing Environment");
		System.out.println("Write 'exit' to end the program");
		System.out.println("Write '' to continue");
		
		Root root = new Root(new MemSequence(
				new DummyNode(),
				new DebugNode()
		));
		int maxID = root.initiate();
		ContextStorage storage = new ContextStorage(maxID+1);
		Tick<Object> tick = new Tick<Object>(null, root, storage);
		
		Scanner scanner = new Scanner(System.in);
		while (true) {
			String input = scanner.nextLine().toLowerCase();
			if (input.equals("exit"))
				break;
			tick.Tick();
		}
		scanner.close();
	}
}
