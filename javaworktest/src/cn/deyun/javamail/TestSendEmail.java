package cn.deyun.javamail;

public class TestSendEmail {
	public static void main(String[] args) {
		// 这个类主要是设置邮件
		MailSenderInfo mailInfo = new MailSenderInfo();
		mailInfo.setMailServerHost("smtp.163.com");     	 //邮箱服务器      
		mailInfo.setMailServerPort("25");				 	 //邮箱端口
		mailInfo.setValidate(true);						  	 //是否需要身份验证
		mailInfo.setUserName("wangdeyun303@163.com");	  	 //发件人
		mailInfo.setPassword("13926583639");			  	 //您的邮箱密码
		mailInfo.setFromAddress("wangdeyun303@163.com");  	 //发件人
		mailInfo.setToAddress("344429461@qq.com");			 //要发送的邮件地址                               
		mailInfo.setSubject("设置邮箱标题 如http://www.guihua.org 中国桂花网");               //发送邮件标题
		mailInfo.setContent("设置邮箱内容 如http://www.guihua.org 中国桂花网 是中国最大桂花网站=="); //要发送的内容
		// 这个类主要来发送邮件
		SimpleMailSender sms = new SimpleMailSender();
		sms.sendTextMail(mailInfo); // 发送文体格式
		sms.sendHtmlMail(mailInfo); // 发送html格式
	}
}
