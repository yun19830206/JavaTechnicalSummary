List去重的5种方式

1.使用LinkedHashSet删除arraylist中的重复数据
LinkedHashSet是在一个ArrayList删除重复数据的最佳方法。LinkedHashSet在内部完成两件事：

删除重复数据
保持添加到其中的数据的顺序

Java示例使用LinkedHashSet删除arraylist中的重复项。在给定的示例中，numbersList是包含整数的arraylist，其中一些是重复的数字。

例如1,3和5.我们将列表添加到LinkedHashSet，然后将内容返回到列表中。结果arraylist没有重复的整数。
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
 
public class ArrayListExample
 
{
    public static void main(String[] args)
 
    {
 
        ArrayList<Integer> numbersList = new ArrayList<>(Arrays.asList(1, 1, 2, 3, 3, 3, 4, 5, 6, 6, 6, 7, 8));
 
        System.out.println(numbersList);
 
        LinkedHashSet<Integer> hashSet = new LinkedHashSet<>(numbersList);
 
        ArrayList<Integer> listWithoutDuplicates = new ArrayList<>(hashSet);
 
        System.out.println(listWithoutDuplicates);
 
    }
 
}
输出结果
[1, 1, 2, 3, 3, 3, 4, 5, 6, 6, 6, 7, 8]
 
[1, 2, 3, 4, 5, 6, 7, 8]
2.使用java8新特性stream进行List去重
要从arraylist中删除重复项，我们也可以使用java 8 stream api。使用steam的distinct()方法返回一个由不同数据组成的流，通过对象的equals（）方法进行比较。
收集所有区域数据List使用Collectors.toList()。
Java程序，用于在不使用Set的情况下从java中的arraylist中删除重复项。
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
 
public class ArrayListExample
{
    public static void main(String[] args)
 
    {
 
        ArrayList<Integer> numbersList = new ArrayList<>(Arrays.asList(1, 1, 2, 3, 3, 3, 4, 5, 6, 6, 6, 7, 8));
        System.out.println(numbersList);
        List<Integer> listWithoutDuplicates = numbersList.stream().distinct().collect(Collectors.toList());
 
        System.out.println(listWithoutDuplicates);
 
    }
 
}
输出结果
[1, 1, 2, 3, 3, 3, 4, 5, 6, 6, 6, 7, 8]
 
[1, 2, 3, 4, 5, 6, 7, 8]
3.利用HashSet不能添加重复数据的特性 由于HashSet不能保证添加顺序，所以只能作为判断条件保证顺序：
private static void removeDuplicate(List<String> list) {
    HashSet<String> set = new HashSet<String>(list.size());
    List<String> result = new ArrayList<String>(list.size());
    for (String str : list) {
        if (set.add(str)) {
            result.add(str);
        }
    }
    list.clear();
    list.addAll(result);
}
4.利用List的contains方法循环遍历,重新排序,只添加一次数据,避免重复：
private static void removeDuplicate(List<String> list) {
    List<String> result = new ArrayList<String>(list.size());
    for (String str : list) {
        if (!result.contains(str)) {
            result.add(str);
        }
    }
    list.clear();
    list.addAll(result);
}
5.双重for循环去重
for (int i = 0; i < list.size(); i++) { 
for (int j = 0; j < list.size(); j++) { 
if(i!=j&&list.get(i)==list.get(j)) { 
list.remove(list.get(j)); 
 } 
} 
