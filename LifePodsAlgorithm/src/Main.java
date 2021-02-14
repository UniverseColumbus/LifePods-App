import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		// file path
		String file = "src/source/life_pod.csv";
		ArrayList<User> users;
		
		//read file and assign Users to the users ArrayList
		users = FileReader.readFile(file);		
		
		//prints all the users
		/*for (User u : users) {
			System.out.println(u.toString());
		}*/
		
		ArrayList<LifePod> pods = GroupBuilder.buildPods(users);
		for (LifePod p : pods) {
			System.out.println(p.toString());
		}
	}

}
