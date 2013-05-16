package app;

import java.io.File;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

@Root
public class DbConn {
	
	@Element
	private String url;
	
	@Element
	private String user;
	
	@Element
	private String password;
	
	@Element
	private String driver;
	
	@Element
	private String documentBase;
	
	public String getDocumentBase() {
		return documentBase;
	}

	public void setDocumentBase(String documentBase) {
		this.documentBase = documentBase;
	}

	@Override
	public String toString() {
		return "URL:" + url + "\nUSER:" + user + "\nPASSWORD:" + password + "\nDRIVER:" + driver;
	}
	
	public void serialize(String file){
		try {
			Serializer serializer = new Persister();
			serializer.write(this, new File(file));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deSerialize(String file){
		try {
			Serializer serializer = new Persister();
			DbConn newValue = serializer.read(DbConn.class, new File(file));
			this.url = newValue.url;
			this.user = newValue.user;
			this.password = newValue.password;
			this.driver = newValue.driver;
			this.documentBase = newValue.documentBase;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}
		
}
