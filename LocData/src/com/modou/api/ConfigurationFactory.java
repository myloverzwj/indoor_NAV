package com.modou.api;

import com.modou.utils.MLog;


/**
 * 信息生产类
 * @author changqiang.li
 * 初始化服务配置信息
 */
public class ConfigurationFactory {
	private static Configuration config;
	public static Configuration getInstance() {
		if (config != null) return config;
		config = new Configuration();
		init();
		return config;
	}
	
	private static void init() {
		config.setSignCode("S-haqvbms6");
		String code = config.getSignCode();
		String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALH0xudlo58a4nl81KOFaMy8j6cfQDI+q05OUWAubv2jobxl4NnU9GCpvbcKfpYjssiBrfuhKeLiETRGwcSmA5kIyxT/NjEg0ZEO6fFWjmJK3ACLOmMElJNiUGw0ICoyVdyBzxl/RedTKTohtMY+D2ty4e32SmWClSZsCe9m9nsNAgMBAAECgYEAjqjGE5a9dr0ctXvHE1fNzeOT3cwFsMHgnqPWRJ60x4y4Cco6WSaIyj0JI4W8OSxhFLAxA7oNVB8a36ehir3zvw1hml4cN5cAZntpaYGMYgaA3EA1TDW+mA2ujpY2ZzpG6sWCdPnOphdgVKyuEGmEpdtAzQ0jus75CGW9YDPmdRkCQQD+v9bfMM+AfKCvigE3OkGl1vHVzLYz6Dax5ZBppi33xPDrNB1+rhRonFiiVjyu5Cv7cAWGWDGH9SLlJgmWns93AkEAstRtKsvnpdkCmORm2FMgNiSR9pt8XbQoyL+fQkpc0jZhjaw2Nbn2VyF1NlgNJwZz3iDVyPdC2NG8zSK54quSmwJBAPetwNWC+Vvsz7WbsY7mfwkkMEA0JjnVXcgccAmn3i11Nt8W7k5KJeGHkM1Ulu9bPD/cLCLwAEg1V9X+43ejtAUCQF7zNUds3oELeTqOwyCG+mpk/m8u3VivaJw2Siwbaa9fmmprjpj2NRrMM/z3wXwADBNb4ccDqiWPLyzIDGSS7pMCQADvjmrDPY4Hcr15QPWOxgTIlhOM9Raa2da+nSQiZueI5uGYLcvmtYZMD5UeZszK16dMEEE8P1O50RjOqROIaUo=";
		try {
			privateKey = RSACoder.sign(code.getBytes(), privateKey);
		} catch (Exception e) {
			e.printStackTrace();
		}
		config.setPrivatekey(privateKey);
		config.setShopId("2659");
		
		config.setSupportSSL(false);
		config.setSnum("渠道号添加");
		
		
		if (MLog.DEBUG) {
			config.setHostName("112.124.21.184");
			config.setHttpPort(8081);
			config.setRootPath("/showapi/open");
		} else {
			config.setHostName("www.showmi.com.cn/open");
			config.setHttpPort(8080);
			config.setRootPath("/showapi");
		}
		
	}
}
