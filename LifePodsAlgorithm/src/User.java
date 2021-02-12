
public class User {	
	private int id;
	private String postGradPlans;
	private String willingPodLeader;
	private int pref1;
	private int pref2;
	private int antPref1;
	private int antPref2;
	private int antPref3;
	private int antPref4;
	
	public User(int id, String postGradPlans, String willingPodLeader, int pref1, int pref2, int antPref1,
			int antPref2, int antPref3, int antPref4) {
		this.id = id;
		this.postGradPlans = postGradPlans;
		this.willingPodLeader = willingPodLeader;
		this.pref1 = pref1;
		this.pref2 = pref2;
		this.antPref1 = antPref1;
		this.antPref2 = antPref2;
		this.antPref3 = antPref3;
		this.antPref4 = antPref4;
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

	public String isWillingPodLeader() {
		return willingPodLeader;
	}

	public void setWillingPodLeader(String willingPodLeader) {
		this.willingPodLeader = willingPodLeader;
	}

	public int getPref1() {
		return pref1;
	}

	public void setPref1(int pref1) {
		this.pref1 = pref1;
	}

	public int getPref2() {
		return pref2;
	}

	public void setPref2(int pref2) {
		this.pref2 = pref2;
	}

	public int getAntPref1() {
		return antPref1;
	}

	public void setAntPref1(int antPref1) {
		this.antPref1 = antPref1;
	}

	public int getAntPref2() {
		return antPref2;
	}

	public void setAntPref2(int antPref2) {
		this.antPref2 = antPref2;
	}

	public int getAntPref3() {
		return antPref3;
	}

	public void setAntPref3(int antPref3) {
		this.antPref3 = antPref3;
	}

	public int getAntPref4() {
		return antPref4;
	}

	public void setAntPref4(int antPref4) {
		this.antPref4 = antPref4;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", postGradPlans=" + postGradPlans + ", willingPodLeader=" + willingPodLeader
				+ ", pref1=" + pref1 + ", pref2=" + pref2 + ", antPref1=" + antPref1 + ", antPref2=" + antPref2
				+ ", antPref3=" + antPref3 + ", antPref4=" + antPref4 + "]";
	}
	
	
}
