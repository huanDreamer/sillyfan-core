package top.sillyfan.dao.mybatis;

import java.sql.Types;

import org.joda.time.DateTime;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.internal.types.JavaTypeResolverDefaultImpl;

public class MyJavaTypeResolverImpl extends JavaTypeResolverDefaultImpl {

	
	public MyJavaTypeResolverImpl(){
		super();
		
        typeMap.put(Types.DATE, new JdbcTypeInformation("DATE", //$NON-NLS-1$
                new FullyQualifiedJavaType(DateTime.class.getName())));
        typeMap.put(Types.TIME, new JdbcTypeInformation("TIME", //$NON-NLS-1$
                new FullyQualifiedJavaType(DateTime.class.getName())));
        typeMap.put(Types.TIMESTAMP, new JdbcTypeInformation("TIMESTAMP", //$NON-NLS-1$
                new FullyQualifiedJavaType(DateTime.class.getName())));
        typeMap.put(Types.TINYINT, new JdbcTypeInformation("SMALLINT", //$NON-NLS-1$
                new FullyQualifiedJavaType(Short.class.getName())));


	}
	
}
