package igouc.com.nodepad.Empty;

/**
 * Created by gouchao on 16-4-26.
 */
public class Item {
	private int id;
	private String title;
	private String content;
	private long datetime;
	private int label;
	private int power;

	public Item(){

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public long getDatetime() {
		return datetime;
	}

	public void setDatetime(long datetime) {
		this.datetime = datetime;
	}

	public int getLabel() {
		return label;
	}

	public void setLabel(int label) {
		this.label = label;
	}

	public int getPower() {
		return power;
	}

	public void setPower(int power) {
		this.power = power;
	}

	@Override
	public String toString() {
		return "Item{" +
				"id=" + id +
				", title='" + title + '\'' +
				", content='" + content + '\'' +
				", datetime=" + datetime +
				", label=" + label +
				", power=" + power +
				'}';
	}
}
