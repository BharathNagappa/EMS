package com.ems.dataobject;

import java.util.List;

public class PrivilagesDO {

Long id;
String moduleName;
String path;
boolean readable;
boolean writeable;
List<UserRolesDO> roles;

public Long getId() {
	return id;
}
public void setId(Long id) {
	this.id = id;
}
public String getModuleName() {
	return moduleName;
}
public void setModuleName(String moduleName) {
	this.moduleName = moduleName;
}
public String getPath() {
	return path;
}
public void setPath(String path) {
	this.path = path;
}
public boolean isReadable() {
	return readable;
}
public void setReadable(boolean readable) {
	this.readable = readable;
}
public boolean isWriteable() {
	return writeable;
}
public void setWriteable(boolean writeable) {
	this.writeable = writeable;
}



}
