import java.util.Arrays;

public class SmallestDifference {

    public static int[] findSmallestDifference(int[] arr1, int[] arr2) {
        // Sort both arrays
        Arrays.sort(arr1);
        Arrays.sort(arr2);
        
        // Initialize pointers for both arrays
        int i = 0, j = 0;
        
        // Initialize variables to keep track of the smallest difference and the corresponding elements
        int smallestDiff = Integer.MAX_VALUE;
        int[] pair = new int[2];
        
        // Iterate through both arrays
        while (i < arr1.length && j < arr2.length) {
            int diff = Math.abs(arr1[i] - arr2[j]);
            
            // Update the smallest difference and the pair if the current difference is smaller
            if (diff < smallestDiff) {
                smallestDiff = diff;
                pair[0] = arr1[i];
                pair[1] = arr2[j];
            }
            
            // Move the pointer that points to the smaller element
            if (arr1[i] < arr2[j]) {
                i++;
            } else {
                j++;
            }
        }
        
        return pair;
    }
    
    public static void main(String[] args) {
        int[] arr1 = {1, 3, 15, 11, 2};
        int[] arr2 = {23, 127, 235, 19, 8};
        
        int[] result = findSmallestDifference(arr1, arr2);
        System.out.println("Pair with the smallest difference: (" + result[0] + ", " + result[1] + ")");
        System.out.println("Smallest difference: " + Math.abs(result[0] - result[1]));
    }
}
