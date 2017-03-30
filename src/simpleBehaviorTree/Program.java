package simpleBehaviorTree;
import java.util.Scanner;

import simpleBehaviorTree.nodes.Action;
import simpleBehaviorTree.nodes.Condition;
import simpleBehaviorTree.nodes.Guard;
import simpleBehaviorTree.nodes.MemSelector;
import simpleBehaviorTree.nodes.MemSequence;
import simpleBehaviorTree.nodes.Selector;
import simpleBehaviorTree.nodes.Sequence;

class DebugNode<T> extends Node<T> {
	@Override
	protected void onEnter(Tick<T> tick) { 
		System.out.println(this.getID() + ": onEnter");
	}
	@Override
	protected void onOpen(Tick<T> tick) {
		System.out.println(this.getID() + ": onOpen"); 
	}
	@Override
	protected NodeStatus onTick(Tick<T> tick) {
		System.out.println(this.getID() + ": onTick"); 
		return super.onTick(tick);
	}
	@Override
	protected void onClose(Tick<T> tick) {
		System.out.println(this.getID() + ": onClose"); 
	}
	@Override
	protected void onExit(Tick<T> tick) {
		System.out.println(this.getID() + ": onExit"); 
	}
}

class CounterNode<T> extends DebugNode<T> {
	private int count = 0;
	private int currentCount = 0;
	
	public CounterNode(int count) {
		this.count = count;
	}

	@Override
	protected void onOpen(Tick<T> tick) {
		super.onOpen(tick);
		currentCount = count;
	}
	@Override
	protected NodeStatus onTick(Tick<T> tick) {
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

public class Program {
	static Action<Person> setName(String question) {
		return new Action<Person>((target)->{
			System.out.println(question);
			target.askNameCount++;
			String input = target.scan.next();
			if (input.length() >= 3) {
				target.name = input;
				return NodeStatus.Success; 
			}
			return NodeStatus.Failure;
		});
	}
	
	static Action<Person> setAge() {
		return new Action<Person>((target)->{
			System.out.println("How old are you? [int]:");
			try {
				String input = target.scan.next();
				target.age = Integer.parseInt(input);
				return NodeStatus.Success;
			} catch(Exception e) {
				return NodeStatus.Failure;
			}
		});
	}
	static Action<Person> setFunny() {
		return new Action<Person>((target)->{
			System.out.println("Are you funny? [y,n]:");
			String input = target.scan.next().toLowerCase();
			if (input.equals("y") || input.equals("n")) {
				target.funny = new Boolean(input.equals("y"));
				return NodeStatus.Success; 
			}
			return NodeStatus.Failure;
		});
	}
	
	static boolean isNameSet(Person target) {
		return target.name != null && target.name != "";
	}
	static boolean isAgeSet(Person target) {
			return target.age >= 1;
	}

	static boolean isFunnySet(Person target) {
			return target.funny != null;
	}
	
	static void printPerson(Person target) {
			System.out.println("Name: " + target.name + ", Age: " + target.age + ", Funny: " + target.funny);
	}
	
	public static void main(String[] args) {
		System.out.println("Welome to the SimpleBehaviorTree Testing Environment");
		System.out.println("Write 'exit' to end the program");
		
		Root<Person> root = new Root<Person>(new MemSelector<Person>(
			new Sequence<>(
				new Condition<>(Program::isNameSet),
				new Condition<>(Program::isAgeSet),
				new Condition<>(Program::isFunnySet),
				new Action<>(Program::printPerson)
			),
			new Guard<>(Program::isNameSet, false, setName("What is your name?")),
			new Guard<>(Program::isAgeSet, false,  setAge()),
			new Guard<>(Program::isFunnySet, false, setFunny())
		)).init();
		
		Root<Person> test = new Root<Person>(new MemSequence<Person>(
			new Selector<Person>(
				new Condition<>(Program::isNameSet),
				new Selector<>(
					new Guard<>((t)->{return t.askNameCount < 1;}, true, setName("What is your name?")),
					new Guard<>((t)->{return t.askNameCount < 2;}, true, setName("Please tell me your name:")),
					setName("Don't make me keep begging! What is your name?")
				)
			),
			new Guard<Person>(Program::isAgeSet, false, setAge()),
			new Guard<Person>(Program::isFunnySet, false, setFunny()),
			new Action<>(Program::printPerson)
		)).init();
		Person p = new Person();
		Scanner scanner = new Scanner(System.in);
		p.scan = scanner;
		Tick<Person> tick = new Tick<Person>(p, root);
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
