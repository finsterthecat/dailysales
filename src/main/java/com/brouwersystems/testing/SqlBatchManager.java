/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.brouwersystems.testing;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author tonybrouwer
 */
@Stateless
public class SqlBatchManager {
    
    @Inject
    public TestingUtilities testingUtilities;
    
    private static final Logger LOG = LoggerFactory.getLogger(SqlBatchManager.class);
    List<String> sqlCommands;
    
    public SqlBatchManager() {
        
    }
    
    public List<String> loadSqlCommands(String commandFileName) {
        sqlCommands = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(commandFileName))) {
            sqlCommands = new ArrayList<>();
            String sql = br.readLine();
            while (sql != null) {
                if (StringUtils.isNotEmpty(sql)) {
                    sqlCommands.add(sql);
                }
                sql = br.readLine();
            }
        } catch (FileNotFoundException ex) {
            LOG.error("File /tmp/dailysalesdb/data.sql not found", ex);
        } catch (IOException ex) {
            LOG.error("IO error accessing file " + commandFileName, ex);
        }
        
        for (String sql: sqlCommands) {
            System.out.println("SQL: " + sql);
        }
        return sqlCommands;
    }
    
    public void executeSqlCommands() {
        if (sqlCommands == null) {
            throw new IllegalStateException("Forgot to load sql commands before executing them");
        }
        
        testingUtilities.runSqlCommands(sqlCommands);
    }
}
