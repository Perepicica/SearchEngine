import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ParsersManager implements Runnable {
    private static ConcurrentLinkedDeque<Future<ArrayList<Word>>> futures = new ConcurrentLinkedDeque<>();
    private static ExecutorService service;
    private int maxNumberOfThreads;
    private int maxDepthOfSearch;

    public ParsersManager(int maxNumberOfThreads, int maxDepthOfSearch) {
        this.maxDepthOfSearch = maxDepthOfSearch;
        this.maxNumberOfThreads = maxNumberOfThreads;
    }

    @Override
    public void run() {
        try {

            service = Executors.newFixedThreadPool(maxNumberOfThreads);


            ArrayList<String> links = (ArrayList<String>) UrlReader.selecthttp();

            for (String link : links) {
                futures.add(service.submit(new Parser(link, 1, maxDepthOfSearch, new CopyOnWriteArrayList<>())));
            }

            while (!futures.isEmpty()) {
                try {
                    List<Word> futureResult = futures.getFirst().get();

                    if (!futureResult.isEmpty()) {
                        String link = futureResult.get(0).getWord();
                        futureResult.remove(0);

                        if (!futureResult.isEmpty()) {
                            WordsWriter.insertData(link, futureResult);
                        }
                    }

                } catch (Exception e) {
                    e.getStackTrace();
                } finally {
                    futures.pollFirst();
                }
            }
        } catch (IOException e) {
            System.exit(-1);
        }
    }


    public static synchronized void addTasks(ArrayList<Parser> tasks) {

        for (Parser task : tasks) {
            futures.add(service.submit(task));
        }
    }
}
