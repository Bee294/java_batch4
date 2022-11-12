//abstract class: trong class nay se chua 1 phương thức co kieu laf abstract
public abstract class Animal {
    private String name;
    public Animal(){};
    public Animal(String name){
        this.name = name;
    }

    //neu phuong thuc la abstract thi no co the duoc the hien theo cach cua no
    //co the ghi de hanh vi nay o lop con

    public abstract String getAnimalName();
}
