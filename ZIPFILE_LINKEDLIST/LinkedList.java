import java.util.Iterator;
public class LinkedList<E> {

    public class LLIterator<G> implements Iterator<G> {

        private LinkedList<G>.Node<G> current;
        protected LinkedList<G> ll;

        public LLIterator(LinkedList<G> ll) {
            current = null;
            this.ll = ll;
        }

        public boolean hasNext() {
            if (current == null) {
                return !ll.isEmpty();
            }
            return current.next != null;
        }

        public G next() {
            if (current == null) {
                current = ll.head;
            } else {
                current = current.next;
            }
            return current.data;
        }

        public void remove() {
        }
    }

    private class Node<T> {

        T data;
        Node<T> next;

        public Node(T d) {
            data = d;
        }

        public Node(T d, Node<T> n) {
            this(d);
            next = n;
        }

        public void setNext(Node<T> value) {
            next = value;
        }

        public String toString() {
            return "" + data.toString();
        }
    }

    private Node<E> head;
    private Node<E> tail;
    private int length;

    public LinkedList() {
        head = tail = null;
        length = 0;
    }

    public LinkedList(E d) {
        head = tail = new Node<E>(d);
        length = 1;
    }

    public void prepend(E d) {
        head = new Node<E>(d, head);
        if (tail == null) {
            tail = head;
        }
        length++;
    }

    public void append(E d) {
        if (isEmpty()) {
            head = tail = new Node<E>(d);
        } else {
            tail = tail.next = new Node<E>(d);
        }
        length++;
    }

    public int length() {
        return length;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public String toString() {
        String retVal = "LinkedList with elements ";
        /*    Node temp = head;
         while (temp != null) {
         retVal += temp.toString() + " ";
         temp = temp.next;
         }*/

        for (Node<E> temp = head; temp != null; temp = temp.next) {
            retVal += temp.toString() + " ";
        }

        return retVal;
    }

    //Implement this
    public void insert(E data, int index) {
        //create the node that contains the new data
        Node<E> temp = new Node<E>(data);
        //start with the head
        Node<E> current = head;
        Node<E> temp2 = null;
        if (index > length() || index < 0) {
            throw new LinkedListException("Index out of bounds: " + index);
        } else {
            //iterate
            for (int i = 0; i < index && current.next != null; i++) {
                //save the current node, which would be the node before the new node
                temp2 = current;
                //current now will be the node after the new node
                current = current.next;
            }
            // connect the saved current node with the new node. 
            temp2.setNext(temp);
            // conect the new node with the node after. 
            temp.setNext(current);

            length++;
        }
    }

    public boolean remove(E data) {

        Node<E> temp = head;
        Node<E> previous = null;
        boolean removed = false;
        while (temp != null) {
            if (temp.data.equals(data)) {
                if (temp.equals(head)) {
                    head = head.next;
                } else {
                    previous.next = temp.next;
                }
                removed = true;
            } else {
                previous = temp;
            }
            temp = temp.next;
        }

        return removed;
    }

    public boolean exists(E data) {
        Node<E> current = head;
        while (current != null) {
            if (current.data.equals(data)) {
                return true;
            }

            current = current.next;
        }
        return false;
    }

    public E get(int index) {
        if (index < 0 || index >= length) {
            return null;
        }else if(head == null) {
            return null;
        }else{
        Node<E> current = head;
        for (int i = 0; i < index; i++) {
            if (current.next == null) {
                return null;
                
            }
            current = current.next;
        }
        return current.data;
        }
    }
//        public E get(int index) {
//        if (index < 0 || index >= length) {
//            return null;
//        } else {
//        Node<E> current = head;
//        for (int i = 0; i < index; i++) {
//           
//            current = current.next;
//        }
//        return current.data;
//    }
//        }

    public LinkedList<E> shallowCopy() {
        LinkedList<E> copy = new LinkedList<E>();
        Node<E> current = head;
        for (int i = 0; i < length() && current!= null; i++)   {              
            copy.append(current.data);
           
            current = current.next;
        }
        return copy;
    }

}