package com.allinfinance.system.util;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;  
import java.util.List;
import org.hibernate.cfg.Configuration;  
import org.hibernate.mapping.Column;  
import org.hibernate.mapping.PersistentClass;  
import org.hibernate.mapping.Property;  
import org.springframework.beans.BeansException;  
import org.springframework.context.ApplicationContext;  
import org.springframework.context.ApplicationContextAware;  
import org.springframework.orm.hibernate3.LocalSessionFactoryBean;
import com.allinfinance.po.TblTermKeyPK;
import com.allinfinance.po.mchnt.TblHisDiscAlgoPK;
import com.allinfinance.po.mchnt.TblHisDiscAlgo;
import com.allinfinance.po.mchnt.TblMchtBaseInf;
  
/** 
 * 根据实体类得到对应的表名、主键名、字段名（与Spring集成） 
 * 这里使用xml文件配置的映射，需要保证实体类名与对应映射文件名一致，即User.java与User.hbm.xml  
 * </p> 
 * 这里使用继承ApplicationContextAware的方式来获得ApplicationContext, 
 * 因此需要在Spring配置文件中配置一下该类，才能自动注入ApplicationContext对象 
 *  
 * <bean class="util.HibernateConfigurationUtil"/> 
 */  
public class HibernateConfigurationUtil implements ApplicationContextAware {  
  
    private static ApplicationContext applicationContext;  
  
    private static Configuration configuration;  
  
    public static Configuration getConfiguration() {  
  
        if (configuration == null) {   
            // 取sessionFactory的时候要加上&  
            LocalSessionFactoryBean factory = (LocalSessionFactoryBean) applicationContext  
                    .getBean("&sessionFactory");  
            configuration = factory.getConfiguration();  
        }  
//        configuration.addResource("hbm/pos/TblTermInf.hbm.xml");
        return configuration;  
    }  
  
    private static <T> PersistentClass getPersistentClass(Class<T> clazz) {  
        synchronized (HibernateConfigurationUtil.class) {  
            PersistentClass pc = getConfiguration().getClassMapping(  
                    clazz.getSimpleName());  
            if (pc == null) {  
//                configuration = configuration.addClass(clazz);  
                pc = configuration.getClassMapping(clazz.getName());  
            }  
            return pc;  
        }  
    }  
  
    /** 
     * 获得实体类对应的表名 
     *  
     * @param clazz 
     *            实体类的Class对象 
     * @return 表名 
     */  
    public static <T> String getTableName(Class<T> clazz) {  
        return getPersistentClass(clazz).getTable().getName();  
    }  
  
    /** 
     * 获得实体类对应表的主键字段名称 
     *  
     * @param clazz 
     *            实体类的Class对象 
     * @return 主键字段名称 
     */  
    public static <T> String getPKColumnName(Class<T> clazz) {  
        return getPersistentClass(clazz).getTable().getPrimaryKey()  
                .getColumn(0).getName();  
    }  
  
    /** 
     * 获得类属性对应的字段名 
     *  
     * @param clazz 
     *            实体类的Class对象 
     * @param propertyName 
     *            实体类的属性名 
     * @return 属性对应的字段名 
     */  
    public static <T> String getColumnName(Class<T> clazz, String propertyName) {  
        String columnName = "";  
        PersistentClass persistentClass = getPersistentClass(clazz);  
        Property property = persistentClass.getProperty(propertyName);  
        Iterator<?> iterator = property.getColumnIterator();  
        if (iterator.hasNext()) {  
            Column column = (Column) iterator.next();  
            columnName += column.getName();  
        }  
        return columnName;  
    }  
  
    @Override  
    public void setApplicationContext(ApplicationContext context)  
            throws BeansException {  
        applicationContext = context;  
    }  
    
    /** 
     * 利用反射+JDBC实现类似于Session.save(Object object)的操作，适用于主键为单一自增主键 
     * 这里只是提供一种实现思路，可以根据需要进行扩展以及实现类似的update、delete、get操作 
     *  
     * @param connection 数据库连接 
     * @param entity 要保存的实体类 
     * @throws Exception 
     */  
    public static <T> int save(Connection connection, T entity) throws Exception{  
        Class<? extends Object> clazz = entity.getClass();  
        Class orgClass = clazz;
        if(clazz==TblMchtBaseInf.class||clazz==TblHisDiscAlgo.class){
        	clazz = clazz.getSuperclass();
        }
        Field[] fieldsAll = clazz.getDeclaredFields();  
        Field[] fieldsPub = clazz.getFields();
        List<Field> flistAll = Arrays.asList(fieldsAll);
        List<Field> flistPub = Arrays.asList(fieldsPub);
        Field[] fields = new Field[fieldsAll.length - fieldsPub.length];
        int l = 0;
        for (Field f1:flistAll){
        	if (flistPub.contains(f1)) {
        		continue;
        	}
        	fields[l++] = f1;
        }
        
        StringBuilder builder = new StringBuilder();  
        builder.append("insert into ");  
        builder.append(HibernateConfigurationUtil.getTableName(orgClass));  
        builder.append(" (");  
  
        List<Field> useFields = new ArrayList<Field>();  
        Field field = null;  
  
        for (int i = 0; i < fields.length; i++) {  
            //设置字段可以被访问，如果不设置此选项就无法访问private字段  
            field = fields[i];  
            field.setAccessible(true);  
            // 过滤不需要的属性,  
//            if ("id".equals(field.getName())) {  
//                continue;  
//            }  
             try {
            	 String fieldName = field.getName();
            	 String fieldName1 = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            	 String columnNm = null;
            	 if (!(field.get(entity) instanceof TblTermKeyPK) && !(field.get(entity) instanceof TblHisDiscAlgoPK)){
            		 try{
	            		 columnNm = HibernateConfigurationUtil.getColumnName(orgClass, fieldName1);
	            	 } catch (Exception e){
	            		 columnNm = HibernateConfigurationUtil.getColumnName(orgClass, fieldName);
	            	 }
            	 } else {
            		 if (field.get(entity) instanceof TblTermKeyPK) {
            			 columnNm = "MCHT_CD,TERM_ID";
            		 } else {
            			 columnNm = "disc_Id,index_Num";
            		 }
            	 }
            	 builder.append(columnNm);  
            	 if (i < fields.length - 1)  
            		 builder.append(", ");  
            	 useFields.add(field); 
//            	 System.out.println(field.getName());
            	 
             }catch(Exception e){
            	 continue;
             }
        }  
        builder.append(") values (");  
        for (int i = 0; i < useFields.size(); i++) {  
        	field = useFields.get(i); 
        	if (!(field.get(entity) instanceof TblTermKeyPK) && !(field.get(entity) instanceof TblHisDiscAlgoPK)){
                builder.append(" ?"); 
        	} else {
    			builder.append(" ?, ?"); 
        	}
            if (i < useFields.size() - 1) {  
                builder.append(", ");  
            } else {  
                builder.append(" )");  
            }  
        }  
        PreparedStatement pstmt = connection.prepareStatement(builder.toString());  
        System.out.println(builder.toString());
        Class<?> clazz2 = null;  
        int i = 1;
        for (int index = 0; index < useFields.size(); index++) {  
            field = useFields.get(index);  
            // 根据字段的类型设置对应类型的值  
            clazz2 = field.getType();  
            if (clazz2 == String.class) {  
                pstmt.setString(i++, field.get(entity)==null?"":field.get(entity).toString());  
//                System.out.println(field.get(entity));
                continue;  
            }  
            if (clazz2 == Integer.class || clazz2 == int.class) {  
            	
                pstmt.setInt(i++, Integer.parseInt(field.get(entity)==null?"0":field.get(entity).toString()));  
//                System.out.println(field.get(entity));
                continue;  
            }  
            if (clazz2 == Double.class || clazz2 == double.class) {  
                pstmt.setDouble(i++, field.getDouble(entity));  
//                System.out.println(field.getDouble(entity));
                continue;  
            }  
            if (clazz2 == BigDecimal.class) {  
                pstmt.setBigDecimal(i++, (BigDecimal)field.get(entity));
//                System.out.println((BigDecimal)field.get(entity));
                continue;  
            }
            if (clazz2 == Date.class) {  
                Date date = (Date) field.get(entity);  
                pstmt.setTimestamp(i+1, new Timestamp(date.getTime()));  
//                System.out.println(new Timestamp(date.getTime()));
                continue;  
            }  
            if (clazz2 == TblTermKeyPK.class) {  
            	TblTermKeyPK pk = (TblTermKeyPK) field.get(entity);  
                pstmt.setString(i++, pk.getMchtCd());  
                pstmt.setString(i++, pk.getTermId()); 
//                System.out.println(pk.getMchtCd());
//                System.out.println(pk.getTermId());
                continue;  
            } 
            if (clazz2 == TblHisDiscAlgoPK.class) {  
            	TblHisDiscAlgoPK pk = (TblHisDiscAlgoPK) field.get(entity);  
                pstmt.setString(i++, pk.getDiscId());  
                pstmt.setInt(i++, pk.getIndexNum()); 
//                System.out.println(pk.getDiscId());
//                System.out.println(pk.getIndexNum());
                continue;  
            } 
        }  
        int iRet = pstmt.executeUpdate();
        if (pstmt != null) {
        	pstmt.close();
        }
        return iRet;  
    }  
} 
