import java.util.HashSet;
import java.util.Set;

class ArraySumSwap {
    
    public static void main(String[] args) {
        
        int[] array1 = {4, 1, 2, 1, 1, 2};
        int[] array2 = {3, 6, 3, 3};

        int[] result = findSwapValues(array1, array2);

        if (result != null) {
            System.out.println("Numbers to be swapped are: " + result[0] + " from array1 and " + result[1] + " from array2");
        } else {
            System.out.println("No valid swap found that makes the sums equal.");
        }
    }
    
     public static int[] findSwapValues(int[] array1, int[] array2) {
        int sum1 = 0;
        int sum2 = 0;

        // Calculate the sum of both arrays
        for (int num : array1) {
            sum1 += num;
        }
        for (int num : array2) {
            sum2 += num;
        }

        // Find the difference between the sums
        int diff = sum1 - sum2;

        // If the difference is not even, there is no solution
        if (diff % 2 != 0) {
            return null;
        }

        // Find the target difference for the swap
        int target = diff / 2;

        // Use a set to store elements of the first array
        Set<Integer> set = new HashSet<>();
        for (int num : array1) {
            set.add(num);
        }

        // Look for a pair of values that match the target difference
        for (int num : array2) {
            if (set.contains(num + target)) {
                return new int[]{num + target, num};
            }
        }

        // No valid pair found
        return null;
    }
}
