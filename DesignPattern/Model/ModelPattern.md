# 模版方法

## 定义

定义一个操作中的算法骨架，而将算法的一些步骤延迟到子类中，使得子类可以不改变算法结构的情况下重新定义该算法的某些特定步骤。

## 核心思想

1. 定义算法骨架：将算法的不变部分（步骤顺序）放在父类中
2. 延迟实现：将可变部分定义为抽象方法或钩子方法，有子类实现
3. 避免重复：消除子类中的代码重复

## 优缺点

1. 优点：
   - 把认为是不变部分的算法封装到父类中实现，而把可变部分算法由子类继承实现，便于子类扩展。
   - 在父类中提取了公共的部分代码便于代码复用。
   - 部分方法是由子类实现的，子类可以通过扩展的方式增加相应的功能。
2. 缺点：
   - 对每个不同的实现都需要定义一个子类，导致类的个数增加，系统更加庞大，设计更抽象，间接增加了系统实现的复杂度。
   - 父类中的抽象方法由子类实现，子类执行的结果会影响父类。
   - 由于继承关系自身的缺点，如果父类添加新的抽象方法，则所有的子类都要更改。

## 结构

![image](/DesignPattern/images/ModelPattern.png)

1. 抽象模版类：负责给出一个算法的轮廓和骨架。

    模版方法：定义了算法的骨架，按某种顺序调用其包含的基本方法。
    基本方法：
    - 抽象方法：在抽象类中声明，由具体子类实现。
    - 具体方法：在抽象类中已经实现，在具体子类中可以继承或重写它。
    - 钩子方法：在抽象类中已经实现，包括由于判断的逻辑方法和需要子类重写的空方法两种。
2. 具体子类：实现抽象类中所定义的抽象方法和钩子方法。

## 应用场景

1. 算法框架固定但实现可变
2. 代码复用与扩展
3. 框架设计
4. 流程控制
5. 带钩子的扩展点

## 代码示例

### Java示例

```Java
public abstract Bank {

    AService aservice;
    
    BService bservice;

    //如果要使用service接口，可以通过构造方法来注入
    public Bank(AService aservice, BService bservice){
        this.aservice = aservice;
        this.bservice = bservice;
    }

    public void service(){
        getNum();
        doWait();
        doService();
        doExit();
    }

    public void getNum(){
        System.out.println("取号中。。。");
    }

    public void doWait(){
        System.out.println("等待中。。。");
    }

    protected abstract void doService();

    public void doExit(){
        System.out.println("离开。。。");
    }

    public static void main(String[] args){
        Bank demo = new Demo();
        demo.service();
    }
}

public class people extends Bank{
    @Override
    protected void service(){
        System.out.println("存款中。。。");
    } 
}

或者

public class people {
    Bank bank = new Bank(
        @Override
        protected void doService(){
            System.out.println("存款中。。。");
        }
    )
    bank.service();
}

扩展：
callback模式
public interface Callback{
    void onCallback(String message);
}

public class Caller{
    private Callback callback;

    public void doSomething(){
        String message = "操作完成";
        callback.onCallback(message);
    }
}

public class testClass{
    public static void main(String[] args){
        Callback callback = new Callback(){
            @Override
            void onCallback(String message){
                System.out.println(message);
            }
        }
        //Callback callback = message -> System.out.println(message);

        Caller caller = new Caller(callback);
        caller.doSomething();
    }
}
```

### Python示例

```Python
from abc import ABC, abstractmethod

# 1. 抽象类：定义模板方法
class DocumentProcessor(ABC):
    # 模版方法：定义处理流程（不可被子类重写）
    def process_document(self):
        self.open_document()
        self.read_content()
        self.parse_content()
        if self.needs_validation():
            self.validate_content()
        self.save_document()
        self.close_document()

    # 具体方法：通用实现（子类可直接使用）
    def open_document(self):
        print("打开文档...")

    # 抽象方法：通用实现（子类可直接使用）
    @abstractmethod
    def read_content(self):
        pass

    @abstractmethod
    def parse_content(self):
        pass

    # 钩子方法：可选实现（提供默认行为）
    def needs_validation(self):
        return False

    def validate_content(self):
        print('正在验证文档内容...')

    # 抽象方法
    @abstractmethod
    def save_document(self):
        pass

    # 具体方法
    def close_document(self):
        print('关闭文档...')

# 2. 具体类：PDF文档处理器
class PDFProcessor(DocumentProcessor):
    def read_content(self):
        print('读取PDF文档内容')

    def parse_content(self):
        print('解析PDF文本和图像')

    def needs_validation(self):
        return True # 覆盖钩子方法，启用验证

    def validate_content(self):
        print("验证PDF数字签名...")

    def save_document(self):
        print("保存为PDF/A格式...")

# 3. 具体类：Word文档处理器
class WordProcessor(DocumentProcessor):
    def read_content(self):
        print("读取Word文档内容...")
    
    def parse_content(self):
        print("解析Word样式和表格...")
    
    def save_document(self):
        print("保存为DOCX格式...")

# 4. 具体类：Markdown处理器
class MarkdownProcessor(DocumentProcessor):
    def read_content(self):
        print("读取Markdown文本...")
    
    def parse_content(self):
        print("解析Markdown语法...")
    
    def save_document(self):
        print("导出为HTML格式...")
    
    def close_document(self):
        print("清理临时文件...")
        super().close_document()  # 调用父类方法

# 客户端代码
if __name__ == "__main__":
    print("==== 处理PDF文档 ====")
    pdf_processor = PDFProcessor()
    pdf_processor.process_document()
    
    print("==== 处理Word文档 ====")
    word_processor = WordProcessor()
    word_processor.process_document()
    
    print("==== 处理Markdown文档 ====")
    md_processor = MarkdownProcessor()
    md_processor.process_document()
```
