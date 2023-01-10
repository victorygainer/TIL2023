//Object Mapper란?
//JSON 형식을 사용할 때, 응답들을 직렬화하고 요청들을 역직렬화 할 때 사용하는 기술
//직렬화(Serialize): 데이터를 전송하거나 저장할 때 바이트 문자열이어야 하기 때문에 객체들을 문자열로 바꾸어 주는 것, Object -> String 문자열
//역직렬화(Deserialize) : 데이터가 모두 전송된 이후, 수신측에서 다시 문자열을 기존의 객체로 회복시켜주는 것, String 문자열 -> Object

@Getter // Object -> String 문자열로 바꿀 때 필요!
class Car {
 private String name;
 private String color;
 
 public Car(String name, String color) {
  this.name = name;
  this.color = color;
 }
 
 public Car() { // String 문자열 => Object로 바꿀 때 필요!
  this.name = null;
  this.color = null;
 } 
}

ObjectMapper mapper = new ObjectMapper();
Car car = new Car("K5", "gray");

//직렬화(Serialize)
String text = mapper.WriteValueAsString(car); //{"name":"K5","color":"gray"}

//역직렬화 (DeSerialize, Jackson Library 이용)
Car carObject = mapper.readValue(text, Car.class); //Car{name='k5',color='gray'}
