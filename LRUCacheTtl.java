import java.util.HashMap;

class LRUCacheTtl<K, V> {

    private final int capacity;
    private final long ttl; // Time-to-live in milliseconds
    private final HashMap<K, Node<K, V>> map;
    private final DoublyLinkedList<K, V> dll;

    public LRUCacheTtl(int capacity, long ttl) {
        this.capacity = capacity;
        this.ttl = ttl;
        this.map = new HashMap<>();
        this.dll = new DoublyLinkedList<>();
    }

    public V get(K key) {
        if (!map.containsKey(key)) {
            return null;
        }
        Node<K, V> node = map.get(key);
        if (isExpired(node)) {
            map.remove(key);
            dll.removeNode(node);
            return null;
        }
        dll.moveToFront(node);
        return node.value;
    }

    public void put(K key, V value) {
        if (map.containsKey(key)) {
            Node<K, V> node = map.get(key);
            node.value = value;
            node.timestamp = System.currentTimeMillis();
            dll.moveToFront(node);
        } else {
            if (map.size() == capacity) {
                removeExpiredNodes();
                if (map.size() == capacity) {
                    Node<K, V> tail = dll.removeTail();
                    map.remove(tail.key);
                }
            }
            Node<K, V> newNode = new Node<>(key, value, System.currentTimeMillis());
            dll.addToFront(newNode);
            map.put(key, newNode);
        }
    }

    private void removeExpiredNodes() {
        Node<K, V> current = dll.tail.prev;
        while (current != dll.head) {
            if (isExpired(current)) {
                Node<K, V> prev = current.prev;
                map.remove(current.key);
                dll.removeNode(current);
                current = prev;
            } else {
                break;
            }
        }
    }

    private boolean isExpired(Node<K, V> node) {
        return (System.currentTimeMillis() - node.timestamp) > ttl;
    }

    private static class Node<K, V> {
        K key;
        V value;
        long timestamp;
        Node<K, V> prev;
        Node<K, V> next;

        public Node(K key, V value, long timestamp) {
            this.key = key;
            this.value = value;
            this.timestamp = timestamp;
        }
    }

    private static class DoublyLinkedList<K, V> {
        private final Node<K, V> head;
        private final Node<K, V> tail;

        public DoublyLinkedList() {
            this.head = new Node<>(null, null, -1);
            this.tail = new Node<>(null, null, -1);
            head.next = tail;
            tail.prev = head;
        }

        public void addToFront(Node<K, V> node) {
            node.next = head.next;
            node.prev = head;
            head.next.prev = node;
            head.next = node;
        }

        public void moveToFront(Node<K, V> node) {
            removeNode(node);
            addToFront(node);
        }

        public Node<K, V> removeTail() {
            Node<K, V> node = tail.prev;
            removeNode(node);
            return node;
        }

        public void removeNode(Node<K, V> node) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        LRUCache<Integer, String> lruCache = new LRUCache<>(3, 3000); // 3 seconds TTL

        lruCache.put(1, "A");
        lruCache.put(2, "B");
        lruCache.put(3, "C");

        System.out.println(lruCache.get(1)); // Should print "A"
        Thread.sleep(4000); // Wait for TTL to expire
        System.out.println(lruCache.get(1)); // Should print null
        lruCache.put(4, "D"); // Evicts key 2 (if it has not expired), or the oldest unexpired one
        System.out.println(lruCache.get(2)); // Should print null or "B" if not expired
        lruCache.put(5, "E"); // Evicts the oldest item
        System.out.println(lruCache.get(3)); // Should print null
        System.out.println(lruCache.get(4)); // Should print "D"
        System.out.println(lruCache.get(5)); // Should print "E"
    }
}
