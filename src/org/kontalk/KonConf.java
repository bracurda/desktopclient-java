/*
 *  Kontalk Java client
 *  Copyright (C) 2014 Kontalk Devteam <devteam@kontalk.org>
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.kontalk;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.kontalk.client.Client;

/**
 *
 * Global configuration options.
 *
 * @author Alexander Bikadorov <abiku@cs.tu-berlin.de>
 */
public final class KonConf extends PropertiesConfiguration {
    private final static Logger LOGGER = Logger.getLogger(KonConf.class.getName());

    private static KonConf INSTANCE = null;

    // All configuration option keys
    public final static String SERV_NET = "server.network";
    public final static String SERV_HOST = "server.host";
    public final static String SERV_PORT = "server.port";
    public final static String ACC_PASS = "account.passphrase";
    public final static String VIEW_FRAME_WIDTH = "view.frame.width";
    public final static String VIEW_FRAME_HEIGHT = "view.frame.height";
    public final static String VIEW_SELECTED_THREAD = "view.thread";
    public final static String NET_SEND_CHAT_STATE = "net.chatstate";
    public final static String NET_STATUS_LIST = "net.status_list";
    public final static String MAIN_CONNECT_STARTUP = "main.connect_startup";
    public final static String MAIN_TRAY = "main.tray";
    public final static String MAIN_TRAY_CLOSE = "main.tray_close";
    public final static String MAIN_ENTER_SENDS = "main.enter_sends";

    public final static String DEFAULT_SERV_NET = Client.KONTALK_NETWORK;
    public final static String DEFAULT_SERV_HOST = "prime.kontalk.net";
    public final static int DEFAULT_SERV_PORT = 5222;

    private KonConf() {
        super();

        String filePath = Kontalk.getConfigDir() + "/kontalk.properties";

        // separate list elements by tab character
        this.setListDelimiter((char) 9);

        try {
            this.load(filePath);
        } catch (ConfigurationException ex) {
            LOGGER.info("Configuration not found. Using default values");
        }

        this.setFileName(filePath);

        // init config
        Map<String, Object> map = new HashMap<>();
        map.put(SERV_NET, DEFAULT_SERV_NET);
        map.put(SERV_HOST, DEFAULT_SERV_HOST);
        map.put(SERV_PORT, DEFAULT_SERV_PORT);
        map.put(ACC_PASS, "");
        map.put(VIEW_FRAME_WIDTH, 600);
        map.put(VIEW_FRAME_HEIGHT, 650);
        map.put(VIEW_SELECTED_THREAD, -1);
        map.put(NET_SEND_CHAT_STATE, true);
        map.put(NET_STATUS_LIST, new String[]{""});
        map.put(MAIN_CONNECT_STARTUP, false);
        map.put(MAIN_TRAY, true);
        map.put(MAIN_TRAY_CLOSE, false);
        map.put(MAIN_ENTER_SENDS, true);

        for(Entry<String, Object> e : map.entrySet()) {
            if (!this.containsKey(e.getKey())) {
                this.setProperty(e.getKey(), e.getValue());
            }
        }
    }

    public void saveToFile() {
        try {
            this.save();
        } catch (ConfigurationException ex) {
            LOGGER.log(Level.WARNING, "can't save configuration", ex);
        }
    }

    public static KonConf getInstance() {
        if (INSTANCE == null)
            INSTANCE = new KonConf();
        return INSTANCE;
    }
}
