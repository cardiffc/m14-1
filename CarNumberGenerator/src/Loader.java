import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.*;

public class Loader
{
    public static void main(String[] args)
    {
        long start = System.currentTimeMillis();

        List<Thread> threads= new ArrayList<>();
        int begin = 1;
        for (int i = 0; i < 4; i++) {
            if (begin == 1) {
                threads.add(getThread(begin, begin + 24, i));
                begin +=24;
            } else {
                threads.add(getThread(begin, begin + 25, i));
                begin += 25;
            }
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

            // Создаем массив регионов
            String[] regioncodes = new String[100];
            for (int i = 0; i < 100 ; i++) {
                regioncodes[i] = (i <= 9) ? "0".concat(Integer.toString(i)) : Integer.toString(i);
            }

            // Создаем массив номеров
            String[] numbers = new String[1000];
            for (int i = 0; i < 1000 ; i++) {
                String num = Integer.toString(i);
                if (i <= 9) {
                    numbers[i] = "00".concat(num);
                } else if (i <= 99) {
                    numbers[i] = "0".concat(num);
                } else {
                    numbers[i] = num;
                }
            }

            // Строим билдер, пишем в файл
            StringBuilder builder = new StringBuilder();
            char letters[] = {'У', 'К', 'Е', 'Н', 'Х', 'В', 'А', 'Р', 'О', 'С', 'М', 'Т'};
            for (int regionCode = start; regionCode < stop ; regionCode++) {
                for(int number = 1; number < 1000; number++) {
                    for (char firstLetter : letters) {
                        for (char secondLetter : letters) {
                            for (char thirdLetter : letters) {
                                builder.append(firstLetter);
                                builder.append(numbers[number]);
                                builder.append(secondLetter);
                                builder.append(thirdLetter);
                                builder.append(regioncodes[regionCode]);
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
}
