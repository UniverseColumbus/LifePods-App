import java.util.ArrayList;

public class LifePod {	
	private ArrayList<User> pod;
	private int leader;
	private double score;
	
	public LifePod() {
		pod = new ArrayList<>();
	}

	public ArrayList<User> getPod() {
		return pod;
	}

	public void setPod(ArrayList<User> pod) {
		this.pod = pod;
	}

	public int getLeader() {
		return leader;
	}

	public void setLeader(int leader) {
		this.leader = leader;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}
	
	public void addUser(User user) {
		pod.add(user);
	}
	
	public void addLeader(User user) {
		this.leader = user.getId();
	}

	@Override
	public String toString() {
		StringBuilder strPod = new StringBuilder();
		strPod.append("LifePod: \n");
		for (User u : pod) {
			strPod.append(u.toString() + "\n");
		}
		
		return strPod.toString();
	}
}
