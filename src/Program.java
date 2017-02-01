import java.util.Scanner;

class DebugNode extends BaseNode {
	@Override
	protected <T> void onEnter(Tick<T> tick) { 
		System.out.println(this.getID() + ": onEnter");
	}
	@Override
	protected <T> void onOpen(Tick<T> tick) {
		System.out.println(this.getID() + ": onOpen"); 
	}
	@Override
	protected <T> NodeStatus onTick(Tick<T> tick) {
		System.out.println(this.getID() + ": onTick"); 
		return super.onTick(tick);
	}
	@Override
	protected <T> void onClose(Tick<T> tick) {
		System.out.println(this.getID() + ": onClose"); 
	}
	@Override
	protected <T> void onExit(Tick<T> tick) {
		System.out.println(this.getID() + ": onExit"); 
	}
}

class CounterNode extends DebugNode {
	private int count = 0;
	private int currentCount = 0;
	
	public CounterNode(int count) {
		this.count = count;
	}

	@Override
	protected <T> void onOpen(Tick<T> tick) {
		super.onOpen(tick);
		currentCount = count;
	}
	@Override
	protected <T> NodeStatus onTick(Tick<T> tick) {
		super.onTick(tick);
		currentCount--;
		return currentCount <= 0 ? NodeStatus.Success : NodeStatus.Running;
	}
}

public class Program {
	public static void main(String[] args) {
		System.out.println("Welome to the SimpleBehaviorTree Testing Environment");
		System.out.println("Write 'exit' to end the program");
		System.out.println("Write '' to continue");
		
		Root root = new Root(new MemSequence(
				new CounterNode(2),
				new DebugNode()
		));
		root.initiate();
		Tick<Object> tick = new Tick<Object>(null, root);
		
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
