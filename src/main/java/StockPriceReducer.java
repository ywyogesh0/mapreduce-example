import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * Reducer class is used for reducer phase during the job run
 * <p>
 * - reducer function will take key/value pairs from multiple map function as input and reduce them to output
 * - keys will be grouped with values
 * - reduce function will be called once per key with its values
 * - there could be 0,1 or more reduce function for map-reduce job
 */
class StockPriceReducer extends Reducer<Text, FloatWritable, Text, FloatWritable> {

    /**
     * @param key     output from the map function in mapper phase
     * @param prices  price from the map function for multiple mappers group together and returns an iterable list
     * @param context map-reduce job context which is used to perform specific activity around the job surrounding (within the job)
     * @throws IOException          input/output operations failure
     * @throws InterruptedException some sort of exception happened which interrupted the job execution
     */
    @Override
    public void reduce(Text key, Iterable<FloatWritable> prices, Context context)
            throws IOException, InterruptedException {
        float maxPrice = 0;
        for (FloatWritable price : prices) {
            if (price.get() >= maxPrice) {
                maxPrice = price.get();
            }
        }
        context.write(key, new FloatWritable(maxPrice));
    }
}
