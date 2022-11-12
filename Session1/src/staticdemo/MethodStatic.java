package staticdemo;
//Một phương thức static thuộc lớp chứ không phải đối tượng của lớp.
//Một phương thức static có thể được gọi mà không cần tạo khởi tạo (instance) của một lớp.
//Phương thức static có thể truy cập biến static và có thể thay đổi giá trị của nó.
public class MethodStatic {
    int rollno;
    String name;
    static String college = "Bưu Chính Viễn Thông";

    static void change() {
        // Thay đổi thuộc tính static (thuộc tính chung của tất cả các đối tượng)
        college = "Đại Học Công Nghệ";
    }

    public MethodStatic(int r, String n) {
        rollno = r;
        name = n;
    }

    void display() {
        System.out.println(rollno + " - " + name + " - " + college);
    }

    public static void main(String args[]) {
        MethodStatic.change(); //goi luon method k can thong qua doi tuong

        MethodStatic s1 = new MethodStatic(111, "Thông");
        MethodStatic s2 = new MethodStatic(222, "Minh");


        s1.display();
        s2.display();
    }
}
