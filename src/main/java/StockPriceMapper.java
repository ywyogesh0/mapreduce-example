import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

class StockPriceMapper extends Mapper<LongWritable, Text, Text, FloatWritable> {

    @Override
    public void map(LongWritable key, Text value, Context context)
            throws IOException, InterruptedException {
        String line = value.toString();
        String[] items = line.split(" ");
        String stock = items[0];
        float price = Float.parseFloat(items[1]);
        context.write(new Text(stock), new FloatWritable(price));
    }
}
