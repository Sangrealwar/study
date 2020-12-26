package study.java8;

/**
 * @Description:
 *
 *
 * 如果一个嵌套类需要在单个方法之外是可见的，或者太长了不适合放在方法内部，则应该使用成员类。
 *      如果成员类的每个实例需要指向其外围实例的引用，就要把成员类做成非静态的。否则做成静态的。
 *
 * 如果嵌套类只属于方法内部，只需要在一个地方创建实例，并且已经有了一个预制的类型，就做成匿名类
 *  否则做成局部类
 *
 * @Author; weiqian
 * @Date: 2020/10/22 10:14 下午
 * @Version:
 */
public class InnerClassStudy {
    public String outClassField="外部字段";

    private void outMethod(){
        System.out.println("外部类方法");
    }

    public void outRun(){
        InnerClass innerClass=new InnerClass();
        innerClass.run();

        InnerStaticClass a = new InnerStaticClass();
        a.run();

        class PartClass{
            public void run (){
                System.out.println("局部类");
            }
        }

        PartClass partClass = new PartClass();
        partClass.run();
    }

    public static void main(String[] args) {
        InnerClassStudy a = new InnerClassStudy();
        a.outRun();
    }

    /**
     * 静态成员，节省一个引用，同时不需要访问外层类的变量方法，可以独立存在
     * 常见用法是Map里的Entry实现，如HashMap，实现了一个Node，因为
     */
    static class InnerStaticClass {
        public void run (){
            System.out.println("静态内部类的方法");
        }
    }

    /**
     * 非静态成员创建时，会隐含一个指向外层实例的引用
     * 常见用法是在Map的keyset，entryset中使用集合视图，暴露entrySet方法，返回非静态内部类EntrySet。同时将非静态内部类标记为final
     *
     */
    class InnerClass{
        private String innerClassField="内部字段";

        public void run(){
            System.out.println("非静态内部类的方法");
            System.out.println("输出内部字段"+innerClassField);
            System.out.println("输出外部字段"+outClassField);
        }
    }


}
