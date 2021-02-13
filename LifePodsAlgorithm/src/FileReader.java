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
					int preferenceList [] = new int [2];
					preferenceList[0] = Integer.parseInt(rowScanner.next());
					preferenceList[1] = Integer.parseInt(rowScanner.next());
					
					int antiPreferenceList [] = new int [4];
					antiPreferenceList[0] = Integer.parseInt(rowScanner.next());
					antiPreferenceList[1] = Integer.parseInt(rowScanner.next());
					antiPreferenceList[2] = Integer.parseInt(rowScanner.next());
					antiPreferenceList[3] = Integer.parseInt(rowScanner.next());
					
					users.add(new User(id, postGradPlans, willingPodLeader, preferenceList, antiPreferenceList));
				}				
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return users;
	}
}
