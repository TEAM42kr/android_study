import com.fasterxml.jackson.annotation.JsonProperty;

public class Body {
	@JsonProperty("GangseoFoodHygieneBizBakery")
	private GangseoFoodHygieneBizBakery gangseoFoodHygieneBizBakery;

	public GangseoFoodHygieneBizBakery getGangseoFoodHygieneBizBakery() {
		return gangseoFoodHygieneBizBakery;
	}

	public void setGangseoFoodHygieneBizBakery(GangseoFoodHygieneBizBakery gangseoFoodHygieneBizBakery) {
		this.gangseoFoodHygieneBizBakery = gangseoFoodHygieneBizBakery;
	}
}
