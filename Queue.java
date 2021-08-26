/**
 * Problem solved: Implementing a Queue with Generics
 * @author Joe Cassidy
 * 2/23/15
 */
public class Queue<E> {
	E[] queue;
	private int queueLength;
	private int head;
	private int tail;
	
	@SuppressWarnings("unchecked")
	public Queue(int length){
		head = 0;
		tail = 0;
		if(length > 0){
			queueLength = length;
		}
		else{
			queueLength = 10;
		}
		queue = (E[]) new Object[queueLength];
	}
	
	// creates a Queue of 10 Objects
	public Queue(){
		this(10);
	}
	@SuppressWarnings("unchecked")
	public void enqueue(E item){
		if(count() == queueLength){
			E[] temp = queue;
			queue = (E[]) new Object[queueLength *2];
			for(int i = 0; i < temp.length; i++){
				queue[i] = temp[(head + i) % queueLength];
			}
			head = 0;
			tail = queueLength;
			queueLength = queueLength * 2;
		}
		else{
			queue[tail] = item;
			tail = (tail+1) % queueLength;
		}
	}
	public E dequeue() throws Exception{
		if(count() == 0){
			new Exception("The queue is empty.");
			return null;
		}
		else{
			E item = queue[head];
			head = (head+1) % queueLength;
			return item;
		}
	}
	public int count(){
		int length = 0;
		for(int i = head; i != tail; i = (i + 1) % queueLength){
			length++;
		}
		return length;
	}
	public E peek() throws Exception{
		if(head != tail){
			return queue[head];
		}
		else{
			new Exception("The queue is empty");
			return null;
		}
	}
	public String toString(){
		String queueString = "";
		for(int i = head; i != tail; i = (i + 1) % queueLength){
			queueString = queueString + queue[i] + " , ";
		}
		queueString = queueString + queue[tail];
		return(queueString);
	}

}
