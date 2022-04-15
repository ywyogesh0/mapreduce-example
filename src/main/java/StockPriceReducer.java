import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

class StockPriceReducer extends Reducer<Text, FloatWritable, Text, FloatWritable> {

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