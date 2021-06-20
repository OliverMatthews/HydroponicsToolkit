public enum DatabaseType {
    PROD(
            "hydro_tk",
            "./src/main/resources/sql/ddl_header.sql",
            "./src/main/resources/sql/ddl.sql"
    ),

    TEST(
            "hydro_tk_test",
            "./src/test/resources/sql/ddl_header.sql",
            "./src/test/resources/sql/ddl.sql"
    ),

    DEFAULT(
            "postgres",
            null,
            null
    );

    private final String databaseName;
    private final String headerFilepath;
    private final String ddlFilepath;

    DatabaseType(final String databaseName, final String headerFilepath, final String ddlFilepath) {
        this.databaseName = databaseName;
        this.headerFilepath = headerFilepath;
        this.ddlFilepath = ddlFilepath;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public String getHeaderFilepath() {
        return headerFilepath;
    }

    public String getDdlFilepath() {
        return ddlFilepath;
    }
}
