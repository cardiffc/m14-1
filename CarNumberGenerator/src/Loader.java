import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Loader
{
    public static void main(String[] args)
    {
        long start = System.currentTimeMillis();

        List<Thread> threads= new ArrayList<>();
        int begin = 1;
        for (int i = 0; i < 4; i++) {
            threads.add(getThread(begin, begin+25, i));
            begin += 25;
        }

        threads.forEach(Thread::start);

        threads.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println((System.currentTimeMillis() - start) + " ms");
    }

    private static Thread getThread (int start, int stop, int filenumber) {

        Thread thread = new Thread(() -> {


            PrintWriter writer = null;
            try {
                writer = new PrintWriter("res/numbers" + filenumber + ".txt");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            StringBuilder builder = new StringBuilder();
            char letters[] = {'У', 'К', 'Е', 'Н', 'Х', 'В', 'А', 'Р', 'О', 'С', 'М', 'Т'};
            for (int regionCode = start; regionCode < stop ; regionCode++) {
                for(int number = 1; number < 1000; number++) {
                    for (char firstLetter : letters) {
                        for (char secondLetter : letters) {
                            for (char thirdLetter : letters) {
                                builder.append(firstLetter);
                                builder.append((number < 100) ? padNumber(Integer.toString(number),3) : number);
                                builder.append(secondLetter);
                                builder.append(thirdLetter);
                                StringBuilder reg = new StringBuilder();
                                if (regionCode < 10) {
                                    reg.append("0");
                                }
                                reg.append(regionCode);
                                builder.append(reg);
                                builder.append("\n");
                            }
                        }
                    }
                }
                writer.write(builder.toString());
                builder = new StringBuilder();
            }
            writer.flush();
            writer.close();


        });

        return thread;
    }

    private synchronized static StringBuilder padNumber(String numberStr, int numberLength)
    {
        int padSize = numberLength - numberStr.length();
        StringBuilder returningNumber = new StringBuilder();
        for(int i = 0; i < padSize; i++) {
            returningNumber.append("0");
        }
        returningNumber.append(numberStr);
        return returningNumber;
    }
}
