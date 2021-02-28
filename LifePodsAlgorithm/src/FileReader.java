import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileReader {
	
	/**
	 * Reads csv file and return a list of Users
	 * @param file path
	 * @return users
	 */
	public static ArrayList<User> readFile(String file) {
		int rowCount = 0;
		ArrayList<User> users = new ArrayList<User>();
		
		try (Scanner scanner = new Scanner(new File(file));) {
			while (scanner.hasNextLine()) {
				if (rowCount == 0) {
					rowCount++;
					scanner.nextLine();
					continue;
				}
				rowCount++;
				String row = scanner.nextLine();
				try (Scanner rowScanner = new Scanner(row)) {
					rowScanner.useDelimiter(",");
					int id = Integer.parseInt(rowScanner.next());
					String plans = rowScanner.next();
					String willLead = rowScanner.next();
					int friends [] = new int [2];
					friends[0] = Integer.parseInt(rowScanner.next());
					friends[1] = Integer.parseInt(rowScanner.next());
					
					int enemies [] = new int [4];
					enemies[0] = Integer.parseInt(rowScanner.next());
					enemies[1] = Integer.parseInt(rowScanner.next());
					enemies[2] = Integer.parseInt(rowScanner.next());
					enemies[3] = Integer.parseInt(rowScanner.next());
					
					users.add(new User(id, plans, willLead, friends, enemies));
				}				
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return users;
	}
}
