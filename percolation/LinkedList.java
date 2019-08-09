public class LinkedList {

    private Node first = null;

    private class Node {
        String item;
        Node next;
    }

    private boolean isEmpty() {
        return first == null;
    }

    public void push(String item) {
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
    }

    public String pop() {
        String toReturn = first.item;
        first = first.next;
        return toReturn;
    }

    public static void main(String[] args) {
        LinkedList list = new LinkedList();

        list.push("hello");
        list.push("world");

        System.out.println(list.pop());
        System.out.println(list.pop());
        System.out.println(list.isEmpty());
    }

}
