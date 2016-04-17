import java.io.File;

import com.fasterxml.jackson.databind.ObjectMapper;

public final class Example {

    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Body body = mapper.readValue(new File("test.json"), Body.class);
        GangseoFoodHygieneBizBakery response = body.getGangseoFoodHygieneBizBakery();
        if(response.getResult().getCode().equals("INFO-000")){
        	System.out.println("������ ��ȸ ���� : "+response.getResult().getMessage());
        	for(Bakery bakery : response.getBakeryList()){
        		System.out.println("-------------------");
        		System.out.println("������ �̸� : "+bakery.getName());
        		System.out.println("������ �ּ� : "+bakery.getAddress());
        		System.out.println("������ ��ȭ��ȣ : "+bakery.getPhoneNumber());
        		System.out.println("������ �����⵵ : "+bakery.getFoundationYear());
        	}
        }else{
        	System.out.println("������ ��ȸ�� �����Ͽ����ϴ�.");
        }
    }
}
