import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class GoogleSheetsRepo{

    private ArrayList<String[]> repository = new ArrayList<>();

    public ArrayList<String[]>  GoogleSheetsRepo() throws IOException {

        String csvFile = "C:\\Users\\Leila\\Documents\\GitHub\\Telegrambot\\src\\main\\resources\\MathTerm - Definition.csv";

        BufferedReader csvReader = new BufferedReader(new FileReader(csvFile));
        String row;
        while ((row = csvReader.readLine()) != null) {
            String[] data = row.split(",", 2);
            repository.add(data);
        }
        csvReader.close();

        return repository;
    }

}