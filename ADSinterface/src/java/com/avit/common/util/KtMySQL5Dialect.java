package com.avit.common.util;

import java.sql.Types;

import org.hibernate.Hibernate;
import org.hibernate.dialect.MySQL5Dialect;

public class KtMySQL5Dialect extends MySQL5Dialect {
	 
	public KtMySQL5Dialect() {
		super();
		this.registerHibernateType(Types.LONGVARCHAR, Hibernate.STRING.getName());
	}
}
