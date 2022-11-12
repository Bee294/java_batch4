public class PassByReference {
    //Truyền tham chiếu: gọi một phương thức và truyền một tham chiếu cho phương thức đó được gọi là truyền tham chiếu.
    //nó lưu trữ bên trong nó một địa chỉ dùng để truy cập bộ nhớ khi lưu/lấy dữ liệu (giá trị) của biến(chính là các đối tượng).
    //chiếu tới địa chỉ bộ nhớ
    //Method được gọi và nơi gọi chúng sẽ thao tác trên cùng một object
    // Khi method thay đổi giá trị của tham số thì giá trị của chúng tại nơi gọi cũng thay đổi theo.
    int data = 50;

    void change(PassByReference op) {
        op.data = op.data + 100;
    }

    public static void main(String args[]) {
        PassByReference  op = new PassByReference ();

        System.out.println("Trước khi thay đổi: " + op.data);
        op.change(op); // truyền đối tượng
        System.out.println("Sau khi thay đổi: " + op.data);
    }
}
