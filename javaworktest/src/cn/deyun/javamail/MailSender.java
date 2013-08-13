package cn.deyun.javamail;

import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailSender {
	private String host;
	private String username;
	private String password;
	
	/**
	 * @param host			邮箱服务器
	 * @param username		邮箱用户全名。例如：test@qq.com
	 * @param password		邮箱用户密码
	 */
	public MailSender(String host,String username,String password) {
		this.host = host;
		this.username = username;
		this.password = password;
	}
	
	/**
	 * 发送邮件
	 * @param mailTo			收件人email地址
	 * @param mailSubject		邮件标题
	 * @param mailBody			邮件正文
	 * @throws Exception
	 */
	public void send(String mailTo,String mailSubject,String mailBody) throws Exception{
		this.send(mailTo, mailSubject, mailBody,null);
	}
	
	public void send(String mailTo,String mailSubject,String mailBody,String persionName) throws Exception{
		try {
			Properties props = new Properties();
			Authenticator auth = new MyAuthenticator(username,password); 
			//进行邮件服务器用户认证
			props.put("mail.smtp.host", host);
			props.put("mail.smtp.auth", true);
			Session session = Session.getDefaultInstance(props,auth);
			//设置session,和邮件服务器进行通讯
			MimeMessage message = new MimeMessage(session);
			//message.setContent("foobar, "application/x-foobar"); // 设置邮件格式
			message.setSubject(mailSubject);     //设置邮件主题
			message.setText(mailBody);           //设置邮件正文
			message.setSentDate(new Date());     //设置邮件发送日期
			Address address = new InternetAddress(username, persionName);
			message.setFrom(address);            //设置邮件发送者的地址
			//设置邮件接收方的地址
			Address toAddress = new InternetAddress(mailTo);
			message.addRecipient(Message.RecipientType.TO, toAddress);
			Transport.send(message);
		} catch (Exception e) {
			e.printStackTrace();
			//throw new Exception(e.getMessage());
		}
	}
}
