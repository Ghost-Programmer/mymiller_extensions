/*
  Copyright 2018 MyMiller Consulting LLC.
  <p>
  Licensed under the Apache License, Version 2.0 (the "License"); you may not
  use this file except in compliance with the License.  You may obtain a copy
  of the License at
  <p>
  http://www.apache.org/licenses/LICENSE-2.0
  <p>
  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
  WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
  License for the specific language governing permissions and limitations under
  the License.
 */
/*

 */
package name.mymiller.io.net;

import name.mymiller.io.Process;
import java.util.logging.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.logging.Level;

/**
 * @author jmiller
 *
 */
public class AdvancedInetAddress {
    /**
     * InetAddress this is used for.
     */
    private InetAddress address = null;

    /**
     * String for the Vendor name.
     */
    private String vendorName = null;

    /**
     * String representing the MAC Address
     */
    private String mac = null;

    /**
     * Construct the AdvancedInetAddress for the local host.
     */
    public AdvancedInetAddress() {
        try {
            this.address = InetAddress.getLocalHost();
        } catch (final UnknownHostException e) {
            Logger.getLogger(AdvancedInetAddress.class.getName()).log(Level.SEVERE, "Failed to get Local Host", e);
        }
    }

    /**
     * Advanced InetAddress that allows
     *
     * @param address InetAddress to handle
     */
    public AdvancedInetAddress(final InetAddress address) {
        this.address = address;
    }

    /**
     * Lookup host via name
     *
     * @param hostName Name of Host
     * @throws UnknownHostException Host not Found
     */
    public AdvancedInetAddress(final String hostName) throws UnknownHostException {
        this.address = InetAddress.getByName(hostName);
    }

    /**
     * Internal call to use ARP tables to look up a MAC Address, only works for IPs
     * on the same subnet.
     *
     * @param timeout the time in milliseconds before aborting the call
     * @return String contain the MAC Address.
     */
    private String arp(final int timeout) {
        try {
            this.isReachable(timeout);

            final Process arp = new Process();
            arp.setArgs(new String[]{"arp", "-a"});

            arp.start();

            arp.join();

            final String[] lines = arp.getOutput();

            if (lines != null) {
                for (final String line : lines) {
                    if (line.indexOf(this.address.getHostAddress()) > -1) {
                        return line;
                    }
                }
            }
        } catch (IOException | InterruptedException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "ARP Failed", e);
        }
        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof AdvancedInetAddress)) {
            return false;
        }
        final AdvancedInetAddress other = (AdvancedInetAddress) obj;
        if (this.address == null) {
            return other.address == null;
        } else return this.address.equals(other.address);
    }

    /**
     * @return Returns the raw IP address of this InetAddress object. The result is
     *         in network byte order: the highest order byte of the address is in
     *         getAddress()[0].
     * @see InetAddress#getAddress()
     */
    public byte[] getAddress() {
        return this.address.getAddress();
    }

    /**
     * @return the fully qualified domain name for this IP address, or if the
     *         operation is not allowed by the security check, the textual
     *         representation of the IP address.
     * @see InetAddress#getCanonicalHostName()
     */
    public String getCanonicalHostName() {
        return this.address.getCanonicalHostName();
    }

    /**
     * @return the raw IP address in a string format.
     * @see InetAddress#getHostAddress()
     */
    public String getHostAddress() {
        return this.address.getHostAddress();
    }

    /**
     * @return the host name for this IP address, or if the operation is not allowed
     *         by the security check, the textual representation of the IP address.
     * @see InetAddress#getHostName()
     */
    public String getHostName() {
        return this.address.getHostName();
    }

    /**
     * @return InetAddress this references
     */
    public InetAddress getInetAddress() {
        return this.address;
    }

    /**
     * Method to lookup the MAC Address
     *
     * @return String containing the MAC Address.
     */
    public String getMacAddress() {
        if (this.mac == null) {
            final String line = this.arp(50);

            if ((line == null) || (line.indexOf("--") > -1) || (line.indexOf("unknown host") > -1)) {
                try {
                    final Enumeration<NetworkInterface> networks = NetworkInterface.getNetworkInterfaces();

                    while (networks.hasMoreElements()) {
                        final NetworkInterface network = networks.nextElement();

                        final Enumeration<InetAddress> inetAddresses = network.getInetAddresses();

                        while (inetAddresses.hasMoreElements()) {
                            final InetAddress nextElement = inetAddresses.nextElement();

                            if (nextElement.equals(this.address)) {
                                final byte[] mac = network.getHardwareAddress();

                                if (mac.length > 5) {
                                    final StringBuilder sb = new StringBuilder();
                                    for (int i = 0; i < mac.length; i++) {
                                        sb.append(String.format("%02X%s", mac[i], (i < (mac.length - 1)) ? "-" : ""));
                                    }
                                    this.mac = sb.toString();
                                }
                                break;
                            }
                            if (this.mac != null) {
                                return this.mac;
                            }
                        }
                        if (this.mac != null) {
                            return this.mac;
                        }
                    }

                    return this.mac;
                } catch (final SocketException e) {
                }
            }

            int startIndex = -1;
            if (line.indexOf('-') > -1) {
                startIndex = line.indexOf('-') - 2;
            } else if (line.indexOf(':') > -1) {
                startIndex = line.indexOf(':') - 2;
            }

            int endIndex = line.length() - 1;
            if (line.indexOf(' ', startIndex + 1) > -1) {
                endIndex = line.indexOf(' ', startIndex + 1);
            }
            this.mac = line.substring(startIndex, endIndex);
        }

        return this.mac;
    }

    /**
     * Method to lookup the vendor of a MAC Address.
     *
     * @return String containing the vendor name.
     */
    public String getMacVendor() {
        if (this.vendorName == null) {
            this.vendorName = "";
            final String mac = this.getMacAddress();

            if ((mac != null) && (mac.length() > 8)) {
                this.vendorName = lookupMacVendor(mac);
            }
        }
        return this.vendorName;
    }

    /**
     * Method to lookup the vendor of a MAC Address
     * @param mac String containing the MAC Address
     * @return String containing the vendor name.
     */
    public static String lookupMacVendor(String mac) {
        String vendorName = null;
        if ((mac != null) && (mac.length() > 8)) {
            final String vendorID = mac.substring(0, 8).toUpperCase();

            Logger.getLogger(AdvancedInetAddress.class.getName()).log(Level.INFO, "Looking for Vendor ID: " + vendorID);

            try {
                final URL url = new URL("http://api.macvendors.com/" + vendorID);
                final BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));

                for (String line; (line = reader.readLine()) != null; ) {
                    vendorName = line;
                }
            } catch (final IOException e) {
                Logger.getLogger(AdvancedInetAddress.class.getName()).log(Level.SEVERE, "Failed to Find MAC Address", e);
            }
        }
        return vendorName;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((this.address == null) ? 0 : this.address.hashCode());
        return result;
    }

    /**
     * @return a boolean indicating if the InetAddress is a wildcard address.
     * @see InetAddress#isAnyLocalAddress()
     */
    public boolean isAnyLocalAddress() {
        return this.address.isAnyLocalAddress();
    }

    /**
     * @return a boolean indicating if the InetAddress is a link local address; or
     *         false if address is not a link local unicast address.
     * @see InetAddress#isLinkLocalAddress()
     */
    public boolean isLinkLocalAddress() {
        return this.address.isLinkLocalAddress();
    }

    /**
     * @return a boolean indicating if the InetAddress is a loopback address; or
     *         false otherwise.
     * @see InetAddress#isLoopbackAddress()
     */
    public boolean isLoopbackAddress() {
        return this.address.isLoopbackAddress();
    }

    /**
     * @return a boolean indicating if the address has is a multicast address of
     *         global scope, false if it is not of global scope or it is not a
     *         multicast address
     * @see InetAddress#isMCGlobal()
     */
    public boolean isMCGlobal() {
        return this.address.isMCGlobal();
    }

    /**
     * @return a boolean indicating if the address has is a multicast address of
     *         link-local scope, false if it is not of link-local scope or it is not
     *         a multicast address
     * @see InetAddress#isMCLinkLocal()
     */
    public boolean isMCLinkLocal() {
        return this.address.isMCLinkLocal();
    }

    /**
     * @return a boolean indicating if the address has is a multicast address of
     *         node-local scope, false if it is not of node-local scope or it is not
     *         a multicast address
     * @see InetAddress#isMCNodeLocal()
     */
    public boolean isMCNodeLocal() {
        return this.address.isMCNodeLocal();
    }

    /**
     * @return a boolean indicating if the address has is a multicast address of
     *         organization-local scope, false if it is not of organization-local
     *         scope or it is not a multicast address
     * @see InetAddress#isMCOrgLocal()
     */
    public boolean isMCOrgLocal() {
        return this.address.isMCOrgLocal();
    }

    /**
     * @return a boolean indicating if the address has is a multicast address of
     *         site-local scope, false if it is not of site-local scope or it is not
     *         a multicast address
     * @see InetAddress#isMCSiteLocal()
     */
    public boolean isMCSiteLocal() {
        return this.address.isMCSiteLocal();
    }

    /**
     *
     * @return a boolean indicating if the InetAddress is an IP multicast address
     * @see InetAddress#isMulticastAddress()
     */
    public boolean isMulticastAddress() {
        return this.address.isMulticastAddress();
    }

    /**
     * @param timeout the time, in milliseconds, before the call aborts
     * @return a boolean indicating if the address is reachable.
     * @throws IOException Error trying to reach the host.
     * @see InetAddress#isReachable(int)
     */
    public boolean isReachable(final int timeout) throws IOException {
        return this.address.isReachable(timeout);
    }

    /**
     * @param networkInterface   the NetworkInterface through which the test will be done, or
     *                null for any interface
     * @param ttl     the maximum numbers of hops to try or 0 for the default
     * @param timeout the time, in milliseconds, before the call aborts
     * @return a boolean indicating if the address is reachable.
     * @throws IOException Error trying to reach the host.
     * @see InetAddress#isReachable(NetworkInterface, int, int)
     */
    public boolean isReachable(final NetworkInterface networkInterface, final int ttl, final int timeout) throws IOException {
        return this.address.isReachable(networkInterface, ttl, timeout);
    }

    /**
     * @return a boolean indicating if the InetAddress is a site local address; or
     *         false if address is not a site local unicast address.
     * @see InetAddress#isSiteLocalAddress()
     */
    public boolean isSiteLocalAddress() {
        return this.address.isSiteLocalAddress();
    }

    /**
     * Gets the next IP Address after this one.
     *
     * @return AdvancedInetAddress that references the next address after this
     *         address.
     * @throws UnknownHostException Host is an unknown address
     */
    public AdvancedInetAddress next() throws UnknownHostException {
        final byte[] newAddr = this.getAddress();

        int index = newAddr.length - 1;

        while (true) {
            if (newAddr[index] == 255) {
                newAddr[index] = 0;
                index--;
            } else {
                newAddr[index] = (byte) (newAddr[index] + 1);
                break;
            }
        }

        return new AdvancedInetAddress(InetAddress.getByAddress(newAddr));
    }

    /**
     * @return -1 of ping failed, or the average number of MS to ping this address
     */
    public int ping() {
        try {
            final Process ping = new Process();
            ping.setArgs(new String[]{"ping", this.getHostName()});
            ping.start();
            ping.join();
            final String[] results = ping.getOutput();

            for (final String result : results) {
                if (result.contains("Average =")) {
                    final int avgIndex = result.indexOf("Average =") + 9;
                    final int msIndex = result.indexOf("ms", avgIndex);

                    return Integer.parseInt(result.substring(avgIndex, msIndex).trim());
                }
            }
        } catch (final InterruptedException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Ping Failed", e);
        }

        return -1;
    }

    /**
     * Returns the previous address before this one.
     *
     * @return AdvancedInetAddress that references the previous address before this
     *         address.
     * @throws UnknownHostException Host is an unknown address
     */
    public AdvancedInetAddress previous() throws UnknownHostException {
        final byte[] newAddr = this.getAddress();

        int index = newAddr.length - 1;

        while (true) {
            if (newAddr[index] == 0) {
                newAddr[index] = (byte) 255;
                index--;
            } else {
                newAddr[index] = (byte) (newAddr[index] - 1);
                break;
            }
        }

        return new AdvancedInetAddress(InetAddress.getByAddress(newAddr));
    }

    /**
     * Determines if this port is open or closed
     *
     * @param port Port to check
     * @return true if the port is open.
     */
    public boolean tcpPortOpen(final int port) {
        Socket s;
        try {
            s = new Socket(this.address, port);

            if (s.isConnected()) {
                s.close();

                return true;
            }
        } catch (final ConnectException e) {
        } catch (final IOException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.WARNING, "Host: " + this.toString() + " Port: " + port, e);
        }
        return false;
    }

    /**
     * @return a string representation of this IP address.
     * @see InetAddress#toString()
     */
    @Override
    public String toString() {
        return this.address.toString();
    }

    /**
     * @return Array of AdvancedInetAddress that make the path of the trace route
     */
    public AdvancedInetAddress[] traceRoute() {
        final ArrayList<AdvancedInetAddress> list = new ArrayList<>();

        try {
            final Process traceRoute = new Process();
            //noinspection SpellCheckingInspection
            traceRoute.setArgs(new String[]{"tracert", this.getHostName()});
            traceRoute.start();
            traceRoute.join();

            final String[] lines = traceRoute.getOutput();

            for (final String line : lines) {
                final String output = line.trim();

                if (output.startsWith("0") || output.startsWith("1") || output.startsWith("2") || output.startsWith("3")
                        || output.startsWith("4") || output.startsWith("5") || output.startsWith("6")
                        || output.startsWith("7") || output.startsWith("8") || output.startsWith("9")) {
                    if (output.contains("Request timed out")) {
                        list.add(null);
                    } else if (output.contains("Destination host unreachable")) {
                        list.add(null);
                    } else {
                        String node = line.substring(30).trim();
                        if (node.contains("[")) {
                            node = node.substring(node.indexOf("[") + 1, node.indexOf("]")).trim();
                        }

                        try {
                            list.add(new AdvancedInetAddress(node));
                        } catch (final UnknownHostException e) {
                            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "TraceRoute Failed adding node", e);
                        }
                    }
                }
            }
        } catch (final InterruptedException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "TraceRoute Failed", e);
        }
        return list.toArray(new AdvancedInetAddress[list.size()]);
    }
}
