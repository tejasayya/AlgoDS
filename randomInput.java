package sorting;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Random;

public class randomInput extends JPanel {

    // Data for sorting algorithms and their execution times
    private String[] algorithms = {"Insertion Sort", "Merge Sort", "Heap Sort", "In-Place Quick Sort", "Modified Quick Sort"};
    private long[][] executionTimes;
    private int[] arraySizes;  // Store array sizes

    public randomInput(long[][] executionTimes, int[] arraySizes) {
        this.executionTimes = executionTimes;
        this.arraySizes = arraySizes;
        setBackground(Color.LIGHT_GRAY);  // Set background color for the chart
    }

    // Overriding paintComponent to draw the line chart with axis labels and values at intersections
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Set chart dimensions
        int chartWidth = getWidth();
        int chartHeight = getHeight();
        int padding = 60;
        int labelPadding = 50;
        int pointWidth = 8;
        int numYDivisions = 10; // Number of Y axis divisions

        // Get max array size and max execution time for scaling
        int maxArraySize = Arrays.stream(arraySizes).max().orElse(1);
        long maxTime = Arrays.stream(executionTimes)
                .flatMapToLong(Arrays::stream)
                .max()
                .orElse(1);

        // Draw white background for chart area
        g2d.setColor(Color.WHITE);
        g2d.fillRect(padding + labelPadding, padding, chartWidth - 2 * padding - labelPadding, chartHeight - 2 * padding - labelPadding);
        g2d.setColor(Color.BLACK);

        // Draw y-axis lines and labels (execution times)
        for (int i = 0; i <= numYDivisions; i++) {
            int y = chartHeight - ((i * (chartHeight - padding * 2 - labelPadding)) / numYDivisions + padding + labelPadding);
            g2d.setColor(Color.GRAY);
            g2d.drawLine(padding + labelPadding, y, chartWidth - padding, y);  // Draw grid lines
            g2d.setColor(Color.BLACK);
            String yLabel = String.format("%.1f", ((maxTime * i) / (double) numYDivisions) / 1_000_000) + " ms";  // Convert to milliseconds
            g2d.drawString(yLabel, padding, y + (g2d.getFontMetrics().getHeight() / 2) - 3);  // Draw Y-axis labels
        }

        // Draw x-axis lines and labels (array sizes)
        for (int i = 0; i < arraySizes.length; i++) {
            int x = i * (chartWidth - padding * 2 - labelPadding) / (arraySizes.length - 1) + padding + labelPadding;
            g2d.setColor(Color.GRAY);
            g2d.drawLine(x, chartHeight - padding - labelPadding, x, padding);  // Draw grid lines
            g2d.setColor(Color.BLACK);
            String xLabel = arraySizes[i] + "";
            g2d.drawString(xLabel, x - g2d.getFontMetrics().stringWidth(xLabel) / 2, chartHeight - padding - labelPadding + g2d.getFontMetrics().getHeight() + 3);  // Draw X-axis labels
        }

        // Draw the title and axis labels
        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        g2d.drawString("Random INPUT -- Sorting Algorithms Execution Time", (chartWidth - padding * 8 - labelPadding) / 2 + padding, padding / 2);
        g2d.setFont(new Font("Arial", Font.PLAIN, 14));
        g2d.drawString("Array Size", (chartWidth - padding * 2 - labelPadding) / 2 + padding, chartHeight - padding);
        g2d.drawString("Execution Time (ms)", padding / 2, padding);

        // Draw lines for each algorithm with legend
        Color[] colors = {Color.BLUE, Color.RED, Color.GREEN, Color.MAGENTA, Color.ORANGE};

        for (int alg = 0; alg < executionTimes.length; alg++) {
            g2d.setColor(colors[alg]);
            int prevX = -1, prevY = -1;
            for (int i = 0; i < arraySizes.length; i++) {
                int x = i * (chartWidth - padding * 2 - labelPadding) / (arraySizes.length - 1) + padding + labelPadding;
                int y = (int) ((maxTime - executionTimes[alg][i]) * (chartHeight - padding * 2 - labelPadding) / maxTime + padding);

                // Draw line between previous and current point
                if (prevX != -1 && prevY != -1) {
                    g2d.drawLine(prevX, prevY, x, y);
                }

                // Draw point
                g2d.fillOval(x - pointWidth / 2, y - pointWidth / 2, pointWidth, pointWidth);

                // Draw value at the point (in milliseconds)
                String timeLabel = String.format("%.2f ms", executionTimes[alg][i] / 1_000_000.0);  // Convert to milliseconds
                g2d.drawString(timeLabel, x - pointWidth / 2, y - pointWidth / 2 - 5);  // Display above the point

                // Store previous points
                prevX = x;
                prevY = y;
            }

            // Draw legend for each algorithm with bold font
            g2d.setFont(new Font("Arial", Font.BOLD, 12));  // Set font to bold for algorithm names
            g2d.setColor(colors[alg]);  // Set color for algorithm name
            g2d.drawString(algorithms[alg], chartWidth - padding - 150, padding + alg * 20);
            g2d.setFont(new Font("Arial", Font.PLAIN, 12));  // Reset font to normal
        }
    }

    // Main method to test sorting algorithms and display the chart
    public static void main(String[] args) {
        int[] arraySizes = {1000, 2000, 3000, 4000, 5000, 10000, 20000, 40000, 50000, 60000, 80000, 90000, 100000};

        long[][] executionTimes = new long[5][arraySizes.length];  // 5 algorithms, multiple array sizes

        // Test each sorting algorithm on arrays of varying sizes
        for (int i = 0; i < arraySizes.length; i++) {
            int size = arraySizes[i];
            int[] arr = generateRandomArray(size);

            // Make copies of the array for each sorting algorithm
            int array1[] = Arrays.copyOf(arr, arr.length);
            int array2[] = Arrays.copyOf(arr, arr.length);
            int array3[] = Arrays.copyOf(arr, arr.length);
            int array4[] = Arrays.copyOf(arr, arr.length);
            int array5[] = Arrays.copyOf(arr, arr.length);

            // Measure execution time for each sorting algorithm
            executionTimes[0][i] = measureExecutionTime(() -> SortingTechniques.insertionSort(array1));
            executionTimes[1][i] = measureExecutionTime(() -> SortingTechniques.mergeSort(array2, 0, array2.length - 1));
            executionTimes[2][i] = measureExecutionTime(() -> SortingTechniques.heapSort(array3));
            executionTimes[3][i] = measureExecutionTime(() -> SortingTechniques.inPlaceQuickSort(0, array4.length - 1, array4));
            executionTimes[4][i] = measureExecutionTime(() -> SortingTechniques.modifiedQuickSort(array5, 0, array5.length - 1));
        }
        System.out.println("---------For Random Input----------");
        System.out.println("Input Size (n) | Insertion Sort | MergeSort | HeapSort | InPlace Quick Sort | Modified Quick Sort");
        
        for(int x=0; x<executionTimes[0].length;x++){
            System.out.print(arraySizes[x]+":  ");
            for(int y=0; y<executionTimes.length;y++){
                System.out.print((executionTimes[y][x]/1_000_000.0)+" ");
            }
            System.out.println();
        }
        System.out.println();

        // Display the line chart in a window
        JFrame frame = new JFrame("Sorting Algorithms Execution Time");
        randomInput chart = new randomInput(executionTimes, arraySizes);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(chart);
        frame.setSize(800, 600);
        frame.setVisible(true);
    }

    // Generate a random array of size n
    public static int[] generateRandomArray(int n) {
        int[] arr = new int[n];
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            arr[i] = random.nextInt(n);
        }
        return arr;
    }

    // Method to measure execution time of any sorting algorithm
    public static long measureExecutionTime1(Runnable sortAlgorithm) {
        long startTime = System.nanoTime();
        sortAlgorithm.run();
        long endTime = System.nanoTime();
        return endTime - startTime;
    }

    public static long measureExecutionTime(Runnable sortAlgorithm){
        long totalTime = 0;
        for (int i = 0; i < 5; i++) {
            long startTime = System.nanoTime();
            sortAlgorithm.run();
            long endTime = System.nanoTime();
            totalTime += endTime - startTime;
        }
        return totalTime / 5;
    }
}