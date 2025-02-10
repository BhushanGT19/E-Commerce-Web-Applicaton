package com.bookcharm.app;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
import org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy;

public class CustomPhysicalNamingStrategy extends SpringPhysicalNamingStrategy {

    @Override
    public Identifier toPhysicalColumnName(Identifier name, JdbcEnvironment context) {
        return Identifier.toIdentifier(name.getText(), true); // Convert to lower camelCase
    }
}
