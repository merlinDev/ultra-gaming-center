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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ProtocolException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Only for development purposes.
 *
 * @author Roshana Pitigala
 */
public class DB {

    private static Connection c;
    private static final String ipAddress = "127.0.0.1";

    private DB() {
    }

    public static synchronized Connection getConnection() throws ClassNotFoundException, FileNotFoundException, URISyntaxException, ProtocolException, IOException, SQLException {
        if (c == null) {
            Class.forName("com.mysql.jdbc.Driver");
            c = DriverManager.getConnection("jdbc:mysql://" + getIpAddress() + ":3306/gamecenter", "root", "1234");
        }
        return c;
    }

    public static String getIpAddress() {
        return ipAddress;
    }
}
