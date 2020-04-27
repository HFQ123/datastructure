/**
 * @Created by hfq on 2020/4/9 20:10
 * @used to: 随机生成数据表记录
 *           数据格式如：
 *           id :1000--1100
 *           parkinglot_id :1、2、3
 *           car_id:
 *           in_use :0、1
 *           park_time : 当in_use不为空时有取值
 *
 * @return:
 */
public class ParkingData {
    private int id;
    private int parkinglot_id;

    private int in_use;
    private String car_id;
    private Long park_time;

    public String insert(){
        return "insert into parking_space values ("+id+","+parkinglot_id+","+car_id+","+in_use+","+park_time+")";
    }

    public static void main(String[] args) {
        System.out.println(System.currentTimeMillis());

         int in_use;
         String car_id;
         Long park_time;
        for(int i=1;i<=3;i++){
            for(int j=1000;j<=1100;j++){
                in_use=  ((int)(10 * Math.random())) % 2;
//                if(in_use==0){
//                    car_id="";
//                    park_time=0l;
//                }
//                else{
                    car_id="浙A"+(int) ((Math.random() * 9 + 1) * Math.pow(10, 5 - 1));
//                    car_id="浙A"+(int)(Math.random()*9+1)*10000;
                    park_time=System.currentTimeMillis()-(int) ((Math.random() * 9 + 1) * Math.pow(10, 4 - 1));
//                }
//                System.out.println("insert into parking_space(parkinglot_id,id,car_id,in_use,park_time) values ("+i+","+j+",\""+car_id+"\","+in_use+","+park_time+");");
            }
        }
    }

}
