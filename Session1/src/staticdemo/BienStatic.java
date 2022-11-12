package staticdemo;
// static variable: la 1 bien tinh, duoc cap phat bo nho 1 lan khi class duoc nap vao bo nho
// thuoc tinh cua bien static duoc chia se toi tat ca doi tuong

public class BienStatic {
    int rollno;
    String name;
    static String college = "Bưu Chính Viễn Thông";

    public BienStatic(int r, String n) {
        rollno = r;
        name = n;
    }

    void display() {
        System.out.println(rollno + " - " + name + " - " + college);
    }

    public static void main(String args[]) {
        BienStatic s1 = new BienStatic(111, "Thông");
        BienStatic s2 = new BienStatic(222, "Minh");

        s1.display();
        s2.display();
    }
}
