import java.util.*;

public class DistinctElementsInWindow {
    public static int[] countDistinctElements(int[] arr, int k) {
        if (arr == null || k == 0 || k > arr.length) {
            return new int[0];
        }

        Map<Integer, Integer> freqMap = new HashMap<>();
        int[] result = new int[arr.length - k + 1];

        // Initialize the first window
        for (int i = 0; i < k; i++) {
            freqMap.put(arr[i], freqMap.getOrDefault(arr[i], 0) + 1);
        }
        result[0] = freqMap.size();

        // Slide the window
        for (int i = k; i < arr.length; i++) {
            // Add the new element (arr[i]) to the window
            freqMap.put(arr[i], freqMap.getOrDefault(arr[i], 0) + 1);

            // Remove the element that is sliding out of the window (arr[i - k])
            int outElement = arr[i - k];
            if (freqMap.get(outElement) == 1) {
                freqMap.remove(outElement);
            } else {
                freqMap.put(outElement, freqMap.get(outElement) - 1);
            }

            // Store the count of distinct elements in the current window
            result[i - k + 1] = freqMap.size();
        }

        return result;
    }

    public static void main(String[] args) {
        int[] arr = {1, 2, 4, 4,5,5,6,4};
        //i=4                   i
        //map         [(4,1),(5,1)]
        // i - k = 2
        // arr[i - k]/ outElement = 4
        // freqMap.get(outElement) = 2
         // i - k +1 = 3
        //result [2, 2, 1, 2]
        int k = 2;
        int[] result = countDistinctElements(arr, k);
        System.out.println(Arrays.toString(result));  // Output: [2, 2, 1]
    }
}
