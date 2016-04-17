import java.io.File;

import com.fasterxml.jackson.databind.ObjectMapper;

public final class Example {

    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Body body = mapper.readValue(new File("test.json"), Body.class);
        GangseoFoodHygieneBizBakery response = body.getGangseoFoodHygieneBizBakery();
        if(response.getResult().getCode().equals("INFO-000")){
        	System.out.println("데이터 조회 성공 : "+response.getResult().getMessage());
        	for(Bakery bakery : response.getBakeryList()){
        		System.out.println("-------------------");
        		System.out.println("제과점 이름 : "+bakery.getName());
        		System.out.println("제과점 주소 : "+bakery.getAddress());
        		System.out.println("제과점 전화번호 : "+bakery.getPhoneNumber());
        		System.out.println("제과점 설립년도 : "+bakery.getFoundationYear());
        	}
        }else{
        	System.out.println("데이터 조회에 실패하였습니다.");
        }
    }
}
