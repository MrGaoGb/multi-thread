## IO流

### 1、IO流概念
> IO流是Java程序与外部设备之间进行数据交换的通道，即Java程序与外部设备之间进行数据交换的通道。
> 
> - 在Java中IO流分为两种类型：**字节流和字符流**。字节流以字节为单位进行数据交换，字符流以字符为单位进行数据交换。

#### 1.1 字节流
> InputStream 和 OutputStream 是IO包下的两个抽象类，
> 分别用于读取和写入字节流数据，主要区别如下：
> - InputStream:
>   - 用于从各种输入源读取字节流数据，如文件、网络、内存等。
>   - 提供了读取单个字节、字节数组或指定长度字节的方法，如read()、read(byte[] b)等。
>   - 常见的实现类包括FileInputStream、ByteArrayInputStream、BufferedInputStream、FilterInputStream、PipedInputStream等。
> 
> - OutputStream:
>   - 用于向各种目标输出字节流数据，如文件、网络、内存等。
>   - 提供了写入单个字节、字节数组或指定长度字节的方法，如write()、write(byte[] b)等。
>   - 常见的实现类包括FileOutputStream、ByteArrayOutputStream、BufferedOutputStream、FilterOutputStream、PipedOutputStream等。
> 

##### FileInputStream 单元测试案例

```java
import java.io.FileInputStream;
import java.io.IOException;

private static void readFile(String filePath) throws IOException {
    try (FileInputStream fis = new FileInputStream(filePath)) {
        int data;
        StringBuilder content = new StringBuilder();
        while ((data = fis.read()) != -1) {
            content.append((char) data);
        }

        System.out.println("读取到的内容:" + content);
    }
}
```

##### FileOutputStream 单元测试案例

```java
import java.io.FileOutputStream;
import java.io.IOException;

private static void writeFile(String filePath) throws IOException {

    // --向文件写入内容
    String contentToWrite = "Hello, World!";

    // --写入文件
    try (FileOutputStream fos = new FileOutputStream(filePath)) {
        fos.write(contentToWrite.getBytes());
    }
}
```

##### ByteArrayInputStream 单元测试案例
```java
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
     * 读取字节数组
     *
     * @throws IOException
 */
private static void readContent() throws IOException {
    byte[] bytes = "Hello, World!".getBytes();
    try (ByteArrayInputStream bais = new ByteArrayInputStream(bytes)) {
        int data;
        StringBuilder content = new StringBuilder();
        while ((data = bais.read()) != -1) {
            content.append((char) data);
        }

        System.out.println("读取到的内容:" + content);
    }
}
```

##### ByteArrayOutputStream 单元测试案例
```java
import java.io.ByteArrayOutputStream;
import java.io.IOException; 
/**
 * 写入字节数组
 *
 * @throws IOException
 */
private static void writeContent() throws IOException {
    byte[] bytes = "Hello, World!".getBytes();
    System.out.println("准备写入字节数组, 长度:" + bytes.length);
    try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
        baos.write(bytes); // 将字节数组写入到输出流中

        System.out.println("字节数组已写入输出流中!");
        // 获取字节数组
        byte[] outputBytes = baos.toByteArray();
        System.out.println("从输出流中获取字节数组，长度:" + outputBytes.length);
        // 输出字节数组内容
        readContent(outputBytes);
    }
}
```

##### BufferedInputStream 单元测试案例

> - BufferedInputStream:
>  - 内部缓冲区: BufferedInputStream 内部维护一个字节数组作为缓冲区。
>  - 读取数据: 当调用 read() 方法时，BufferedInputStream 会先检查缓冲区是否有数据。如果有，则直接从缓冲区读取；如果没有，则从底层输入流读取大量数据到缓冲区，然后再从缓冲区读取。
>  - 减少读取次数: 通过这种方式，减少了对底层输入流的读取次数，提高了读取效率。

```java
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
/**
 * 读取文件
 *
 * @param filePath
 * @throws IOException
 */
private static void readFile(String filePath) throws IOException {
        try (FileInputStream fis = new FileInputStream(filePath);
             BufferedInputStream bis = new BufferedInputStream(fis)) {
            int data;
            StringBuilder content = new StringBuilder();
            while ((data = bis.read()) != -1) {
                content.append((char) data);
            }
            System.out.println("读取到的内容:" + content);
        }
    }
```

##### BufferedOutputStream 单元测试案例

> - BufferedOutputStream:
>  - **内部缓冲区**: BufferedOutputStream 内部也维护一个字节数组作为缓冲区。
>  - **写入数据**: 当调用 write() 方法时，数据首先被写入缓冲区。当缓冲区满时，数据会被批量写入底层输出流。
>  - **减少写入次数**: 通过这种方式，减少了对底层输出流的写入次数，提高了写入效率。
>  - **flush() 方法**: 当需要确保所有数据都被写入底层输出流时，可以调用 flush() 方法，将缓冲区中的剩余数据强制写入底层输出流。

```java
 import java.io.*; 
/**
 * 写入文件
 *
 * @param filePath
 */
private static void writeFile(String filePath) throws IOException {
    String contentToWrite = "Hello, BufferedOutputStream!";

    try (FileOutputStream fos = new FileOutputStream(filePath);
         BufferedOutputStream bos = new BufferedOutputStream(fos)) {
        bos.write(contentToWrite.getBytes());
        bos.flush(); // 确保所有数据都被写入文件中
    }

    System.out.println("文件写入成功!");
    try (FileInputStream fis = new FileInputStream(filePath);
         BufferedInputStream bis = new BufferedInputStream(fis)) {
        int data;
        StringBuilder content = new StringBuilder();
        while ((data = bis.read()) != -1) {
            content.append((char) data);
        }

        System.out.println("读取到的内容:" + content);
        boolean compareContent = content.toString().equals(contentToWrite);
        System.out.println("内容是否相同:" + compareContent);
    }
}
```

##### 缓冲机制
> 缓冲机制在IO操作中扮演着重要的角色，特别是在处理大量数据时。通过使用缓冲，可以显著提高读取和写入操作的效率。
> - **1、缓冲区（Buffer）**:
>    - 缓冲区是一个临时存储区域，通常是一个字节数组。
>    - 数据在读取或写入时，首先被放入或从缓冲区中取出，而不是直接与外部设备（如磁盘、网络）交互。
> - **2、提高效率**:
>    - 减少I/O操作次数: 直接与外部设备交互的I/O操作通常比较耗时。通过缓冲，可以减少这些操作的次数。
>    - 批量处理数据: 缓冲机制允许一次性读取或写入大量数据，而不是逐字节处理，从而提高效率。
> - **3、减少延迟**:
>    - 缓冲区可以减少由于频繁的I/O操作导致的延迟，使得程序运行更加流畅。

#### 1.2 字符流

> 字符流（Character Streams）用于处理字符数据，而不是字节数据。字符流以字符为单位进行数据交换，通常用于处理文本数据。字符流的主要抽象类是 Reader 和 Writer。

##### 1.2.1 常用的Reader实现类
> - FileReader:
>   - 从文件中读取字符数据。
>   - 实际上是 InputStreamReader 的一个方便子类，使用默认字符编码。
> - BufferedReader:
>   - 提供缓冲功能，可以提高读取效率。
>   - 常与 FileReader 或 InputStreamReader 结合使用。
>   - 提供 readLine() 方法，方便逐行读取文本。
> - InputStreamReader:
>   - 将字节流转化为字符流。
>   - 可以指定字符编码。
> - CharArrayReader:
>   - 从字符数组读取数据。
> - StringReader:
>   - 从字符串中读取数据。
> - PipedReader:
>   - 用于线程间通信，与 PipedWriter 配合使用。
> 

##### 1.2.2 常用的Writer实现类
> - FileWriter:
>   - 向文件中写入字符数据。
>   - 实际上是 OutputStreamWriter 的一个方便子类，使用默认字符编码。
> 
> - BufferedWriter:
>   - 提供缓冲功能，可以提高写入效率。
>   - 常与 FileWriter 或 OutputStreamWriter 结合使用。
>   - 提供 newLine()方法，方便写入换行符。
> 
> - OutputStreamWriter:
>   - 将字符流转化为字节流
>   - 可以指定字符编码。
> 
> - CharArrayWriter:
>   - 向字符数组写入数据。
> 
> - StringWriter:
>   - 向字符串写入数据。
> 
> - PipedWriter:
>   - 用于线程间通信，与 PipedReader 配合使用。
>

###### FileReadWrite 单元测试案例
```java
private static void testFileReadWrite(String filePath) throws IOException {
    // 写入内容
    String contentToWrite = "Hello, FileWriter and FileReader!";

    // 使用 FileWriter 写入文件
    try (FileWriter writer = new FileWriter(filePath)) {
        writer.write(contentToWrite);
    }

    // 使用 FileReader 读取文件
    StringBuilder contentRead = new StringBuilder();
    try (FileReader fileReader = new FileReader(filePath)) {
        int data;
        while ((data = fileReader.read()) != -1) {
            contentRead.append((char) data);
        }
    }

    System.out.println("读取到的内容:" + contentRead);

}
```
###### BufferedReaderWriter 单元测试案例
```java
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException; 
/**
 * 读取文件内容
 *
 * @param filePath
 * @throws IOException
 */
private static void read(String filePath) throws IOException {
    try (FileReader fr = new FileReader(filePath);
         BufferedReader br = new BufferedReader(fr);
    ) {
        String line;
        StringBuilder contentRead = new StringBuilder();
        int count = 0;
        while ((line = br.readLine()) != null) {
            contentRead.append(line);
            count++;
        }
        System.out.println("读取到的内容:" + contentRead + " ,总共读取了" + count + "次");
    }
}
```
```java
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException; 
/**
 * 写入文件
 *
 * @param filePath
 * @throws IOException
 */
private static void write(String filePath) throws IOException {
    String contentToWrite = "Hello, BufferedWriter!\nThis is a test.";
    try (FileWriter fw = new FileWriter(filePath);
         BufferedWriter bw = new BufferedWriter(fw);
    ) {
        bw.write(contentToWrite);
        bw.newLine(); // 写入换行符
        bw.write("Another line of text.");
    }
}
```

###### InputStreamReader 单元测试案例
> InputStreamReader 是一个字节流到字符流的桥梁，它将字节流转换为字符流。
> 它属于字符流（Character Streams）的一部分，通常用于将字节输入流（如 FileInputStream）转换为字符输入流（如 Reader），以便以字符形式读取数据。
> **<br>主要功能</br>**
>    - 字节到字符的转换：
>       - InputStreamReader 将字节流中的字节数据转换为字符数据，使用指定的字符编码。
>    - 字符编码支持：
>        - 可以指定字符编码（如 UTF-8、GBK 等），如果不指定，则使用默认的系统字符编码。
>    - 提高读取效率：
>       - 通过缓冲机制，可以提高读取效率。

```java
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader; 
/**
 * 读取文件
 *
 * @param filePath
 * @throws IOException
 */
private static void input(String filePath) throws IOException {
    try (FileInputStream fis = new FileInputStream(filePath);
         InputStreamReader isr = new InputStreamReader(fis);) {
        int data;
        int count = 0;
        StringBuilder content = new StringBuilder();
        while ((data = isr.read()) != -1) {
            // 按字符读取
            content.append((char) data);
            count++;
        }
        System.out.println("读取到的内容:" + content + ", 读取次数:" + count + "次!");
    }
}
```
```java
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader; 
/**
 * 结合 BufferedReader 使用
 *
 * @param filePath
 * @throws IOException
 */
private static void inputBufferedReader(String filePath) throws IOException {
    try (FileInputStream fis = new FileInputStream(filePath);
         InputStreamReader isr = new InputStreamReader(fis);
         BufferedReader br = new BufferedReader(isr);
    ) {

        String data;
        int count = 0;
        StringBuilder content = new StringBuilder();
        while ((data = br.readLine()) != null) {
            content.append(data);
            count++;
        }
        System.out.println("读取到的内容:" + content + ", 读取次数:" + count + "次!");
    }
}
```

###### OutputStreamWriter 单元测试案例
> OutputStreamWriter 是 Java 中用于将字符流转换为字节流的桥梁类。
> 它属于字符流（Character Streams）的一部分，通常用于将字符输出流（如 Writer）转换为字节输出流（如 OutputStream），
> 以便以字节形式写入数据。
> <br>主要功能</br>
> - 字符到字节的转换：
>   - OutputStreamWriter 将字符流中的字符数据转换为字节数据，使用指定的字符编码。
> - 字符编码支持：
>   - 可以指定字符编码（如 UTF-8、GBK 等），如果不指定，则使用默认的系统字符编码。
> - 提高写入效率：
>   - 通过缓冲机制，可以提高写入效率。

```java
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
/**
 * 写入文件
 *
 * @param filePath
 * @throws IOException
 */
private static void output(String filePath) throws IOException {
    String contentToWrite = "Hello, OutputStreamWriter!";
    try (FileOutputStream fos = new FileOutputStream(filePath);
         OutputStreamWriter osw = new OutputStreamWriter(fos)) {
        osw.write(contentToWrite);
        osw.flush();
    }
}
```
```java
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
/**
 * 结合 BufferedWriter 使用
 *
 * @param filePath
 * @throws IOException
 */
private static void outputWithBuffered(String filePath) throws IOException {
    String contentToWrite = "Hello, BufferedWriter!\nThis is a test.";
    try (FileOutputStream fos = new FileOutputStream(filePath);
         OutputStreamWriter osw = new OutputStreamWriter(fos);
         BufferedWriter bw = new BufferedWriter(osw)) {

        bw.write(contentToWrite);

        bw.newLine(); // 写入换行符
        bw.write("Another line of text.");
    }
}
```

##### 1.2.3 小总结

> - 如果需要从文件中读取文本数据，可以使用 FileReader 或 BufferedReader；
> - 如果需要将文本数据写入文件，可以使用 FileWriter 或 BufferedWriter。
