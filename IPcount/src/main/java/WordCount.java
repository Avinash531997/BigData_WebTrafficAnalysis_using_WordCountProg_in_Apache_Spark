import org.apache.spark.api.java.*;
import org.apache.spark.SparkConf;
import scala.Tuple2;
import java.util.Arrays;

public class WordCount {
 public static void main(String[] args) {

  SparkConf conf = new SparkConf().setMaster("local").setAppName("wordCount");
  JavaSparkContext sc = new JavaSparkContext(conf);

  // Load our input data.
  String inputFile = "file:///home/iitp/spark/ipcount/input.txt";
  ///home/iitp/spark-2.2.0-bin-hadoop2.6/wordcount/input.txt";
  String outputFile = args[0];
  JavaRDD < String > input = sc.textFile(inputFile);
  // Split in to list of words
  JavaRDD < String > words = input.flatMap(l -> Arrays.asList(l.split(" ")).iterator());

  // Transform into pairs and count.
  JavaPairRDD < String, Integer > pairs = words.mapToPair(w -> new Tuple2(w, 1));

  JavaPairRDD < String, Integer > counts = pairs.reduceByKey((x, y) -> x + y);

  System.out.println(counts.collect());
  counts.saveAsTextFile(outputFile);
 }
}
