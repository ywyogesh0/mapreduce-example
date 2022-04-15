import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

/**
 * A driver program will gather all the needed information to submit map-reduce job
 * <p>
 * jar-name: jar file which hadoop uses to submit the job in the cluster
 * driver-class-name: name of the driver class which hadoop will use to locate the jar file in the cluster
 * input-path: location of your input dataset
 * output-path: location where map-reduce job will output the result-set
 * mapper-class: mapper program which needs to be executed for mapper phase
 * reducer-class: reducer program which needs to be executed for reducer phase
 */
public class WordCountDriver {

    public static void main(String[] args) throws Exception {
        // 1. as per the map-reduce program, when you run the hadoop jar command it needs at-least 2 arguments from command line.
        // 2. if not provided then it will terminate the application instantly.
        // 3. non-zero status code signifies abnormal termination
        if (args.length != 2) {
            System.err.println("Usage: hadoop jar <jar-name> <driver-class-name> <input-path> <output-path>");
            System.exit(-1);
        }

        // 1. job object refers to the map-reduce job which will instantiate the hadoop job class.
        // 2. job object sets required configurations needed to submit the job on hadoop cluster.
        // 3. hadoop job will submit the packaged jar across all the nodes in the cluster for distributed processing.
        Job job = new Job();
        job.setJarByClass(WordCountDriver.class);
        job.setJobName("WordCount");

        // 1. input path is HDFS path where your input dataset is located
        // 2. output path is HDFS path where hadoop job will write the result-set
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        // 1. input format class will tell hadoop the information of what input dataset you are using.
        // - validate inputs (whether the input directory exists)
        // - split your input file in data chunks aka logical input-splits
        // - use RecordReader class implementation to extract logical records

        // 2. output format class will tell hadoop the information of what output result type should be :-
        // - validate output specifications (whether the output directory is already existed)
        // - use RecordWriter class implementation to write output result-set of the job
        //
        // Note: hadoop creates a new directory everytime to write the result-set as it doesn't want by mistake to overwrite the
        // existing directory (safety check)
        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);

        // 1. mapper class will tell hadoop what mapper implementation it should use to run the job for mapper phase
        // 2. reduce class will tell hadoop what reducer implementation it should use to run the job for reducer phase
        job.setMapperClass(WordCountMapper.class);
        job.setReducerClass(WordCountReducer.class);

        // 1. output key class will tell hadoop what type of output key mapper and reducer using
        // 2. output value class will tell hadoop what type of output value mapper and reducer using
        // 3. writable class will give you a simple and efficient serialization protocol which is fast and compact.
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        // 1. waitForCompletion method will submit the job and wait for its completion
        // 2. if you want to see the progress of the job on the console then enable the verbose parameter for the method
        // 3. non-status code signifies abnormal termination
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}