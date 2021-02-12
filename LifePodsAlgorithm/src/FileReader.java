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
					String postGradPlans = rowScanner.next();
					String willingPodLeader = rowScanner.next();
					int pref1 = Integer.parseInt(rowScanner.next());
					int pref2 = Integer.parseInt(rowScanner.next());
					int antPref1 = Integer.parseInt(rowScanner.next());
					int antPref2 = Integer.parseInt(rowScanner.next());
					int antPref3 = Integer.parseInt(rowScanner.next());
					int antPref4 = Integer.parseInt(rowScanner.next());
					
					users.add(new User(id, postGradPlans, willingPodLeader, pref1, pref2, antPref1, antPref2, antPref3, antPref4));
				}				
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return users;
	}
}
