public class Cat extends Animal implements CatSkill{
    private int age;
    private int height;

    public Cat(int age,int height){  //constructor -> khoi tao doi tuong
        this.age =age;
        this.height= height;
    }
    public Cat(int age,int height,String name){
        super(name); //goi constructor cua class cha(Animal) -> tinh ke thua
        this.age=age;
        this.height=height;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String getAnimalName() {
        return "Cat class";   //tinh chat da hinh cua oops
    }

    @Override
    public void findMouse() {

    }
}
