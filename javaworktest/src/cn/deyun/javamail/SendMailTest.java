package cn.deyun.javamail;

public class SendMailTest {
	public static void main(String[] args) {
		String host = "smtp.qq.com";						//邮箱服务器
		String username = "344429461@qq.com";				//你的邮箱用户名
		String password = "deyunwang@126.com";				//你的邮箱密码	
		MailSender sendmail = new MailSender(host, username, password);
		try {
			String mailTo = "344429461@qq.com";				//发送的目标邮箱
			String mailSubject = "JavaMail发送邮件测试";		//邮箱的主题
			String mailBody = "hello world!";				//邮箱的内容
			sendmail.send(mailTo, mailSubject, mailBody);	//发送邮箱
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
