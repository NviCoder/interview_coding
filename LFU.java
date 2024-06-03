import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

class LFU {
    int cacheSize;
    Map<Integer, Pair> cache;
    PriorityQueue<Pair> minHeap;

    public LFU(int cacheSize) {
        this.cacheSize = cacheSize;
        this.cache = new HashMap<>();
        this.minHeap = new PriorityQueue<>((a, b) -> a.frequency - b.frequency);
    }

    public void increment(int value) {
        if (cache.containsKey(value)) {
            Pair pair = cache.get(value);
            pair.frequency += 1;
            minHeap.remove(pair);
            minHeap.offer(pair);
        }
    }

    public void insert(int value) {
        if (cache.size() == cacheSize) {
            evictLFU();
        }

        Pair newPair = new Pair(value, 1);
        cache.put(value, newPair);
        minHeap.offer(newPair);
        System.out.println("Cache block " + value + " inserted.");
    }

    public void put(int value) {
        if (!cache.containsKey(value)) {
            insert(value);
        } else {
            increment(value);
        }
    }

    public void get(int value) {
        if (cache.containsKey(value)) {
            increment(value);
        } else {
            System.out.println("Cache miss for value: " + value);
        }
    }

    public void evictLFU() {
        Pair lfuPair = minHeap.poll();
        cache.remove(lfuPair.value);
        System.out.println("Cache block " + lfuPair.value + " removed.");
    }
}

public class Main {
    public static void main(String[] args) {
        LFU lfuCache = new LFU(4);
        lfuCache.put(1);
        lfuCache.put(2);
        lfuCache.get(1);
        lfuCache.put(3);
        lfuCache.get(2);
        lfuCache.put(4);
        lfuCache.put(5);
    }
}

class Pair {
    int value, frequency;

    public Pair(int value, int frequency) {
        this.value = value;
        this.frequency = frequency;
    }
}
