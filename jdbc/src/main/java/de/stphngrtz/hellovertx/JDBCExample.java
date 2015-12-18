package de.stphngrtz.hellovertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.Launcher;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.ResultSet;
import io.vertx.ext.sql.SQLConnection;

/**
 * https://github.com/vert-x3/vertx-examples/tree/master/jdbc-examples#vertx-jdbc-client-examples
 */
public class JDBCExample extends AbstractVerticle {

    /**
     * Convenience method so you can run it in your IDE
     */
    public static void main(String[] args) {
        Launcher.executeCommand("run", JDBCExample.class.getName());
    }

    @Override
    public void start() throws Exception {
        JDBCClient client = JDBCClient.createShared(vertx, new JsonObject()
                .put("url", "jdbc:hsqldb:mem:test?shutdown=true")
                .put("driver_class", "org.hsqldb.jdbcDriver")
                .put("max_pool_size", 30)
        );

        client.getConnection(conn -> {
            if (conn.failed())
                throw new RuntimeException(conn.cause());

            SQLConnection connection = conn.result();
            execute(connection, "create table test(id int primary key, name varchar(255))", create -> {
                execute(connection, "insert into test values(1, 'hello'), (2, 'world')", insert -> {
                    query(connection, "select * from test", select -> {
                        select.getResults().forEach(result -> System.out.println("first select " + result.encode()));

                        queryWithParams(connection, "select * from test where id = ?", new JsonArray().add(2), selectWithParam -> {
                            selectWithParam.getResults().forEach(result -> System.out.println("select with param " + result.encode()));

                            withoutAutoCommit(connection, ac -> {
                                execute(connection, "insert into test values(3, 'foo'), (4, 'bar')", insertWithoutAutoCommit -> {
                                    query(connection, "select count(*) from test", selectCountPreCommit -> {
                                        selectCountPreCommit.getResults().forEach(result -> System.out.println("count pre commit " + result.encode()));

                                        commit(connection, commit -> {
                                            query(connection, "select count(*) from test", selectCountPostCommit -> {
                                                selectCountPostCommit.getResults().forEach(result -> System.out.println("count post commit " + result.encode()));

                                                execute(connection, "insert into test values(5, 'oh no!')", insertStillWithoutAutoCommit -> {
                                                    rollback(connection, rollback -> {

                                                        query(connection, "select count(*) from test", selectCountPostRollback -> {
                                                            selectCountPostRollback.getResults().forEach(result -> System.out.println("count post rollback " + result.encode()));

                                                            connection.close(close -> {
                                                                if (close.failed())
                                                                    throw new RuntimeException(close.cause());
                                                            });
                                                        });
                                                    });
                                                });
                                            });
                                        });
                                    });
                                });
                            });
                        });
                    });
                });
            });
        });
    }

    private static void execute(SQLConnection connection, String sql, Handler<Void> done) {
        connection.execute(sql, execute -> {
            if (execute.failed())
                throw new RuntimeException(execute.cause());

            done.handle(null);
        });
    }

    private static void query(SQLConnection connection, String sql, Handler<ResultSet> done) {
        connection.query(sql, query -> {
            if (query.failed())
                throw new RuntimeException(query.cause());

            done.handle(query.result());
        });
    }

    private static void queryWithParams(SQLConnection connection, String sql, JsonArray params, Handler<ResultSet> done) {
        connection.queryWithParams(sql, params, query -> {
            if (query.failed())
                throw new RuntimeException(query.cause());

            done.handle(query.result());
        });
    }

    private static void withoutAutoCommit(SQLConnection connection, Handler<Void> done) {
        connection.setAutoCommit(false, ac -> {
            if (ac.failed())
                throw new RuntimeException(ac.cause());

            done.handle(null);
        });
    }

    private static void commit(SQLConnection connection, Handler<Void> done) {
        connection.commit(commit -> {
            if (commit.failed())
                throw new RuntimeException(commit.cause());

            done.handle(null);
        });
    }

    private static void rollback(SQLConnection connection, Handler<Void> done) {
        connection.rollback(rollback -> {
            if (rollback.failed())
                throw new RuntimeException(rollback.cause());

            done.handle(null);
        });
    }
}
