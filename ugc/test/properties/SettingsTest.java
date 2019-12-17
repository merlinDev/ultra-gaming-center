/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package properties;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Roshana Pitigala
 */
public class SettingsTest {

    public SettingsTest() {
    }

    //@Test
    public void testSetValue() {
        System.out.println("setValue");
        String variable = "name";
        String value = "abc";
        new Settings().setValue(variable, value);
    }

    @Test
    public void testGetValue() {
        System.out.println("getValue");
        String variable = "name";
        String expResult = "abc";
        String result = new Settings().getValue(variable);
        assertEquals(expResult, result);
    }

}
