class MyClass {
    static {
        System.out.println("Run static initialization block.");
    }

    {
        System.out.println("Run non-static initialization block.");
    }
}

public class Review37ClassObject {

    public static void main(String[] args) {
//        classTest();
//        forNameTest();
        getClassTest();
    }

    private static void classTest() {
        Class c = MyClass.class;
    }

    private static void forNameTest() {
        try {
            Class c = Class.forName("MyClass");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void getClassTest() {
        MyClass myClass = new MyClass();
        Class c = myClass.getClass();
    }
}

// https://www.cnblogs.com/javazhiyin/p/14265666.html
class Test1 {
    private static int i = 1;
    static {
        i = 2; // static code block 可以「赋值」定义在它「之前/之后」的变量，
        System.out.println(i); // 但 static code block 只能「访问」定义在它「之前」的变量
    }
//    private static int i = 1; // compiling error
}
// class loading: test2 = null, value1 = 0, value2 = 0
// class initialization: test2 = new Test2();
    // call constructor Test2()
    // value1++ => value1 = 1
    // value2++ => value2 = 1
// class initializing: test2 has been initialized, value1 = 1 (didn't change), value2 = 3 (changed)
// class loaded and initialized.
// print value1 = 1
// print value2 = 3
class Test2 {
    private static Test2 test2 = new Test2();
    private static int value1;
    private static int value2 = 3;

    public Test2() {
        value1++;
        value2++;
    }

    public static void main(String[] args) {
        System.out.println(test2.value1);
        System.out.println(test2.value2);
    }
}
// class loading: value1 = 0, value2 = 0, test3 = null
// class initialization: value1 = 0 (didn't change), value2 = 3 (changed), test3 = new Test3()
    // call constructor Test3()
    // value1++ => value1 = 1
    // value2++ => value2 = 4
// class initializing: value1 = 1, value2 = 4, test3 has been initialized
// class loaded and initialized.
// print value1 = 1
// print value2 = 4
class Test3 {
    private static int value1;
    private static int value2 = 3;
    private static Test3 test3 = new Test3();

    public Test3() {
        value1++;
        value2++;
    }

    public static void main(String[] args) {
        System.out.println(test3.value1);
        System.out.println(test3.value2);
    }
}

