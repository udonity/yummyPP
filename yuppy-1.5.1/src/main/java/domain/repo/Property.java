package domain.repo;

import domain.obj.Injection;

public interface Property {

	public String getProperty(Injection inje);

	public void setProperty(Injection inje, String value);

	public void store();
}
