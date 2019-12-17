/*
 *	Java Institute For Advanced Technology
 *	Final Project
 *	
 *	*************************************
 *
 *	The MIT License
 *
 *	Copyright © 2017 CYCLOTECH.
 *
 *	Permission is hereby granted, free of charge, to any person obtaining a copy
 *	of this software and associated documentation files (the "Software"), to deal
 *	in the Software without restriction, including without limitation the rights
 *	to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *	copies of the Software, and to permit persons to whom the Software is
 *	furnished to do so, subject to the following conditions:
 *
 *	The above copyright notice and this permission notice shall be included in
 *	all copies or substantial portions of the Software.
 *
 *	THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *	IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *	FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *	AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *	LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM
 *	OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *	THE SOFTWARE.
 */
package com.cyclotech.repository;

import java.io.IOException;
import java.net.ProtocolException;
import java.net.URISyntaxException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 * Only for development purposes.
 *
 * @author Roshana Pitigala
 */
public class CRUDOperationsImpl implements CRUDOperations {

    public CRUDOperationsImpl() {
    }

    @Override
    public void delete(String table, String idColumn) throws SQLException, ClassNotFoundException, SQLIntegrityConstraintViolationException, URISyntaxException, ProtocolException, IOException {
        DB.getConnection().createStatement().executeUpdate("DELETE FROM " + table + " WHERE " + idColumn);
    }

    @Override
    public String getLastId(String column, String tableName) throws SQLException, ClassNotFoundException, URISyntaxException, ProtocolException, IOException {
        ResultSet rs = DB.getConnection().createStatement().executeQuery("SELECT MAX(" + column + ") AS id FROM " + tableName);
        rs.first();
        return rs.getString("id");
    }

    @Override
    public void save(String tableName, String values) throws SQLException, ClassNotFoundException, URISyntaxException, ProtocolException, IOException {
        System.out.println("INSERT INTO " + tableName + " VALUES(" + values + ")");
        DB.getConnection().createStatement().executeUpdate("INSERT INTO " + tableName + " VALUES(" + values + ")");
    }

    @Override
    public ResultSet search(String columns, String tables, String condition) throws ClassNotFoundException, SQLException, URISyntaxException, ProtocolException, IOException {
        System.out.println("SELECT " + columns + " FROM " + tables + " " + condition);
        return DB.getConnection().createStatement().executeQuery("SELECT " + columns + " FROM " + tables + " " + condition);
    }

    @Override
    public void update(String tableName, String columns, String idColumn) throws SQLException, ClassNotFoundException, URISyntaxException, ProtocolException, IOException {
        System.out.println("UPDATE " + tableName + " SET " + columns + " WHERE " + idColumn);
        DB.getConnection().createStatement().executeUpdate("UPDATE " + tableName + " SET " + columns + " WHERE " + idColumn);
    }
}
