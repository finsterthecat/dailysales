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
import javax.inject.Inject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author tonybrouwer
 */
public class SqlBatchManager {
    
    private static final Logger LOG = LoggerFactory.getLogger(SqlBatchManager.class);
    
    public SqlBatchManager() {
        
    }
    
    public static List<String> loadSqlCommands(String commandFileName) {
        if (commandFileName == null) {
            LOG.error("Missing filename");
            throw new IllegalArgumentException("Missing filename");
        }
        
        List<String> sqlCommands = new ArrayList<>();
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
            LOG.error("File " + commandFileName + " not found", ex);
        } catch (IOException ex) {
            LOG.error("IO error accessing file " + commandFileName, ex);
        }
        
        for (String sql: sqlCommands) {
            System.out.println("SQL: " + sql);
        }
        return sqlCommands;
    }
    
}
