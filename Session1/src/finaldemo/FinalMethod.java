package finaldemo;

//không thể ghi đè phương thức final ctrinh sẽ báo lỗi
public class FinalMethod {
    public class Bike {
        public final void run() {
            System.out.println("running");
        }
    }

//    public class Motorbike extends Bike {
//        public Motorbike(){};
//        public void run() {
//            System.out.println("Chay an toan voi 150km/h");
//        }
//
//    }

//    public static void main(String args[]) {
//        Motorbike sh = new Motorbike();
//        sh.run();
//    }


}
