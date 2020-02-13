import java.io.FileOutputStream;

public class Loader
{
    public static void main(String[] args) throws Exception
    {
        long start = System.currentTimeMillis();

        FileOutputStream writer = new FileOutputStream("res/numbers.txt");
        StringBuilder builder = new StringBuilder();
        char letters[] = {'У', 'К', 'Е', 'Н', 'Х', 'В', 'А', 'Р', 'О', 'С', 'М', 'Т'};
        for(int number = 1; number < 1000; number++)
        {
            int regionCode = 199;
            for (char firstLetter : letters)
            {
                for (char secondLetter : letters)
                {
                    for (char thirdLetter : letters)
                    {
                        builder.append(firstLetter);
                        builder.append(padNumber(number,3));
                        builder.append(secondLetter);
                        builder.append(thirdLetter);
                        builder.append(padNumber(regionCode,2));
                        builder.append("\n");
                    }
                }
            }
        }
        writer.write(builder.toString().getBytes());
        writer.flush();
        writer.close();

        System.out.println((System.currentTimeMillis() - start) + " ms");
    }

    private static StringBuilder padNumber(int number, int numberLength)
    {
        String numberStr = Integer.toString(number);
        int padSize = numberLength - numberStr.length();
        StringBuilder returningNumber = new StringBuilder();

        for(int i = 0; i < padSize; i++) {
          returningNumber.append("0");
        }
        returningNumber.append(numberStr);
        return returningNumber;
    }
}
