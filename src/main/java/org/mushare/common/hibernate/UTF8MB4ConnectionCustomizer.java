package org.mushare.common.hibernate;

import com.mchange.v2.c3p0.AbstractConnectionCustomizer;

import java.sql.Connection;
import java.sql.Statement;

public class UTF8MB4ConnectionCustomizer extends AbstractConnectionCustomizer {

    @Override
    public void onAcquire(Connection c, String parentDataSourceIdentityToken)
            throws Exception {
        super.onAcquire(c, parentDataSourceIdentityToken);
        try (Statement statement = c.createStatement()) {
            statement.executeQuery("SET NAMES utf8mb4");
        }
    }

}
