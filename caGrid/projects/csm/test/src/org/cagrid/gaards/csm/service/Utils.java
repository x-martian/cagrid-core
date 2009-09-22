package org.cagrid.gaards.csm.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.cagrid.tools.database.Database;
import org.springframework.core.io.AbstractResource;
import org.springframework.core.io.ClassPathResource;

public class Utils {

	public static final String CSM_CONFIGURATION = "csm-configuration.xml";
	public static final String CSM_PROPERTIES = "csm.properties";
	public static final String DB_SCHEMA = "test/resources/TestAuthSchemaMySQL.sql";

	public static BeanUtils getBeanUtils() throws Exception {
		ClassPathResource properties = new ClassPathResource(CSM_PROPERTIES);
		return getBeanUtils(properties);
	}

	public static BeanUtils getBeanUtils(AbstractResource properties)
			throws Exception {
		ClassPathResource cpr = new ClassPathResource(CSM_CONFIGURATION);
		return new BeanUtils(cpr, properties);
	}

	public static CSMProperties getCSMProperties() throws Exception {
		return getBeanUtils().getCSMProperties();
	}

	public static void initializeDatabase() throws Exception {
		CSMProperties props = getCSMProperties();
		DatabaseProperties dbprops = props.getDatabaseProperties();
		String url = dbprops.getConnectionURL();
		int beginHost = url.indexOf("//");
		int beginPort = url.indexOf(":", beginHost);
		int beginDB = url.indexOf("/", beginPort);
		String host = url.substring(beginHost + 2, beginPort);
		String port = url.substring(beginPort + 1, beginDB);
		String dbName = url.substring(beginDB + 1);

		Database db = new Database(host, Integer.valueOf(port).intValue(), dbprops.getUserId(), dbprops
				.getPassword(), dbName);
		db.destroyDatabase();
		db.createDatabaseIfNeeded();

		String csmApplication = "CREATE TABLE CSM_APPLICATION ("
				+ "APPLICATION_ID BIGINT AUTO_INCREMENT  NOT NULL,"
				+ "APPLICATION_NAME VARCHAR(255) NOT NULL,"
				+ "APPLICATION_DESCRIPTION VARCHAR(200) NOT NULL,"
				+ "DECLARATIVE_FLAG BOOL NOT NULL DEFAULT 0,"
				+ "ACTIVE_FLAG BOOL NOT NULL DEFAULT 0,"
				+ "UPDATE_DATE DATE DEFAULT '0000-00-00',"
				+ "DATABASE_URL VARCHAR(100),"
				+ "DATABASE_USER_NAME VARCHAR(100),"
				+ "DATABASE_PASSWORD VARCHAR(100),"
				+ "DATABASE_DIALECT VARCHAR(100),"
				+ "DATABASE_DRIVER VARCHAR(100),"
				+ "PRIMARY KEY(APPLICATION_ID)" + ")Type=InnoDB;";

		db.update(csmApplication);

		String csmGroup = "CREATE TABLE CSM_GROUP ("
				+ "GROUP_ID BIGINT AUTO_INCREMENT  NOT NULL,"
				+ "GROUP_NAME VARCHAR(255) NOT NULL,"
				+ "GROUP_DESC VARCHAR(200),"
				+ "UPDATE_DATE DATE NOT NULL DEFAULT '0000-00-00',"
				+ "APPLICATION_ID BIGINT NOT NULL," + "PRIMARY KEY(GROUP_ID)"
				+ ")Type=InnoDB;";

		db.update(csmGroup);

		String csmPrivilege = "CREATE TABLE CSM_PRIVILEGE ("
				+ "PRIVILEGE_ID BIGINT AUTO_INCREMENT  NOT NULL,"
				+ "PRIVILEGE_NAME VARCHAR(100) NOT NULL,"
				+ "PRIVILEGE_DESCRIPTION VARCHAR(200),"
				+ "UPDATE_DATE DATE NOT NULL DEFAULT '0000-00-00',"
				+ "PRIMARY KEY(PRIVILEGE_ID)" + ")Type=InnoDB;";

		db.update(csmPrivilege);

		String filterClause = "CREATE TABLE CSM_FILTER_CLAUSE ("
				+ "FILTER_CLAUSE_ID BIGINT AUTO_INCREMENT  NOT NULL,"
				+ "CLASS_NAME VARCHAR(100) NOT NULL,"
				+ "FILTER_CHAIN VARCHAR(2000) NOT NULL,"
				+ "TARGET_CLASS_NAME VARCHAR (100) NOT NULL,"
				+ "TARGET_CLASS_ATTRIBUTE_NAME VARCHAR (100) NOT NULL,"
				+ "TARGET_CLASS_ATTRIBUTE_TYPE VARCHAR (100) NOT NULL,"
				+ "TARGET_CLASS_ALIAS VARCHAR (100),"
				+ "TARGET_CLASS_ATTRIBUTE_ALIAS VARCHAR (100),"
				+ "GENERATED_SQL_USER VARCHAR (4000) NOT NULL,"
				+ "GENERATED_SQL_GROUP VARCHAR (4000) NOT NULL,"
				+ "APPLICATION_ID BIGINT NOT NULL,"
				+ "UPDATE_DATE DATE NOT NULL DEFAULT '0000-00-00',"
				+ "PRIMARY KEY(FILTER_CLAUSE_ID)	" + ")Type=InnoDB;";

		db.update(filterClause);

		String protectionElement = "CREATE TABLE CSM_PROTECTION_ELEMENT ("
				+ "PROTECTION_ELEMENT_ID BIGINT AUTO_INCREMENT  NOT NULL,"
				+ "PROTECTION_ELEMENT_NAME VARCHAR(100) NOT NULL,"
				+ "PROTECTION_ELEMENT_DESCRIPTION VARCHAR(200),"
				+ "OBJECT_ID VARCHAR(100) NOT NULL,"
				+ "ATTRIBUTE VARCHAR(100)," + "ATTRIBUTE_VALUE VARCHAR(100) ,"
				+ "PROTECTION_ELEMENT_TYPE VARCHAR(100),"
				+ "APPLICATION_ID BIGINT NOT NULL,"
				+ "UPDATE_DATE DATE NOT NULL DEFAULT '0000-00-00',"
				+ "PRIMARY KEY(PROTECTION_ELEMENT_ID)" + ")Type=InnoDB;";

		db.update(protectionElement);

		String protectionGroup = "CREATE TABLE CSM_PROTECTION_GROUP ("
				+ "PROTECTION_GROUP_ID BIGINT AUTO_INCREMENT  NOT NULL,"
				+ "PROTECTION_GROUP_NAME VARCHAR(100) NOT NULL,"
				+ "PROTECTION_GROUP_DESCRIPTION VARCHAR(200),"
				+ "APPLICATION_ID BIGINT NOT NULL,"
				+ "LARGE_ELEMENT_COUNT_FLAG BOOL NOT NULL,"
				+ "UPDATE_DATE DATE NOT NULL DEFAULT '0000-00-00',"
				+ "PARENT_PROTECTION_GROUP_ID BIGINT,"
				+ "PRIMARY KEY(PROTECTION_GROUP_ID)" + ")Type=InnoDB;";

		db.update(protectionGroup);

		String pgtope = "CREATE TABLE CSM_PG_PE ("
				+ "PG_PE_ID BIGINT AUTO_INCREMENT  NOT NULL,"
				+ "PROTECTION_GROUP_ID BIGINT NOT NULL,"
				+ "PROTECTION_ELEMENT_ID BIGINT NOT NULL,"
				+ "UPDATE_DATE DATE DEFAULT '0000-00-00',"
				+ "PRIMARY KEY(PG_PE_ID)" + ")Type=InnoDB;";

		db.update(pgtope);

		String role = "CREATE TABLE CSM_ROLE ("
				+ "ROLE_ID BIGINT AUTO_INCREMENT  NOT NULL,"
				+ "ROLE_NAME VARCHAR(100) NOT NULL,"
				+ "ROLE_DESCRIPTION VARCHAR(200),"
				+ "APPLICATION_ID BIGINT NOT NULL,"
				+ "ACTIVE_FLAG BOOL NOT NULL,"
				+ "UPDATE_DATE DATE NOT NULL DEFAULT '0000-00-00',"
				+ "PRIMARY KEY(ROLE_ID)" + ")Type=InnoDB;";

		db.update(role);

		String rp = "CREATE TABLE CSM_ROLE_PRIVILEGE ("
				+ "ROLE_PRIVILEGE_ID BIGINT AUTO_INCREMENT  NOT NULL,"
				+ "ROLE_ID BIGINT NOT NULL," + "PRIVILEGE_ID BIGINT NOT NULL,"
				+ "PRIMARY KEY(ROLE_PRIVILEGE_ID)" + ")Type=InnoDB;";

		db.update(rp);

		String user = "CREATE TABLE CSM_USER ("
				+ "USER_ID BIGINT AUTO_INCREMENT  NOT NULL,"
				+ "LOGIN_NAME VARCHAR(500) NOT NULL,"
				+ "MIGRATED_FLAG BOOL NOT NULL DEFAULT 0,"
				+ "FIRST_NAME VARCHAR(100) NOT NULL,"
				+ "LAST_NAME VARCHAR(100) NOT NULL,"
				+ "ORGANIZATION VARCHAR(100)," + "DEPARTMENT VARCHAR(100),"
				+ "TITLE VARCHAR(100)," + "PHONE_NUMBER VARCHAR(15),"
				+ "PASSWORD VARCHAR(100)," + "EMAIL_ID VARCHAR(100),"
				+ "START_DATE DATE," + "END_DATE DATE,"
				+ "UPDATE_DATE DATE NOT NULL DEFAULT '0000-00-00',"
				+ "PREMGRT_LOGIN_NAME VARCHAR(100)," + "PRIMARY KEY(USER_ID)"
				+ ")Type=InnoDB;";

		db.update(user);

		String ug = "CREATE TABLE CSM_USER_GROUP ("
				+ "USER_GROUP_ID BIGINT AUTO_INCREMENT  NOT NULL,"
				+ "USER_ID BIGINT NOT NULL," + "GROUP_ID BIGINT NOT NULL,"
				+ "PRIMARY KEY(USER_GROUP_ID)" + ")Type=InnoDB;";

		db.update(ug);

		String ugrp = "CREATE TABLE CSM_USER_GROUP_ROLE_PG ("
				+ "USER_GROUP_ROLE_PG_ID BIGINT AUTO_INCREMENT  NOT NULL,"
				+ "USER_ID BIGINT," + "GROUP_ID BIGINT,"
				+ "ROLE_ID BIGINT NOT NULL,"
				+ "PROTECTION_GROUP_ID BIGINT NOT NULL,"
				+ "UPDATE_DATE DATE NOT NULL DEFAULT '0000-00-00',"
				+ "PRIMARY KEY(USER_GROUP_ROLE_PG_ID)" + ")Type=InnoDB;";

		db.update(ugrp);

		String cup = "CREATE TABLE CSM_USER_PE ("
				+ "USER_PROTECTION_ELEMENT_ID BIGINT AUTO_INCREMENT  NOT NULL,"
				+ "PROTECTION_ELEMENT_ID BIGINT NOT NULL,"
				+ "USER_ID BIGINT NOT NULL,"
				+ "PRIMARY KEY(USER_PROTECTION_ELEMENT_ID)" + ")Type=InnoDB;";
		db.update(cup);

		db
				.update("ALTER TABLE CSM_APPLICATION ADD CONSTRAINT UQ_APPLICATION_NAME UNIQUE (APPLICATION_NAME);");
		db
				.update("CREATE INDEX idx_APPLICATION_ID ON CSM_GROUP(APPLICATION_ID);");
		db
				.update("ALTER TABLE CSM_GROUP ADD CONSTRAINT UQ_GROUP_GROUP_NAME UNIQUE (APPLICATION_ID, GROUP_NAME);");
		db
				.update("ALTER TABLE CSM_PRIVILEGE ADD CONSTRAINT UQ_PRIVILEGE_NAME UNIQUE (PRIVILEGE_NAME);");
		db
				.update("CREATE INDEX idx_APPLICATION_ID ON CSM_PROTECTION_ELEMENT(APPLICATION_ID);");
		db
				.update("ALTER TABLE CSM_PROTECTION_ELEMENT ADD CONSTRAINT UQ_PE_PE_NAME_ATTRIBUTE_VALUE_APP_ID UNIQUE (OBJECT_ID, ATTRIBUTE, ATTRIBUTE_VALUE, APPLICATION_ID);");
		db
				.update("CREATE INDEX idx_APPLICATION_ID ON CSM_PROTECTION_GROUP(APPLICATION_ID);");
		db
				.update("ALTER TABLE CSM_PROTECTION_GROUP ADD CONSTRAINT UQ_PROTECTION_GROUP_PROTECTION_GROUP_NAME UNIQUE (APPLICATION_ID, PROTECTION_GROUP_NAME);");
		db
				.update("CREATE INDEX idx_PARENT_PROTECTION_GROUP_ID ON CSM_PROTECTION_GROUP(PARENT_PROTECTION_GROUP_ID);");
		db
				.update("CREATE INDEX idx_PROTECTION_ELEMENT_ID ON CSM_PG_PE(PROTECTION_ELEMENT_ID);");
		db
				.update("CREATE INDEX idx_PROTECTION_GROUP_ID ON CSM_PG_PE(PROTECTION_GROUP_ID);");
		db
				.update("ALTER TABLE CSM_PG_PE ADD CONSTRAINT UQ_PROTECTION_GROUP_PROTECTION_ELEMENT_PROTECTION_GROUP_ID UNIQUE (PROTECTION_ELEMENT_ID, PROTECTION_GROUP_ID);");
		db
				.update("CREATE INDEX idx_APPLICATION_ID ON CSM_ROLE(APPLICATION_ID);");
		db
				.update("ALTER TABLE CSM_ROLE ADD CONSTRAINT UQ_ROLE_ROLE_NAME UNIQUE (APPLICATION_ID, ROLE_NAME);");
		db
				.update("CREATE INDEX idx_PRIVILEGE_ID ON CSM_ROLE_PRIVILEGE(PRIVILEGE_ID);");
		db
				.update("ALTER TABLE CSM_ROLE_PRIVILEGE ADD CONSTRAINT UQ_ROLE_PRIVILEGE_ROLE_ID UNIQUE (PRIVILEGE_ID, ROLE_ID);");
		db.update("CREATE INDEX idx_ROLE_ID ON CSM_ROLE_PRIVILEGE(ROLE_ID);");
		db
				.update("ALTER TABLE CSM_USER ADD CONSTRAINT UQ_LOGIN_NAME UNIQUE (LOGIN_NAME);");
		db.update("CREATE INDEX idx_USER_ID ON CSM_USER_GROUP(USER_ID);");
		db.update("CREATE INDEX idx_GROUP_ID ON CSM_USER_GROUP(GROUP_ID);");
		db
				.update("CREATE INDEX idx_GROUP_ID ON CSM_USER_GROUP_ROLE_PG(GROUP_ID);");
		db
				.update("CREATE INDEX idx_ROLE_ID ON CSM_USER_GROUP_ROLE_PG(ROLE_ID);");
		db
				.update("CREATE INDEX idx_PROTECTION_GROUP_ID ON CSM_USER_GROUP_ROLE_PG(PROTECTION_GROUP_ID);");
		db
				.update("CREATE INDEX idx_USER_ID ON CSM_USER_GROUP_ROLE_PG(USER_ID);");
		db.update("CREATE INDEX idx_USER_ID ON CSM_USER_PE(USER_ID);");
		db
				.update("CREATE INDEX idx_PROTECTION_ELEMENT_ID ON CSM_USER_PE(PROTECTION_ELEMENT_ID);");
		db
				.update("ALTER TABLE CSM_USER_PE ADD CONSTRAINT UQ_USER_PROTECTION_ELEMENT_PROTECTION_ELEMENT_ID UNIQUE (USER_ID, PROTECTION_ELEMENT_ID);");
		db
				.update("ALTER TABLE CSM_GROUP ADD CONSTRAINT FK_APPLICATION_GROUP FOREIGN KEY (APPLICATION_ID) REFERENCES CSM_APPLICATION (APPLICATION_ID) ON DELETE CASCADE;");
		db
				.update("ALTER TABLE CSM_FILTER_CLAUSE ADD CONSTRAINT FK_APPLICATION_FILTER_CLAUSE FOREIGN KEY (APPLICATION_ID) REFERENCES CSM_APPLICATION (APPLICATION_ID) ON DELETE CASCADE;");
		db
				.update("ALTER TABLE CSM_PROTECTION_ELEMENT ADD CONSTRAINT FK_PE_APPLICATION FOREIGN KEY (APPLICATION_ID) REFERENCES CSM_APPLICATION (APPLICATION_ID) ON DELETE CASCADE;");
		db
				.update("ALTER TABLE CSM_PROTECTION_GROUP ADD CONSTRAINT FK_PG_APPLICATION FOREIGN KEY (APPLICATION_ID) REFERENCES CSM_APPLICATION (APPLICATION_ID) ON DELETE CASCADE;");
		db
				.update("ALTER TABLE CSM_PROTECTION_GROUP ADD CONSTRAINT FK_PROTECTION_GROUP FOREIGN KEY (PARENT_PROTECTION_GROUP_ID) REFERENCES CSM_PROTECTION_GROUP (PROTECTION_GROUP_ID);");
		db
				.update("ALTER TABLE CSM_PG_PE ADD CONSTRAINT FK_PROTECTION_ELEMENT_PROTECTION_GROUP FOREIGN KEY (PROTECTION_ELEMENT_ID) REFERENCES CSM_PROTECTION_ELEMENT (PROTECTION_ELEMENT_ID) ON DELETE CASCADE;");
		db
				.update("ALTER TABLE CSM_PG_PE ADD CONSTRAINT FK_PROTECTION_GROUP_PROTECTION_ELEMENT FOREIGN KEY (PROTECTION_GROUP_ID) REFERENCES CSM_PROTECTION_GROUP (PROTECTION_GROUP_ID) ON DELETE CASCADE;");
		db
				.update("ALTER TABLE CSM_ROLE ADD CONSTRAINT FK_APPLICATION_ROLE FOREIGN KEY (APPLICATION_ID) REFERENCES CSM_APPLICATION (APPLICATION_ID) ON DELETE CASCADE;");
		db
				.update("ALTER TABLE CSM_ROLE_PRIVILEGE ADD CONSTRAINT FK_PRIVILEGE_ROLE FOREIGN KEY (PRIVILEGE_ID) REFERENCES CSM_PRIVILEGE (PRIVILEGE_ID) ON DELETE CASCADE;");
		db
				.update("ALTER TABLE CSM_ROLE_PRIVILEGE ADD CONSTRAINT FK_ROLE FOREIGN KEY (ROLE_ID) REFERENCES CSM_ROLE (ROLE_ID) ON DELETE CASCADE;");
		db
				.update("ALTER TABLE CSM_USER_GROUP ADD CONSTRAINT FK_USER_GROUP FOREIGN KEY (USER_ID) REFERENCES CSM_USER (USER_ID) ON DELETE CASCADE;");
		db
				.update("ALTER TABLE CSM_USER_GROUP ADD CONSTRAINT FK_UG_GROUP FOREIGN KEY (GROUP_ID) REFERENCES CSM_GROUP (GROUP_ID) ON DELETE CASCADE;");
		db
				.update("ALTER TABLE CSM_USER_GROUP_ROLE_PG ADD CONSTRAINT FK_USER_GROUP_ROLE_PROTECTION_GROUP_GROUPS FOREIGN KEY (GROUP_ID) REFERENCES CSM_GROUP (GROUP_ID) ON DELETE CASCADE;");
		db
				.update("ALTER TABLE CSM_USER_GROUP_ROLE_PG ADD CONSTRAINT FK_USER_GROUP_ROLE_PROTECTION_GROUP_ROLE FOREIGN KEY (ROLE_ID) REFERENCES CSM_ROLE (ROLE_ID) ON DELETE CASCADE;");
		db
				.update("ALTER TABLE CSM_USER_GROUP_ROLE_PG ADD CONSTRAINT FK_USER_GROUP_ROLE_PROTECTION_GROUP_PROTECTION_GROUP FOREIGN KEY (PROTECTION_GROUP_ID) REFERENCES CSM_PROTECTION_GROUP (PROTECTION_GROUP_ID) ON DELETE CASCADE;");
		db
				.update("ALTER TABLE CSM_USER_GROUP_ROLE_PG ADD CONSTRAINT FK_USER_GROUP_ROLE_PROTECTION_GROUP_USER FOREIGN KEY (USER_ID) REFERENCES CSM_USER (USER_ID) ON DELETE CASCADE;");
		db
				.update("ALTER TABLE CSM_USER_PE ADD CONSTRAINT FK_PE_USER FOREIGN KEY (USER_ID) REFERENCES CSM_USER (USER_ID) ON DELETE CASCADE;");
		db
				.update("ALTER TABLE CSM_USER_PE ADD CONSTRAINT FK_PROTECTION_ELEMENT_USER FOREIGN KEY (PROTECTION_ELEMENT_ID) REFERENCES CSM_PROTECTION_ELEMENT (PROTECTION_ELEMENT_ID) ON DELETE CASCADE;");
	}
}
