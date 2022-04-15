import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * Mapper class is used for mapper phase during the job run
 * <p>
 * - dataset is divided into multiple parts aka input-splits
 * - each mapper process an input-split
 * - each mapper can be called multiple times depending on the content of the input-split
 * - mapper will emit key-value pairs as an output
 * - there could be one or more mapper in map-reduce job
 */
class WordCountMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

    /**
     * @param key     byte offset which tells the record position in the file to the mapper (ignore)
     * @param value   input value to the mapper phase
     * @param context map-reduce job context which is used to perform specific activity around the job surrounding (within the job)
     * @throws IOException          input/output operations failure
     * @throws InterruptedException some sort of exception happened which interrupted the job execution
     */
    @Override
    public void map(LongWritable key, Text value, Context context)
            throws IOException, InterruptedException {
        String line = value.toString();
        String[] words = line.split(" ");
        for (String word : words) {
            context.write(new Text(word), new IntWritable(1));
        }
    }
}
