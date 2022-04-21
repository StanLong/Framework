package com.stanlong.service.impl;

import com.stanlong.dao.AccountDao;
import com.stanlong.service.AccountService;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

public class AccountServiceImpl implements AccountService {

	private AccountDao accountDao;

	// 注入DAO
	public void setAccountDao(AccountDao accountDao) {
		this.accountDao = accountDao;
	}
	
	//注入Spring事务模板
	private TransactionTemplate transactionTemplate;
	
	public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
		this.transactionTemplate = transactionTemplate;
	}
	
	@Override
	public void transfer(String outer, String inner, Integer money) {
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				accountDao.out(outer, money);
				//模拟断电
				int i = 1/0;
				accountDao.in(inner, money);
			}
		});


	}

}
