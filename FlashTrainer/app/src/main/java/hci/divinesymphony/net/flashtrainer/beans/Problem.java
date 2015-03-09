package hci.divinesymphony.net.flashtrainer.beans;


import java.lang.String;
import java.lang.StringBuffer;

public class Problem {

	private String text;

	private String audio;
	
	private String image;
		
	private String probID;
	
	private String weight;
	
	public Problem(){
		
	}
	
	public Problem(String text, String audio, String image,String probID, String weight) {
		this.text = text;
		this.audio = audio;
		this.image  = image;
		this.probID  = probID;
		this.weight  = weight;
		
	}
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getAudio() {
		return audio;
	}

	public void setName(String audio) {
		this.audio = audio;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}	

	public String getprobID() {
		return probID;
	}

	public void setprobID(String probID) {
		this.probID = probID;
	}	
	
	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Problem: " + getText());
		sb.append("\n");
		sb.append("Audio: " + getAudio());
		sb.append("\n");
		sb.append("Image:" + getImage());
		sb.append("\n");
		sb.append("Prob ID: " + getprobID());
		sb.append("\n");
		sb.append("Weight: " + getWeight());
		
		return sb.toString();
	}
}
