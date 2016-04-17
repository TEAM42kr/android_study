import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GangseoFoodHygieneBizBakery {
	@JsonProperty("list_total_count")
	private int listTotalCount;
	@JsonProperty("RESULT")
	private Result result;
	@JsonProperty("row")
	private List<Bakery> bakeryList;

	public List<Bakery> getBakeryList() {
		return bakeryList;
	}

	public void setBakeryList(List<Bakery> bakeryList) {
		this.bakeryList = bakeryList;
	}

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	public int getListTotalCount() {
		return listTotalCount;
	}

	public void setListTotalCount(int listTotalCount) {
		this.listTotalCount = listTotalCount;
	}
}