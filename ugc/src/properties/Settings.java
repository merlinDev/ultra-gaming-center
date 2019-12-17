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
package properties;

import com.cyclotech.repository.CRUDOperationsImpl;
import gamefx.Log;
import gamefx.Utility;
import java.sql.ResultSet;

/**
 *
 * @author Roshana Pitigala
 */
public class Settings {

    public static final String ADDRESS = "ADDRESS",
            TELEPHONE = "TELE",
            EMAIL_ADDRESS = "EMAIL",
            CONTACT_EMAIL_ADDRESS = "EMAILCONTACT",
            EMAIL_PASSWORD = "EMAILPASS";
    private final String AESPASS = new Utility().MD5("CYCLOTECH_UGC");

    /**
     * Get value of the given setting.
     *
     * @param variable Setting.
     * @return Value.
     */
    public String getValue(String variable) {
        // <editor-fold defaultstate="collapsed" desc="getValue">
        String value = null;
        CRUDOperationsImpl db = new CRUDOperationsImpl();
        try {
            ResultSet rs;
            rs = db.search("CAST(AES_DECRYPT(`value`, '" + AESPASS + "')AS CHAR) AS `value`", "settings", "WHERE variable='" + variable + "'");
            if (rs.first()) {
                value = rs.getString("value");
            }
        } catch (Exception ex) {
            Log.error(ex);
        }
        return value;
        //</editor-fold>
    }

    /**
     * Set value of the given setting.
     *
     * @param variable setting.
     * @param value value.
     */
    public void setValue(String variable, String value) {
        // <editor-fold defaultstate="collapsed" desc="setValue">
        CRUDOperationsImpl db = new CRUDOperationsImpl();
        try {
            ResultSet rs = db.search("'OK'", "settings", "WHERE variable='" + variable + "'");
            if (rs.first()) {
                db.update("settings", "`value` = AES_ENCRYPT('" + value + "', '" + AESPASS + "')", "variable='" + variable + "'");
            } else {
                db.save("settings (variable, `value`)", "'" + variable + "', AES_ENCRYPT('" + value + "', '" + AESPASS + "')");
            }
        } catch (Exception ex) {
            Log.error(ex);
        }
        // </editor-fold>
    }
}
