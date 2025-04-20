import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Consumer process reading data from the memory-mapped file
 */
public class Consumer {
    public static void main(String args[]) throws IOException, InterruptedException {
        RandomAccessFile rd = new RandomAccessFile("C:/Users/omgha/OneDrive/Desktop/2024/Distributed Computing/mapped.txt", "r");
        FileChannel fc = rd.getChannel();
        MappedByteBuffer mem = fc.map(FileChannel.MapMode.READ_ONLY, 0, 1000);

        // Assuming that the producer has already written the data
        for (int i = 0; i < 9; i++) {
            byte value = mem.get();
            System.out.println("Process 2: " + value);
        }

        // Close resources
        fc.close();
        rd.close();
    }
}