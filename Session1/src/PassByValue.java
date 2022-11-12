public class PassByValue {
    //truyền tham trị:Kiểu này dành cho các biến, tham số khai báo kiểu dữ liệu cơ bản nguyên thủy(int,float,double,..)
    //Method được gọi sẽ sao chép một bản sao của tham số truyền vào và hoạt động trên chúng.
    // Mọi thay đổi trên bản sao này không ảnh hưởng đến giá trị ban đầu.
    int data = 50;

    void change(int data) {
        data = data + 100;
    }

    public static void main(String args[]) {
        PassByValue op = new PassByValue();

        System.out.println("Trước khi thay đổi: " + op.data);
        op.change(500);
        System.out.println("Sau khi thay đổi: " + op.data);
    }
}
