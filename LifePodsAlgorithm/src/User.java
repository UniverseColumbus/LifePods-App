import java.util.Arrays;

public class User {	
	private int id;
	private String postGradPlans;
	private String willingPodLeader;
	private int preferenceList [];
	private int antiPreferenceList [];
	private boolean hasGroup;

	public User(int id, String postGradPlans, String willingPodLeader, int[] preferenceList, int[] antiPreferenceList) {
		super();
		this.id = id;
		this.postGradPlans = postGradPlans;
		this.willingPodLeader = willingPodLeader;
		this.preferenceList = preferenceList;
		this.antiPreferenceList = antiPreferenceList;
		this.hasGroup = false;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPostGradPlans() {
		return postGradPlans;
	}

	public void setPostGradPlans(String postGradPlans) {
		this.postGradPlans = postGradPlans;
	}

	public String getWillingPodLeader() {
		return willingPodLeader;
	}

	public void setWillingPodLeader(String willingPodLeader) {
		this.willingPodLeader = willingPodLeader;
	}

	public int[] getPreferenceList() {
		return preferenceList;
	}

	public void setPreferenceList(int[] preferenceList) {
		this.preferenceList = preferenceList;
	}

	public int[] getAntiPreferenceList() {
		return antiPreferenceList;
	}

	public void setAntiPreferenceList(int[] antiPreferenceList) {
		this.antiPreferenceList = antiPreferenceList;
	}

	public boolean hasGroup() {
		return hasGroup;
	}

	public void setHasGroup(boolean hasGroup) {
		this.hasGroup = hasGroup;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", postGradPlans=" + postGradPlans + ", willingPodLeader=" + willingPodLeader
				+ ", preferenceList=" + Arrays.toString(preferenceList) + ", antiPreferenceList="
				+ Arrays.toString(antiPreferenceList) + "]";
	}
}
