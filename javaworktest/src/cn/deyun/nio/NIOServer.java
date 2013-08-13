package cn.deyun.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;


/**
 * NIO是Java New io的简称。在JDK1.4里面提供的新IO操作
 * 1、Buffer - 为所有的原始类型提供缓存支持。
 * 2、Charset - 字符集编码解决方案。
 * 3、Channel – 一个新的数据操作对像，可以理解成管道流。它即可以保存数据，又可以读取数据。
 * 4、Selector - 提供多路（non-blocking）非阻塞式的高伸缩性网络IO。
 * Buffer:是一块连续的内存块。是NIO读或写数据的中转地。－注意：此地即可以读，又写可以写。
 * Channel:1.数据的源头（即in）或数据的目的地（即out）。-同时扮演两个角色。
 * 		   2.用于向buffer提供数据，即向buffer写入数据。或是从buffer中读取数据。	
 * 		   3.对异步IO的支持	selector
 * 缓冲区通过从两个方面来提高IO操作的效率：
 * 减少实际物理的读写次数。
 * 缓冲区在创建时被分配内存，这块内存区域一直被重用，这可以减少动态分配的回收内存区域的次数。
 * 所有缓冲区都具有以下属性：
 * Capacity – 容量。表示缓冲区可以保存多少数据。
 * Limit – 极限。目前所保存的数据容量。此值可以修改，应该小于容量，极限是一个非负整数。
 * position－位置。表示缓冲区下一个读写单元的位置，每次读取缓冲区的数据时，此值都会发生变化。
 * Clear – 将极限设置为容量值，再将位置设置为0.
 * Flip – 将极限设置为位置值，再将位置设置为0.
 * Rewind – 不修改极限，将位置设置为0.
 * Allocate(int size) – 返回一个buffer对象，参数指定了缓冲区的容量。
 * Get() – 相对读：从缓冲区的当前位置读取一个单元的数据，读完后，将位置加1.
 * Get(int idx) – 绝对读：从参数idx指定的位置读取一个单元的数据。
 * Put()－相对写。
 * Put(int idx) –绝对写。
 * @author kervin
 *
 */
public class NIOServer {
	private int flag = 0;
	private int BLOCK = 4096;
	private  ByteBuffer sendbuffer = ByteBuffer.allocate(BLOCK); 
	private  ByteBuffer receivebuffer = ByteBuffer.allocate(BLOCK); 
	private  Selector selector;
	public NIOServer(int port) throws IOException{
		// 打开服务器套接字通道  
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open(); 
		// 服务器配置为非阻塞 
		serverSocketChannel.configureBlocking(false);  
		// 检索与此通道关联的服务器套接字  
		ServerSocket serverSocket = serverSocketChannel.socket();  
		// 进行服务的绑定 
		serverSocket.bind(new InetSocketAddress(port)); 
		// 通过open()方法找到Selector  
		selector = Selector.open(); 
		// 注册到selector，等待连接 
		serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
		System.out.println("Server Start----8888:");  
	}

	// 监听
	private void listen() throws IOException {
		while(true){
			// 选择一组键，并且相应的通道已经打开
			selector.select();
			// 返回此选择器的已选择键集。 
			Set<SelectionKey> selectionKeys = selector.selectedKeys();
			Iterator<SelectionKey> iterator = selectionKeys.iterator();  
			while (iterator.hasNext()) {    
				SelectionKey selectionKey = iterator.next();
				iterator.remove();
				handleKey(selectionKey);
			}
		}
	}
	// 处理请求 
	private void handleKey(SelectionKey selectionKey) throws IOException {
		// 接受请求  
		ServerSocketChannel server = null;  
		SocketChannel client = null;  
		String receiveText;  
		String sendText;
		int count=0;
		// 测试此键的通道是否已准备好接受新的套接字连接。  
		if (selectionKey.isAcceptable()) {
			// 返回为之创建此键的通道。 
			server = (ServerSocketChannel) selectionKey.channel();
			// 接受到此通道套接字的连接。
			// 此方法返回的套接字通道（如果有）将处于阻塞模式。 
			client = server.accept();
			// 配置为非阻塞  
			client.configureBlocking(false);
			// 注册到selector，等待连接
			client.register(selector, SelectionKey.OP_READ);
		}else if(selectionKey.isReadable()){
			// 返回为之创建此键的通道。
			client = (SocketChannel) selectionKey.channel();  
			//将缓冲区清空以备下次读取  
			receivebuffer.clear();  
			//读取服务器发送来的数据到缓冲区中
			count = client.read(receivebuffer);
			if (count > 0) { 
				receiveText = new String( receivebuffer.array(),0,count);  
				System.out.println("服务器端接受客户端数据--:"+receiveText); 
				client.register(selector, SelectionKey.OP_WRITE);  
			}
		}else if(selectionKey.isWritable()){
			//将缓冲区清空以备下次写入  
			sendbuffer.clear();
			// 返回为之创建此键的通道。  
			client = (SocketChannel) selectionKey.channel(); 
			sendText="message from server--" + flag++; 
			//向缓冲区中输入数据 
			sendbuffer.put(sendText.getBytes());  
			//将缓冲区各标志复位,因为向里面put了数据标志被改变要想从中读取数据发向服务器,就要复位
			sendbuffer.flip();
			//输出到通道 
			client.write(sendbuffer); 
			System.out.println("服务器端向客户端发送数据--："+sendText);
			client.register(selector, SelectionKey.OP_READ);  
		}
	}
	public static void main(String[] args) throws IOException {
		int port = 8888;
		NIOServer server = new NIOServer(port);  
		server.listen(); 
	}
}
