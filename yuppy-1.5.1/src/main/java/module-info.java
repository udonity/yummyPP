module yuppymodule {
	exports app;

	requires transitive com.fasterxml.jackson.annotation;
	requires transitive com.fasterxml.jackson.core;
	requires transitive com.fasterxml.jackson.databind;
	requires transitive javafx.base;
	requires transitive javafx.controls;
	requires transitive javafx.fxml;
	requires transitive javafx.graphics;
	requires transitive lombok;
	requires transitive org.jsoup;
	requires transitive org.slf4j;

	
	opens app;
	opens app.ctr;
	opens domain.obj.json;
	opens domain.obj;
	opens domain.service;
	opens domain.service.net;
	opens app.resource;
}