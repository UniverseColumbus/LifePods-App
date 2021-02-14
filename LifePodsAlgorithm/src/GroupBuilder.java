import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

public class GroupBuilder {
	
	private static int POD_SIZE = 5;
	
	/**
	 * Method to create the pods
	 * 
	 * @param users
	 * @return pods
	 */
	public static ArrayList<LifePod> buildPods(ArrayList<User> users) {
		// We can write our logic here and return a list of LifePods
		ArrayList<LifePod> pods = new ArrayList<>();
		HashMap<Integer, User> usersMapped = new HashMap<>();
		int maxPods = users.size() / POD_SIZE;

		// Checks for Users who wants to be leader
		for (User u : users) {
			usersMapped.put(u.getId(), u);
			if (u.getWillingPodLeader().equals("yes") && pods.size() < maxPods) {
				LifePod newPod = new LifePod();
				newPod.addUser(u);
				newPod.addLeader(u);
				pods.add(newPod);
			}
		}
		
		// Add users with no preference and anti-preference randomly into groups
		for (User u : users) {
			if (u.hasGroup()) continue;
			boolean noPref = true;
			for (int id : u.getPreferenceList()) {
				if (usersMapped.containsKey(id)) noPref = false;
			}
			
			boolean noAntiPref = true;
			for (int id : u.getAntiPreferenceList()) {
				if (usersMapped.containsKey(id)) noAntiPref = false;
			}
			
			if (noPref && noAntiPref) {
				int randomPodIndex = ThreadLocalRandom.current().nextInt(0, pods.size());
				LifePod podToTry = pods.get(randomPodIndex);
				while (checkPodAntiPreference(podToTry, u)) {
					randomPodIndex = ThreadLocalRandom.current().nextInt(0, pods.size());
					podToTry = pods.get(randomPodIndex);
					System.out.println("not allowed");
				}
				pods.get(randomPodIndex).addUser(u);
			}
		}

		return pods;
	}
	
	/**
	 * checks if user is welcome in a certain LifePod
	 * @param pod
	 * @param user
	 * @return true if the user is not welcome
	 */
	private static boolean checkPodAntiPreference(LifePod pod, User user) {
		boolean antiPreference = false;
		for (User u : pod.getPod()) {
			for (int id : u.getAntiPreferenceList()) {
				if (id == user.getId()) return true;
			}
		}		
		return antiPreference;
	}
}
