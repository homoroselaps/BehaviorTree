import java.util.Scanner;

class DebugNode extends Node {
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

class Person {
	String name;
	int askNameCount;
	
	int age;
	Boolean funny;
	Scanner scan;
}

class isAskNameCountLowerThan implements INodeCondition<Person> {
	private int value;
	public isAskNameCountLowerThan(int value) {
		this.value = value;
	}
	public boolean run(Person target) {
		return target.askNameCount < value;
	}
}

class isNameSet implements INodeCondition<Person> {
	public boolean run(Person target) {
		return target.name != null && target.name != "";
	}
}

class isAgeSet implements INodeCondition<Person> {
	public boolean run(Person target) {
		return target.age >= 1;
	}
}

class isFunnySet implements INodeCondition<Person> {
	public boolean run(Person target) {
		return target.funny != null;
	}
}

class setName implements INodeAction<Person> {
	private String question;
	public setName(String question) {
		this.question = question;
	}
	public NodeStatus run(Person target) {
		System.out.println(question);
		target.askNameCount++;
		String input = target.scan.next().toLowerCase();
		if (input.length() >= 3) {
			target.name = input;
			return NodeStatus.Success; 
		}
		return NodeStatus.Failure;
	}
}

class setAge implements INodeAction<Person> {
	public NodeStatus run(Person target) {
		System.out.println("How old are you? [int]:");
		try {
			String input = target.scan.next().toLowerCase();
			target.age = Integer.parseInt(input);
			return NodeStatus.Success;
		} catch(Exception e) {
			return NodeStatus.Failure;
		}
	}
}

class setFunny implements INodeAction<Person> {
	public NodeStatus run(Person target) {
		System.out.println("Are you funny? [y,n]:");
		String input = target.scan.next().toLowerCase();
		if (input.equals("y") || input.equals("n")) {
			target.funny = new Boolean(input.equals("y"));
			return NodeStatus.Success; 
		}
		return NodeStatus.Failure;
	}
}

class printPerson implements INodeAction<Person> {
	public NodeStatus run(Person target) {
		System.out.println("Name: " + target.name + ", Age: " + target.age + ", Funny: " + target.funny);
		return NodeStatus.Success;
	}
}

public class Program {
	public static void main(String[] args) {
		System.out.println("Welome to the SimpleBehaviorTree Testing Environment");
		System.out.println("Write 'exit' to end the program");
		
		Root root = new Root(new MemSelector(
			new Sequence(
				new Condition(new isNameSet()),
				new Condition(new isAgeSet()),
				new Condition(new isFunnySet()),
				new Action(new printPerson())
			),
			new Guard(new isNameSet(), false, new Action(new setName("What is your name?"))),
			new Guard(new isAgeSet(), false, new Action(new setAge())),
			new Guard(new isFunnySet(), false, new Action(new setFunny()))
		)).init();
		Root test = new Root(new MemSequence(
			new Selector(
				new Condition(new isNameSet()),
				new Selector(
					new Guard(new isAskNameCountLowerThan(1), new Action(new setName("What is your name?"))),
					new Guard(new isAskNameCountLowerThan(2), new Action(new setName("Please tell me your name:"))),
					new Action(new setName("Don't make me keep begging! What is your name?"))
				)
			),
			new Selector(new Condition(new isAgeSet()), new Action(new setAge())),
			new Selector(new Condition(new isFunnySet()), new Action(new setFunny())),
			new Sequence(
				new Condition(new isNameSet()),
				new Condition(new isAgeSet()),
				new Condition(new isFunnySet()),
				new Action(new printPerson())
			)
		)).init();
		Person p = new Person();
		Scanner scanner = new Scanner(System.in);
		p.scan = scanner;
		Tick<Person> tick = new Tick<Person>(p, test);
		System.out.println("Node Count: "+tick.Root.getChildrenCount());
		while (true) {
			System.out.println("Press [enter] to continue");
			String input = scanner.nextLine().toLowerCase();
			if (input.equals("exit"))
				break;
			NodeStatus status = tick.Tick();
			System.out.println(status);
		}
		scanner.close();
	}
}
