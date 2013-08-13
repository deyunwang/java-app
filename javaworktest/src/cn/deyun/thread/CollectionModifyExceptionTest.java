package cn.deyun.thread;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

	/**
	 * 经典:Collection的一个异常修改
	 * 思考:为什么会抛出:java.util.ConcurrentModificationException
	 * 这个问题是说,你不能在对一个List进行遍历的时候将其中的元素删除掉
	 * 解决办法是,你可以先将要删除的元素用另一个list装起来,等遍历结束再remove掉
	 * 可以这样写
	 * List delList = new ArrayList();//用来装需要删除的元素
	 * for(Information ia:list)
	 * 		if(ia.getId()==k){
	 * 		n++;
	 * 		delList.add(ia);
	 * }
	 * list.removeAll(delList);//遍历完成后执行删除
	 * 下面是网上的其他解释，更能从本质上解释原因：
	 * Iterator 是工作在一个独立的线程中，并且拥有一个 mutex 锁。 Iterator 被创建之后会建立一个指向原来对象的单链索引表
	 * 当原来的对象数量发生变化时，这个索引表的内容不会同步改变，所以当索引指针往后移动的时候就找不到要迭代的对象
	 * 所以按照 fail-fast 原则 Iterator 会马上抛出 java.util.ConcurrentModificationException 异常。
	 * 答案：看java源代码
	 * 在遍历collection和set时，不要remove其中的元素
	 * 如果remove的话，先iterate一下，再使用iterrate的remove(）方法
	 * 踢人的例子
	 * @author Kervin
	 *
	 */
public class CollectionModifyExceptionTest {

	public static void main(String[] args) {
		//1.下面这种写法会抛出异常:java.util.ConcurrentModificationException
		//Collection<User> users = new ArrayList<User>();
		//2.CopyOnWriteArrayList为jdk1.5提供的新方法
		//ArrayList 的一个线程安全的变体，其中所有可变操作（add、set 等等）都是通过对底层数组进行一次新的复制来实现的
		Collection<User> users =new CopyOnWriteArrayList<User>();
		users.add(new User("张三",28));
		users.add(new User("李四",25));
		users.add(new User("王五",31));
		Iterator<User> itrUsers = users.iterator();
		
		while(itrUsers.hasNext()){
			User user = (User) itrUsers.next();
			if("张三".equals(user.getName())){
				users.remove(user);
			}else{
				System.out.println(user);
			}
		}
	}
}
